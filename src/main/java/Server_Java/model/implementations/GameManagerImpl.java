package Server_Java.model.implementations;

import Server_Java.model.ServerJDBC;
import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.GameManagerPOA;
import Server_Java.model.implementations.BoggledApp.GameTimeOut;
import Server_Java.model.implementations.BoggledApp.Round;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class GameManagerImpl extends GameManagerPOA {
    private AtomicReference<Game> waitingGame = new AtomicReference<>();
    private HashMap<Integer, Game> ongoingGames = new LinkedHashMap<>();
    private static int timeLeft;
    private static int playerCounterHolder = 1; // FIXME: temporary, use this if the player count is not being displayed properly for the client. to be used only when the waiting game is now set to null

    /**
     * a constructor with a thread that handles the waiting game object and transfers it to the ongoing games list if
     * certain conditions are met.
     */
    public GameManagerImpl() {
        Thread ongoingGameManager = new Thread(() -> {
            try {
                while (true) {
                    timeLeft = ServerModel.waitingTime;
                    while (timeLeft != 0) {
                        Thread.sleep(1000);
                        timeLeft--;
                    }

                    if (waitingGame.get().isGameValid()) {
                        ongoingGames.put(waitingGame.get().getGid(), waitingGame.get());
                    }
                    waitingGame.set(null);
                }
            } catch (InterruptedException e) {
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
            int latestGid = ServerJDBC.getLastGameId();
            waitingGame.set(new Game(++latestGid));
        } else {
            waitingGame.get().addPlayer(pid);
        }
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
        if (timeLeft == 0) {
            throw new GameTimeOut();
        }
        return timeLeft;
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
     */
    @Override
    public Round playRound(int gid) {
        return ongoingGames.get(gid).getNextRound();
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
        if (remainingTime == 0) {
            throw new GameTimeOut();
        }
        return remainingTime;
    } // end of getRemainingRoundTime

    /**
     * validates the word sent by the player.
     *
     * @param word the word to be validated
     * @param pid the id of the player
     * @param gid the id of the game the player is in
     * @return true if the given word is valid, false otherwise
     */
    @Override
    public boolean submitWord(String word, int pid, int gid) {
        return false;
    } // end of submitWord

    /**
     * returns the round winner.
     *
     * @param gid the id of the game
     * @return the username of the round winner
     */
    @Override
    public String getRoundWinner(int gid) {
        return null;
    }

    /**
     * returns the game winner.
     *
     * @param gid the id of the game
     * @return the username of the game winner
     */
    @Override
    public String getGameWinner(int gid) {
        return null;
    }

    /**
     * removes the player from the game if the player left the game room.
     *
     * @param pid the id of the player to be removed
     * @param gid the id of the game the player is in
     */
    @Override
    public void leaveGame(int pid, int gid) {
        ongoingGames.get(gid).removePlayer(pid);
    }

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
