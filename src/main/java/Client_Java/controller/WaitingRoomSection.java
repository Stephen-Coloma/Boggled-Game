package Client_Java.controller;

import Client_Java.BoggledApp.GameTimeOut;
import Client_Java.ClientJava;
import Client_Java.model.GamePageModel;
import Client_Java.model.WaitingRoomSectionModel;
import Client_Java.view.GamePageView;
import Client_Java.view.WaitingRoomSectionView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class WaitingRoomSection {
    private final WaitingRoomSectionModel model;
    private WaitingRoomSectionView view;
    private static int remainingTime;
    private int playerCount;

    public WaitingRoomSection(WaitingRoomSectionModel model, WaitingRoomSectionView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/WaitingRoomPage.fxml").toURI().toURL());

            Scene waitingRoomScene = new Scene(loader.load());

            waitingRoomScene.getStylesheets().add(new File("src/main/java/Client_Java/res/css/waiting-room.css").toURI().toURL().toExternalForm());

            view = loader.getController();

            ClientJava.APPLICATION_STAGE.setScene(waitingRoomScene);

//            setUpCancelBT(); fixme:commented out for passing purposes
            view.getCancelBT().setDisable(true);
            view.getCancelBT().setVisible(false);
            initiateCountdown();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setUpCancelBT() {
        view.getCancelBT().setOnAction(event -> {
            model.leaveGame();
            ClientJava.APPLICATION_STAGE.setScene(LobbyPage.LOBBY_SCENE);
        });
    } // end of setUpCancelBT

    private void initiateCountdown() {
        Thread countdownThread = new Thread(() -> {
            try {
                while (true) {
                    remainingTime = model.getRemainingWaitingTime();
                    playerCount = model.getNumberOfPlayersWaiting();

                    Platform.runLater(() -> {
                        view.setRemainingTime(remainingTime);
                        view.setWaitingPlayersCount(playerCount);
                    });
                    Thread.sleep(100);
                }
            } catch (GameTimeOut gameTimeOutException) {
                handleFirstRound();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        countdownThread.setDaemon(true);
        countdownThread.start();
    } // end of initiateCountdown

    private void handleFirstRound() {
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (playerCount > 1) {
                    Platform.runLater(() -> {
                        GamePage gamePage = new GamePage(new GamePageModel(model.getPlayer(), model.getGid()), new GamePageView());
                        gamePage.init();
                    });
                } else {
                    Platform.runLater(() -> ClientJava.APPLICATION_STAGE.setScene(LobbyPage.LOBBY_SCENE));
                }
            }
        }, 1000);
    } // end of handleFirstRound
} // end of WaitingRoomSection class
