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

        //todo: create an extra thread that transfers waiting game into ongoing game.
    }

    @Override
    public int startGame(Player player) {
        //todo: create the logic of the waiting game
        return 0;
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
        return null;
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
