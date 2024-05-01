package Client_Java.model;

import Client_Java.BoggledApp.Player;
import Client_Java.ClientJava;

import java.util.ArrayList;
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
        return ClientModel.gameService.startGame(player);
    } // end of startGame

    public List<Player> getLeaderboardPlayers() {
        return Arrays.asList(ClientModel.gameService.viewLeaderboards());
    }
} // end of LobbyPageModel class
