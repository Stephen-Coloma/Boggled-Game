package Client_Java.model;

import Client_Java.BoggledApp.Player;

import java.util.Arrays;
import java.util.List;

public class LobbyPageModel {
    private Player player;

    public LobbyPageModel(Player player) {
        this.player = player;
    }

    public void logout() {
        ClientModel.authService.logout(player.pid);
    } // end of logout

    public int startGame() {
        return ClientModel.gameService.startGame(player.pid);
    } // end of startGame

    public List<String> getLeaderboardPlayers() {
        return Arrays.asList(ClientModel.gameService.getLeaderboards());
    }
} // end of LobbyPageModel class
