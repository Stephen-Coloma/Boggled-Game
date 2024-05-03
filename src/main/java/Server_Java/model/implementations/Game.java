package Server_Java.model.implementations;

import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Game {
    private int gid;
    private Round round;
    private List<Player> playerList;
    private HashMap<Integer, List<Integer>> playerRoundPoints;
    private HashMap<Integer, Integer> playerRoundWinCounts;
    private HashMap<Integer, HashSet<String>> playerWordEntries;
    private String characterSet;
    private String roundWinner;
    private String gameWinner;
    private static int roundTime;
    private int roundNumber;
    private int totalSubmissions;
    private AtomicBoolean startNextGame = new AtomicBoolean(false);
    private CountDownLatch submissionLatch;


    public Game(int gid) {
        this.gid = gid;
        submissionLatch = new CountDownLatch(1);
        totalSubmissions = 0;

        roundNumber = 0;
    }

    /**
     * one time call to start the game's lifecycle.
     */
    private void startGame() {
        Thread startGameThread = new Thread(() -> {
            while (true) {
                if (startNextGame.get()) {
                    roundTime = ServerModel.roundLength;
                    totalSubmissions = 0;

                    try {
                        while (roundTime > 0) {
                            roundTime--;
                            Thread.sleep(1000);
                        }

                        evaluateRound();

                        startNextGame.set(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        startGameThread.setDaemon(true);
        startGameThread.start();
    } // end of startGame

    /**
     * creates a player object and adds it to the list of players waiting for the game to start.
     *
     * @param pid the unique id of the player
     */
    public void addPlayer(int pid) {
        // TODO: query from the database to obtain the details of the player
        Player player = new Player();

        playerList.add(player);
        playerRoundPoints.put(pid, new ArrayList<>());
        playerWordEntries.put(pid, new HashSet<>());
        playerRoundWinCounts.put(pid, 0);
    } // end of addPlayer

    /**
     * adds a word entry from a given player to their hash set.
     *
     * @param pid the id of the player
     * @param word the word submitted by the player
     */
    public void addWordEntry(int pid, String word) {
        playerWordEntries.get(pid).add(word.toLowerCase());
    } // end of addWordEntry

    /**
     * prepares the round to be used by the players.
     *
     * @return next round
     */
    public Round getNextRound() {
        round = null;
        try {
            synchronized (this) {
                totalSubmissions++;

                if (totalSubmissions == playerList.size()) {
                    // prepare the next round
                    round = prepareRoundDetails();

                    startNextGame.set(true);

                    // release the latch
                    submissionLatch.countDown();
                }
            }
            // hold the threads invoking the getRound method if not all players invoked the call
            submissionLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return round;
    } // end of getRound

    /**
     * evaluates the round by computing the points of each player per round.
     */
    public void evaluateRound() {
        // compute the points of each player
        computePoints();

        // determine the round winner
        roundWinner = playerRoundPoints.entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().get(entry.getValue().size() - 1)))
                .map(entry -> {
                    int roundWinnerPid = entry.getKey();

                    return playerList.stream()
                            .filter(player -> player.pid == roundWinnerPid)
                            .map(player -> player.username)
                            .findFirst()
                            .orElse("None");
                })
                .orElse("None");

        // check if a player has three wins
        gameWinner = playerList.stream()
                .filter(player -> playerRoundWinCounts.containsKey(player.pid) && playerRoundWinCounts.get(player.pid) == 3)
                .map(player -> player.username)
                .findFirst()
                .orElse("None");

        // finalize the points of each player by adding the points of each round to the player if a game winner is determined
        if (!gameWinner.equals("None")) {
            playerList.forEach(player -> {
                player.points += playerRoundPoints.get(player.pid)
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum();
            });

            // TODO: use the server model to add the points (player.points) of each player
        }
    } // end of evaluateRound

    /**
     * removes the player from the game data.
     *
     * @param pid the id of the player
     */
    public void removePlayer(int pid) {
        Iterator<Player> iterator = playerList.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.pid == pid) {
                iterator.remove();
                break;
            }
        }

        playerRoundPoints.remove(pid);
        playerRoundWinCounts.remove(pid);
        playerWordEntries.remove(pid);
    } // end of removePlayer

    /**
     * returns a boolean value to check if the game is valid.
     *
     * @return true if players are more than 1, false otherwise
     */
    public boolean isGameValid() {
        return playerList.size() > 1;
    } // end of isGameValid

    /**
     * returns the number of players in the game.
     *
     * @return the number of players
     */
    public int getPlayerCount() {
        return playerList.size();
    } // end of getPlayerCount

    /*--------------------------------< HELPER METHODS >--------------------------------*/

    /**
     * prepares the round object by populating the needed information.
     *
     * @return new round details
     */
    private Round prepareRoundDetails() {
        roundNumber++;

        Round newRound = new Round();
        newRound.gid = gid;
        newRound.playersData = getWinCounts();
        newRound.characterSet = generateCharacterSet();
        newRound.roundNumber = roundNumber;

        return newRound;
    } // end of prepareRoundDetails

    /**
     * creates an array containing the win counts of each player in the game.
     *
     * @return array of strings containing the username along with their win count e.g. "Leonhard-3"
     */
    private String[] getWinCounts() {
        List<String> temp = new ArrayList<>();

        for (Player player : playerList) {
            int winCount = playerRoundWinCounts.get(player.pid);

            temp.add(player.username + "-" + winCount);
        }

        return temp.toArray(new String[0]);
    } // end of getRoundsWonByPlayers

    /**
     * computes the points of all players in the current round.
     */
    private void computePoints() {
        List<String> combinedWordEntries = new ArrayList<>();

        // combine all word entries of each player into one single hash set
        for (HashSet<String> wordEntry : playerWordEntries.values()) {
            combinedWordEntries.addAll(wordEntry);
        }

        // iterate through each player
        for (Player player : playerList) {
            int points = 0;

            // obtain the hashset of the player
            HashSet<String> wordEntry = playerWordEntries.get(player.pid);

            // iterate through each word in the hashset
            for (String word : wordEntry) {
                // check if the word is unique in the combined word entries (i.e. the word only occurs once)
                if (Collections.frequency(combinedWordEntries, word) == 1) {
                    points += word.length();
                }
            }

            playerRoundPoints.get(player.pid).add(points);
        }
    } // end of computePoints

    /**
     * generates a random set of 20 characters, 7 of which are vowels and 13 being consonants.
     *
     * @return the generated character set in String format
     */
    private String generateCharacterSet() {
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";
        List<Character> characterSet = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            characterSet.add(vowels.charAt((int) (Math.random() * vowels.length())));
        }
        for (int i = 0; i < 13; i++) {
            characterSet.add(consonants.charAt((int) (Math.random() * consonants.length())));
        }

        return characterSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    } // end of generateCharacterSet

    /*--------------------------------< GETTER AND SETTER METHODS >--------------------------------*/

    public int getGid() {
        return gid;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public String getRoundWinner() {
        return roundWinner;
    }

    public String getGameWinner() {
        return gameWinner;
    }
} // end of Game class
