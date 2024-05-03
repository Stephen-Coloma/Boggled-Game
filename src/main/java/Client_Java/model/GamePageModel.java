package Client_Java.model;

import Client_Java.BoggledApp.GameNotFound;
import Client_Java.BoggledApp.GameTimeOut;
import Client_Java.BoggledApp.Round;

public class GamePageModel {
    private int gid;
    private Round round;

    public GamePageModel(int gid) {
        this.gid = gid;
    }

    public int getGid() {
        return gid;
    }

    public void obtainRound() {
        try {
            round = ClientModel.gameService.playRound(gid);
        } catch (GameNotFound gameNotFound) {
            System.out.println(gameNotFound.reason);
        }
    }

    public int getRemainingRoundTime() throws GameTimeOut {
        return ClientModel.gameService.getRemainingRoundTime(gid);
    }

    public String getRoundWinner() {
        return ClientModel.gameService.getRoundWinner(gid);
    }

    public String getGameWinner() {
        return ClientModel.gameService.getGameWinner(gid);
    }

    public Round getRound() {
        return round;
    }
} // end of GamePageModel class
