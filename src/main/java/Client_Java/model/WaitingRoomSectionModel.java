package Client_Java.model;

import Client_Java.controller.GamePage;
import Client_Java.view.GamePageView;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomSectionModel {
    private Timer countdownTimer;
    private IntegerProperty duration = new SimpleIntegerProperty(10); // FIXME: test value only
    private IntegerProperty playerCount = new SimpleIntegerProperty(1); // FIXME: test value only

    public WaitingRoomSectionModel() {}

    public void startCountdown() {
        countdownTimer = new Timer(true);
        countdownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    int currentDuration = duration.get();
                    if (currentDuration > 0) {
                        duration.set(currentDuration - 1);
                    } else {
                        countdownTimer.cancel();
                        // TODO: if the countdown reaches to 0 and there are more than 1 players, load the game UI
                        GamePage gamePage = new GamePage(new GamePageModel(), new GamePageView());
                        gamePage.init();
                    }
                });
            }
        }, 1000, 1000);
    } // end of startCountdown

    public void listenForPlayerCounts() {
        Thread playerCountThread = new Thread(() -> {
            while (true) {
                // TODO: obtain the player count from the server

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
