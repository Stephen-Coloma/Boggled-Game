package Server_Java.model.implementations;

import Server_Java.ServerJava;
import Server_Java.model.ServerJDBC;
import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.GameManagerPOA;
import Server_Java.model.implementations.BoggledApp.NoPlayersLeft;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class GameManagerImpl extends GameManagerPOA {
    private AtomicReference<Game> waitingGame = new AtomicReference<>();
    private HashMap<Integer, Game> ongoingGames;
    private static int timeLeft;
    private static int playerCounterHolder = 1; //to be used only when the waiting game is now set to null

    public GameManagerImpl() {
        ongoingGames = new LinkedHashMap<>();
        //an extra thread that transfers
        Thread transfer = new Thread(()->{
            try {
                while (true){
                    if (waitingGame.get() != null){
                        timeLeft = ServerModel.waitingTime;
                        for (; timeLeft != 0 ; timeLeft--) {
                            Thread.sleep(1000);
                        }
                        //after the countdown, check the validity of the game, then transfer it to hashmap or not
                        if (waitingGame.get().isGameValid()){
                            ongoingGames.put(waitingGame.get().getGid(), waitingGame.get());
                            ServerJDBC.saveGameId(waitingGame.get().getGid());  // save the game id to the server so that the game id is reserved if some players would get the latest gid
                            waitingGame.set(null);
                        }else {
                            waitingGame.set(null);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        transfer.start();
    }

    @Override
    public int startGame(Player player) {
        if (waitingGame.get() == null){
            int latestGid = ServerJDBC.getLastGameId();
            playerCounterHolder = 1;
            waitingGame.set(new Game(++latestGid, player));
        }else {
            playerCounterHolder++;
            waitingGame.get().addPlayerToGame(player);
        }
        return waitingGame.get().getGid();
    }

    @Override
    public int getWaitingTime(int gid) {
        return timeLeft;
    }

    @Override
    public int getTotalPlayersJoined(int gid) {
        return playerCounterHolder;
    }

    @Override
    public Round playFirstRound(int gid) {
        Game game = ongoingGames.get(gid);
        if (game == null){
            ServerJDBC.removeLatestGid();

            Round noDataRound = new Round(); //this round is a no data round to be fetched in client side
            noDataRound.gid = -1;
            noDataRound.playersData = new Player[]{};
            noDataRound.characterSet = "";
            noDataRound.roundLength = 0;
            noDataRound.roundNumber = 0;
            noDataRound.roundWinner = new Player(-1,"", "", "", -1, -1); //the pid will be used as validator in the client side
            noDataRound.gameWinner = new Player(-1,"", "", "", -1, -1);; //the pid will be used as validator in the client side
            return noDataRound;
        }else {
            return game.getFirstRound();
        }
    }

    @Override
    public Round submitAndLoadNextRound(String[] answersArray, int pid, int gid) throws NoPlayersLeft {
        // FIXME: 4/25/2024 , handle when there is no players left already. //a solution would be to have a thread from the client that sends a message to server to have a heartbeat. optional
        //  do not implement yet;
        Game game = ongoingGames.get(gid);
        return game.getNextRound(answersArray, pid);
    }

    @Override
    public Player[] viewLeaderboards() {
        return ServerJDBC.fetchTopPlayers();
    }
}
