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
    private Timer countdownTimer;
    private IntegerProperty duration = new SimpleIntegerProperty(10); // FIXME: test value only
    private IntegerProperty playerCount = new SimpleIntegerProperty(1); // FIXME: test value only

    public WaitingRoomSectionModel(int gid) {
        this.gid = gid;
        System.out.println(gid);
    }

    public void startCountdown() {
        Thread countdownThread = new Thread(() -> {
            while (duration.get() > 0) {
                // TODO: obtain the remaining time and update it to the UI
                Platform.runLater(() -> {
                    duration.set(ClientModel.gameService.getWaitingTime(gid));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(() -> {
                if (playerCount.get() > 1) {
                    GamePage gamePage = new GamePage(new GamePageModel(gid), new GamePageView());
                } else {
                    ClientJava.APPLICATION_STAGE.setScene(LobbyPage.LOBBY_SCENE);
                }
            });
        });
        countdownThread.setDaemon(true);
        countdownThread.start();
    } // end of startCountdown

    public void listenForPlayerCounts() {
        Thread playerCountThread = new Thread(() -> {
            while (true) {
                // TODO: obtain the player count from the server
                Platform.runLater(() -> playerCount.set(ClientModel.gameService.getTotalPlayersJoined(gid)));
                if (duration.get() == 0) {
                    break;
                }
            }
        });
        playerCountThread.setDaemon(true);
        playerCountThread.start();
    } // end of listenForPlayerCounts

    public IntegerProperty getDuration() {
        return duration;
    }

    public IntegerProperty getPlayerCount() {
        return playerCount;
    }

    public Timer getCountdownTimer() {
        return countdownTimer;
    }
} // end of WaitingRoomSectionModel class
