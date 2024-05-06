package Client_Java.model;

import Client_Java.BoggledApp.Player;

import java.util.Arrays;
import java.util.List;

public class LobbyPageModel {
    private Player player;

    public LobbyPageModel() {
        player = null;
    }

    public void logout() {
        if (player != null) {
            ClientModel.authService.logout(player.pid);
        }
    } // end of logout

    public int startGame() {
        return ClientModel.gameService.startGame(player.pid);
    } // end of startGame

    public List<String> getLeaderboardPlayers() {
        return Arrays.asList(ClientModel.gameService.getLeaderboards());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
} // end of LobbyPageModel class
