package Client_Java.model;

import Client_Java.BoggledApp.GameTimeOut;
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
