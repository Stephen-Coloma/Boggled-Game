package Client_Java.model;

import Client_Java.BoggledApp.*;

import java.util.HashSet;
import java.util.Set;

public class GamePageModel {
    private Player player;
    private int gid;
    private Round round = new Round(0, null, null, 0);
    private Set<String> wordEntry = new HashSet<>();

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
        wordEntry.add(word);
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

    public String getGamePoints(boolean gameWinner) {
        String[] playerDatas = round.playersData;
        for (String entry : playerDatas) {
            String username = entry.split("-")[0];
            String points = entry.split("-")[2];

            if (gameWinner) {
                if (username.equals(getGameWinner())) {
                    return points;
                }
            } else {
                if (username.equals(player.username)) {
                    return points;
                }
            }
        }
        return "0";
    }

    public boolean isAlreadySubmitted(String word) {
        return wordEntry.contains(word);
    }

    public String getRoundWinner() {
        return ClientModel.gameService.getRoundWinner(gid);
    }

    public String getGameWinner() {
        return ClientModel.gameService.getGameWinner(gid);
    }

    public void logout() {
        ClientModel.authService.logout(player.pid);
    }

    public void leaveGame() {
        ClientModel.gameService.leaveGame(player.pid, gid);
    }

    public Round getRound() {
        return round;
    }
} // end of GamePageModel class
