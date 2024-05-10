package Server_Java.model.implementations;

import Server_Java.model.ServerJDBC;
import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Game {
    private final int gid;
    private Round currentRound = null, nextRound = new Round();
    private final List<Player> playerList = new ArrayList<>();
    private final HashMap<Integer, List<Integer>> playerRoundPoints = new LinkedHashMap<>();
    private final HashMap<Integer, Integer> playerRoundWinCounts = new LinkedHashMap<>();
    private final HashMap<Integer, HashSet<String>> playerWordEntries = new LinkedHashMap<>();
    private String roundWinner = "None", gameWinner = "None";
    private static int roundTime;
    private int roundNumber, totalSubmissions;
    private boolean isDoneEvaluating = false;
    private final AtomicBoolean startNextRound = new AtomicBoolean(false);
    private final CountDownLatch submissionLatch;

    public Game(int gid) {
        this.gid = gid;
        submissionLatch = new CountDownLatch(1);
        totalSubmissions = 0;

        roundNumber = 0;
    }

    /**
     * one time call to start the game's lifecycle.
     */
    public void startGame() {
        // use the newRound to create the first round to be assigned to the getNextRound method for the first instance of the game
        nextRound = prepareRoundDetails();

        Thread startGameThread = new Thread(() -> {
            while (true) {
                if (startNextRound.get()) {
                    synchronized (this) { // TODO: this fixed the multiple instances being started
                        roundTime = ServerModel.roundLength;

                        try {
                            Thread.sleep(5000);
                            System.out.println("- STARTING ROUND"); // TODO: remove after debugging

                            while (roundTime != -1) {
                                roundTime--;
                                Thread.sleep(1000);
                            }

                            evaluateRound();

                            startNextRound.set(false);

                            // create the next round after the current round is finished
                            nextRound = prepareRoundDetails();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
        Player player = ServerJDBC.getPlayer(pid);

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
        try {
            synchronized (this) {
                totalSubmissions++;

                if (totalSubmissions == playerList.size()) {
                    startNextRound.set(true);

                    totalSubmissions = 0;

                    isDoneEvaluating = false;

                    // release the latch
                    submissionLatch.countDown();
                }
            }

            // hold the threads invoking the getRound method if not all players invoked the call
            submissionLatch.await();

            // assign the next round to the current round
            currentRound = nextRound;
            System.out.println("\n- CURRENT ROUND: " + currentRound.roundNumber); // TODO: Remove after debugging
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return currentRound;
    } // end of getRound

    /**
     * evaluates the round by computing the points of each player per round.
     */
    public void evaluateRound() {
        // compute the points of each player
        computePoints();

        roundWinner = determineRoundWinner();

        gameWinner = determineGameWinner();

        // finalize the points of each player by adding the points of each round to the player if a game winner is determined
        if (!gameWinner.equals("None")) {
            playerList.forEach(player -> player.points += playerRoundPoints.get(player.pid)
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum());

            // TODO: use the server model to add the points (player.points) of each player
        }

        isDoneEvaluating = true;

        System.out.println("\n- FINISHED EVALUATING CURRENT ROUND"); // TODO: remove after debugging
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

    public boolean isDoneEvaluatingRound() {
        return isDoneEvaluating;
    }

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

        playerWordEntries.values().forEach(HashSet::clear);

        Round r = new Round();
        r.gid = gid;
        r.playersData = getPlayerDatas();
        r.characterSet = generateCharacterSet();
        r.roundNumber = roundNumber;

        return r;
    } // end of prepareRoundDetails

    /**
     * creates an array containing the win counts of each player in the game.
     *
     * @return array of strings containing the username along with their win count e.g. "Leonhard-3-145"
     */
    private String[] getPlayerDatas() {
        List<String> temp = new ArrayList<>();

        for (Player player : playerList) {
            int winCount = playerRoundWinCounts.getOrDefault(player.pid, 0);

            List<Integer> pointsList = playerRoundPoints.get(player.pid);
            if (pointsList.isEmpty()) {
                temp.add(player.username + "-" + winCount + "-" + 0);
            } else {
                int totalPoints = 0;

                for (int roundPoints : pointsList) {
                    totalPoints += roundPoints;
                }

                temp.add(player.username + "-" + winCount + "-" + totalPoints);
            }
        }

        temp.sort((a, b) -> {
            int winCountA = Integer.parseInt(a.split("-")[1]);
            int winCountB = Integer.parseInt(b.split("-")[1]);
            return Integer.compare(winCountB, winCountA);
        });

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

    private String determineRoundWinner() {
        String username = "None";

        int highestPoints = 0;
        int roundWinnerPid = 0;

        System.out.println("PLAYER ROUND POINTS SIZE: " + playerRoundPoints.size());
        for (Map.Entry<Integer, List<Integer>> entry : playerRoundPoints.entrySet()) {
            List<Integer> playerPointsList = entry.getValue();
            int lastIndex = playerPointsList.size() - 1;

            int playerPoints = lastIndex >= 0 ? playerPointsList.get(lastIndex) : 0;

            if (playerPoints > highestPoints) {
                highestPoints = playerPoints;
                roundWinnerPid = entry.getKey();
            } else if (highestPoints == playerPoints) {
                username = "None";
                roundWinnerPid = 0;
            }
        }

        if (roundWinnerPid != 0) {
            for (Player player : playerList) {
                if (player.pid == roundWinnerPid) {
                    username = player.username;
                    playerRoundWinCounts.compute(roundWinnerPid, (key, value) -> (value == null) ? 1 : value + 1);
                    break;
                }
            }
        }
        return username;
    } // end of computeRoundWinner

    private String determineGameWinner() {
        // check if a player has three wins
        return playerList.stream()
                .filter(player -> playerRoundWinCounts.containsKey(player.pid) && playerRoundWinCounts.get(player.pid) == 3)
                .map(player -> player.username)
                .findFirst()
                .orElse("None");
    }

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

    public String getCharacterSet() {
        return currentRound.characterSet;
    }
} // end of Game class
