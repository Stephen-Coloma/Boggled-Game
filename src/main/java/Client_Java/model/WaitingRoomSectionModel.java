package Client_Java.model;

import Client_Java.BoggledApp.GameTimeOut;
import Client_Java.BoggledApp.Player;

public class WaitingRoomSectionModel {
    private Player player;
    private int gid;

    public WaitingRoomSectionModel(Player player, int gid) {
        this.player = player;
        this.gid = gid;
    }

    public void leaveGame() {
        ClientModel.gameService.leaveGame(player.pid, gid);
    }

    public int getRemainingWaitingTime() throws GameTimeOut {
        return ClientModel.gameService.getRemainingWaitingTime();
    }

    public int getNumberOfPlayersWaiting(){
        return ClientModel.gameService.getNumberOfPlayersJoined();
    }

    public Player getPlayer() {
        return player;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
} // end of WaitingRoomSectionModel class
