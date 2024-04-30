package Client_Java.controller;

import Client_Java.BoggledApp.Player;
import Client_Java.BoggledApp.Round;
import Client_Java.ClientJava;
import Client_Java.model.ClientModel;
import Client_Java.model.WaitingRoomSectionModel;
import Client_Java.view.WaitingRoomSectionView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class WaitingRoomSection {
    private WaitingRoomSectionModel model;
    private WaitingRoomSectionView view;
    private static int remainingTime;

    public WaitingRoomSection(WaitingRoomSectionModel model, WaitingRoomSectionView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/WaitingRoomPage.fxml").toURI().toURL());

            Scene waitingRoomScene = new Scene(loader.load());

            view = loader.getController();

            ClientJava.APPLICATION_STAGE.setScene(waitingRoomScene);

            setUpCancelBT();
            initiateCountdown();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setUpCancelBT() {
        view.getCancelBT().setOnAction(event -> {
            /*
            * TODO: make sure to remove the player into the players list of a game in the server side which is there is no method for this yet.
            *  not sure if how it will interact with the callable */

            ClientJava.APPLICATION_STAGE.setScene(LobbyPage.LOBBY_SCENE);
        });
    } // end of setUpCancelBT


    private void initiateCountdown() {
        //an atomic boolean to stop the thread
        AtomicBoolean stopCountdown = new AtomicBoolean(false);

        //a thread that manages the countdown
        Thread countdownThread = new Thread(()->{
            try {
                while (!stopCountdown.get()){
                    remainingTime = model.getWaitingTime();
                    Platform.runLater(()->{
                        view.getCountdownLB().setText(String.valueOf(remainingTime));
                        view.getPlayerCountLB().setText(String.valueOf(model.getTotalPlayersJoined()));
                    });
                    Thread.sleep(100);

                    if (remainingTime <= 0) {
                        stopCountdown.set(true); // Set the flag to stop the countdown
                        handleFirstRound();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        countdownThread.setDaemon(true);
        countdownThread.start();

    } // end of initiateCountdown

    private void handleFirstRound() {
        Round round = ClientModel.gameService.playFirstRound(model.getGid());
        if (round.gid < 0){
            //todo: return back the UI to lobby
            System.out.println("GAME IS INVALID, RETURNING TO LOBBY");
        }else {
            //todo: set up the UI for game
            System.out.println("GAME IS VALID, GOING TO GAME UI");
        }
    }
} // end of WaitingRoomSection class
