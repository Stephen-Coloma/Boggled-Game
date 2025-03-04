package Server_Java.model.implementations;

import Server_Java.model.ServerJDBC;
import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GameServiceImpl extends GameServicePOA {
    private AtomicReference<Game> waitingGame = new AtomicReference<>();
    private HashMap<Integer, Game> ongoingGames = new LinkedHashMap<>();
    private AtomicInteger timeLeft = new AtomicInteger(0);

    /**
     * a constructor with a thread that handles the waiting game object and transfers it to the ongoing games list if
     * certain conditions are met.
     */
    public GameServiceImpl() {
        Thread ongoingGameManager = new Thread(() -> {
            try {
                while (true) {
                    if (waitingGame.get() != null) {
                        int initialTime = ServerModel.waitingTime;
                        timeLeft.set(initialTime);
                        System.out.println("Initial time: " + initialTime); // TODO: remove after debugging


                        while (timeLeft.get() != -1) {
                            try {
                                Thread.sleep(1000);
                                timeLeft.getAndDecrement();
                                System.out.println("Decrementing. Time left: " + timeLeft.get()); // TODO: remove after debugging

                                if (waitingGame.get().getPlayerCount() == 0) {
                                    timeLeft.set(-1);
                                    break;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (waitingGame.get().isGameValid()) {
                            System.out.println("valid game"); // TODO: remove after debugging

                            // start the game
                            waitingGame.get().startGame();

                            // put the game to the ongoing games list
                            ongoingGames.put(waitingGame.get().getGid(), waitingGame.get());

                            // save the game to the database
                            ServerJDBC.saveGameId(waitingGame.get().getGid());
                        }

                        waitingGame.set(null);

                        System.out.println("game is set to null"); // TODO: remove after debugging
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ongoingGameManager.setDaemon(true);
        ongoingGameManager.start();
    }

    /**
     * assigns a player to the respective waiting game object.
     *
     * @param pid the id of the player
     * @return the game id of the waiting game object
     */
    @Override
    public int startGame(int pid) {
        if (waitingGame.get() == null) {
            System.out.println("creating a game"); // TODO: remove after debugging

            int latestGid = ServerJDBC.getLastGameId();
            waitingGame.set(new Game(++latestGid));
        }
        waitingGame.get().addPlayer(pid);

        return waitingGame.get().getGid();
    } // end of startGame

    /**
     * returns an integer representing the remaining waiting time left.
     *
     * @return the remaining waiting time
     * @throws GameTimeOut thrown when the timeleft has already expired
     */
    @Override
    public int getRemainingWaitingTime() throws GameTimeOut {
        if (timeLeft.get() == -1) {
            throw new GameTimeOut("countdown expired");
        }
        return timeLeft.get();
    } // end of getRemainingWaitingTime

    /**
     * returns the number of players that are currently in the waiting game.
     *
     * @return the number of players
     */
    @Override
    public int getNumberOfPlayersJoined() {
        return waitingGame.get().getPlayerCount();
    } // end of getNumberOfPlayersJoined

    /**
     * returns the prepared round to the player.
     *
     * @param gid the gid of the game
     * @return the round object containing the information of the next round
     * @throws GameNotFound throws an exception if the game is not found on the ongoing games.
     *                      The following may be the causes:
     *                      - the given gid is wrong.
     *                      - the waiting game is invalid causing the game to not be added to the ongoing games list.
     */
    @Override
    public Round playRound(int gid) throws GameNotFound {
        if (ongoingGames.keySet().contains(gid)) {
            return ongoingGames.get(gid).getNextRound();
        } else {
            throw new GameNotFound("game not found");
        }
    } // end of playRound

    /**
     * returns an integer representing the remaining round time left.
     *
     * @param gid the gid of the game
     * @return the remaining round time
     * @throws GameTimeOut thrown when the remaining round time has already expired
     */
    @Override
    public int getRemainingRoundTime(int gid) throws GameTimeOut {
        int remainingTime = ongoingGames.get(gid).getRoundTime();
        if (remainingTime == -1) {
            throw new GameTimeOut("countdown expired");
        }
        return remainingTime;
    } // end of getRemainingRoundTime

    /**
     * validates the word sent by the player.
     *
     * @param word the word to be validated
     * @param pid the id of the player
     * @param gid the id of the game the player is in
     * @throws InvalidWord thrown when the word is not included to the word bank or it does not comply to the word rules
     */
    @Override
    public void submitWord(String word, int pid, int gid) throws InvalidWord {

        // check if the word length is greater than 3
        if (word.length() < 4) throw new InvalidWord(word + " is invalid");

        // check if the letters of the word are included in the character set
        StringBuilder characterSet = new StringBuilder(ongoingGames.get(gid).getCharacterSet());
        for (char letter : word.toCharArray()) {
            int index = characterSet.indexOf(String.valueOf(letter));

            if (index != -1) {
                characterSet.deleteCharAt(index);
            } else {
                throw new InvalidWord(word + " is invalid");
            }
        }

        // check if the word is included in the word bank
        if (!ServerModel.isFoundInWordBank(word)) throw new InvalidWord(word + " is invalid");

        // store the word in the player's word entry container.
        ongoingGames.get(gid).addWordEntry(pid, word);
    } // end of submitWord

    /**
     * returns the round winner.
     *
     * @param gid the id of the game
     * @return the username of the round winner
     */
    @Override
    public String getRoundWinner(int gid) {
        return ongoingGames.get(gid).getRoundWinner();
    }

    /**
     * returns the game winner.
     *
     * @param gid the id of the game
     * @return the username of the game winner
     */
    @Override
    public String getGameWinner(int gid) {
        return ongoingGames.get(gid).getGameWinner();
    }

    @Override
    public boolean roundEvaluationDone(int gid) {
        return ongoingGames.get(gid).isDoneEvaluatingRound();
    }

    /**
     * removes the player from the game if the player left the game room.
     *
     * @param pid the id of the player to be removed
     * @param gid the id of the game the player is in
     */
    @Override
    public void leaveGame(int pid, int gid) {
        if (ongoingGames.containsKey(gid)) {
            ongoingGames.get(gid).removePlayer(pid);
        } else {
            waitingGame.get().removePlayer(pid);
        }
    } // end of leaveGame

    /**
     * returns the top 5 players with the highest points in descending order.
     *
     * @return an array of string objects that contains the username and their respective points
     */
    @Override
    public String[] getLeaderboards() {
        return ServerJDBC.fetchTopPlayers();
    }
}
