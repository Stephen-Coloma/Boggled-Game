package Server_Java.model.mainmenu;

import Server_Java.model.ServerJDBC;

import java.util.List;

public class PlayerListModel {
    private List<ServerJDBC.PlayerData> playerDataList;

    public PlayerListModel(List<ServerJDBC.PlayerData> playerDataList) {
        this.playerDataList = playerDataList;
    }

    public List<ServerJDBC.PlayerData> getPlayerDataList() {
        return playerDataList;
    }

    public void setPlayerDataList(List<ServerJDBC.PlayerData> playerDataList) {
        this.playerDataList = playerDataList;
    }
}
