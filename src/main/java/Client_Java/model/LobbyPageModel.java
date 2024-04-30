package Client_Java.model;

import Client_Java.BoggledApp.Player;

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
} // end of LobbyPageModel class
