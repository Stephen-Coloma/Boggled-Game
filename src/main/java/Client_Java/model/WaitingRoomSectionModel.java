package Client_Java.model;

import Client_Java.BoggledApp.GameTimeOut;

public class WaitingRoomSectionModel {
    private int pid;
    private int gid;

    public WaitingRoomSectionModel(int pid, int gid) {
        this.pid = pid;
        this.gid = gid;
    }

    public void leaveGame() {
        ClientModel.gameService.leaveGame(pid, gid);
    }

    public int getRemainingWaitingTime() throws GameTimeOut {
        return ClientModel.gameService.getRemainingWaitingTime();
    }

    public int getNumberOfPlayersJoined(){
        return ClientModel.gameService.getNumberOfPlayersJoined();
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
} // end of WaitingRoomSectionModel class
