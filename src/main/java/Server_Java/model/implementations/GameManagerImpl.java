package Server_Java.model.implementations;

import Server_Java.model.implementations.BoggledApp.GameManagerPOA;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;

public class GameManagerImpl extends GameManagerPOA {
    @Override
    public int startGame(int pid) {
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
    public Round submitAndLoadNextRound(String[] answersArray, int pid, int gid) {
        return null;
    }

    @Override
    public Player[] viewLeaderboards() {
        return new Player[0];
    }
}
