package Server_Java.model;

public class EditPlayerPopUpModel {
    private ServerJDBC.PlayerData playerData;

    public EditPlayerPopUpModel(ServerJDBC.PlayerData playerData) {
        this.playerData = playerData;
    }

    public ServerJDBC.PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(ServerJDBC.PlayerData playerData) {
        this.playerData = playerData;
    }
}
