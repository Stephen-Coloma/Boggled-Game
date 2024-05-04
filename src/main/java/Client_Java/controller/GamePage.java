package Client_Java.controller;

import Client_Java.BoggledApp.GameTimeOut;
import Client_Java.ClientJava;
import Client_Java.model.GamePageModel;
import Client_Java.view.GamePageView;
import Client_Java.view.RoundPopupView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage {
    private GamePageModel model;
    private GamePageView view;
    private boolean endGame = false;
    private static int remainingTime;
    private boolean roundRequested = false;

    public GamePage(GamePageModel model, GamePageView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameUI.fxml").toURI().toURL());

                Scene gameScene = new Scene(loader.load());

                view = loader.getController();

                ClientJava.APPLICATION_STAGE.setScene(gameScene);


            } catch (RuntimeException | IOException e) {
                e.printStackTrace();
            }

            startGame();
        });
    } // end of init

    private void startGame() {
        Thread gameThread = new Thread(() -> {
            while (true) {
                if (!model.getGameWinner().equals("None")) {
                    // TODO: display the game winner
                    System.out.println("Ending Game");
                    break;
                }

                if (!model.getRoundWinner().equals("None")) {
                    // TODO: display the round winner
                }

                if (!roundRequested) {
                    model.obtainRound();
//                    showRoundPopup();
                    System.out.println(model.getRound().characterSet);
                    roundRequested = true;
                }

                startCountdown();

                roundRequested = false;
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    } // end of startGame

    private void showRoundPopup() {
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    RoundPopup roundPopup = new RoundPopup(model.getRound(), new RoundPopupView());
                    roundPopup.init();
                });
            }
        }, 1000);
    } // end of showRoundPopup

    private void startCountdown() {
        while (true) {
            try {
                remainingTime = model.getRemainingRoundTime();

                Platform.runLater(() -> {
                    view.getTimeRemainingLB().setText(remainingTime + " seconds");
                });
                Thread.sleep(100);
            } catch (GameTimeOut gameTimeOut) {
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
} // end of GamePage class
