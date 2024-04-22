package Server_Java.model.implementations;

import Server_Java.model.implementations.BoggledApp.GameManagerPOA;
import Server_Java.model.implementations.BoggledApp.NoPlayersLeft;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;

public class GameManagerImpl extends GameManagerPOA {

    @Override
    public int startGame(Player player) {
        return 0;
    }

    @Override
    public int getWaitingTime(int gid) {
        return 0;
    }

    @Override
    public int getTotalPlayersJoined(int gid) {
        return 0;
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
