package Client_Java.model;

import Client_Java.BoggledApp.*;

public class GamePageModel {
    private Player player;
    private int gid;
    private Round round = new Round(0, null, null, 0);

    public GamePageModel(Player player, int gid) {
        this.player = player;
        this.gid = gid;
    }

    public int getGid() {
        return gid;
    }

    public void obtainRound() {
        try {
            round = ClientModel.gameService.playRound(gid);
        } catch (GameNotFound gameNotFound) {
            round = new Round(0, new String[0], "", 0);
        }
    }

    public int getRemainingRoundTime() throws GameTimeOut {
        return ClientModel.gameService.getRemainingRoundTime(gid);
    }

    public void submitWord(String word) throws InvalidWord {
        ClientModel.gameService.submitWord(word, player.pid, gid);
    }

    public boolean isDoneEvaluatingRound() {
        return ClientModel.gameService.roundEvaluationDone(gid);
    }

    public String getUsername() {
        return player.username;
    }

    public int getPid() {
        return player.pid;
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
