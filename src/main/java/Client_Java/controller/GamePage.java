package Client_Java.controller;

import Client_Java.ClientJava;
import Client_Java.model.GamePageModel;
import Client_Java.view.GamePageView;
import Client_Java.view.RoundPopupView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage {
    private GamePageModel model;
    private GamePageView view;

    public GamePage(GamePageModel model, GamePageView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameUI.fxml").toURI().toURL());

            Scene gameScene = new Scene(loader.load());

            view = loader.getController();

            ClientJava.APPLICATION_STAGE.setScene(gameScene);

            startGame();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void startGame() {
        Timer firstDelay = new Timer(true);
        firstDelay.schedule(new TimerTask() {
            @Override
            public void run() {
                showRoundPopup();
            }
        }, 1000);
    } // end of startGame

    private void showRoundPopup() {
        Platform.runLater(() -> {
            System.out.println("changing popup");
            RoundPopup roundPopup = new RoundPopup(model.getRound(), new RoundPopupView());
            roundPopup.init();
        });
    } // end of showRoundPopup
} // end of GamePage class
