package Server_Java.model.implementations;

import Server_Java.model.ServerJDBC;
import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.GameManagerPOA;
import Server_Java.model.implementations.BoggledApp.NoPlayersLeft;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class GameManagerImpl extends GameManagerPOA {
    private Game waitingGame;
    private HashMap<Integer, Game> ongoingGames;
    private static int TIME_LEFT;

    public GameManagerImpl() {
        ongoingGames = new LinkedHashMap<>();
        //an extra thread that transfers
        Thread transfer = new Thread(()->{
            try {
                while (true){
                    if (waitingGame != null){
                        TIME_LEFT = ServerModel.WAITING_TIME;
                        for (; TIME_LEFT != 0 ; TIME_LEFT--) {
                            Thread.sleep(1000);
                        }

                        //after the countdown, check the validity of the game, then transfer it to hashmap or not
                        if (waitingGame.isGameValid()){
                            ongoingGames.put(waitingGame.getGid(), waitingGame);
                            waitingGame = null;
                        }else {
                            waitingGame = null;
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
        if (waitingGame == null){
            int latestGid = ServerJDBC.getLastGameId();
            waitingGame = new Game(++latestGid, player);
        }else {
            waitingGame.addPlayerToGame(player);
        }
        return waitingGame.getGid();
    }

    @Override
    public int getWaitingTime(int gid) {
        return TIME_LEFT;
    }

    @Override
    public int getTotalPlayersJoined(int gid) {
        return waitingGame.getPlayersData().size();
    }

    @Override
    public Round playFirstRound(int gid) {
        Game game = ongoingGames.get(gid);
        if (game == null){
            return null;
        }else {
            return game.getFirstRound();
        }
    }

    @Override
    public Round submitAndLoadNextRound(String[] answersArray, int pid, int gid) throws NoPlayersLeft {
        return null;
    }

    @Override
    public Player[] viewLeaderboards() {
        return new Player[0];
    }
}
