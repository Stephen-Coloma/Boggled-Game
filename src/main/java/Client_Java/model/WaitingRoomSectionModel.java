package Client_Java.model;

import Client_Java.ClientJava;
import Client_Java.controller.GamePage;
import Client_Java.controller.LobbyPage;
import Client_Java.view.GamePageView;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomSectionModel {
    private int gid;

    public WaitingRoomSectionModel(int gid) {
        this.gid = gid;
    }

    public int getWaitingTime(){
        return ClientModel.gameService.getWaitingTime(gid);
    }

    public int getTotalPlayersJoined(){
        return ClientModel.gameService.getTotalPlayersJoined(gid);
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
} // end of WaitingRoomSectionModel class
