package Client_Java.controller;

import Client_Java.BoggledApp.GameTimeOut;
import Client_Java.BoggledApp.Round;
import Client_Java.ClientJava;
import Client_Java.model.GamePageModel;
import Client_Java.view.GamePageView;
import Client_Java.view.RoundPopupView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage {
    private GamePageModel model;
    private GamePageView view;
    RoundPopup roundPopup;
    private static int remainingTime;
    private boolean roundRequested = false;

    public GamePage(GamePageModel model, GamePageView view) {
        this.model = model;
        this.view = view;

        roundPopup = new RoundPopup(new RoundPopupView());
        roundPopup.init();
    }

    /**
     * initializes the ui and loads it to the application stage
     */
    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameUI.fxml").toURI().toURL());

            Scene gameScene = new Scene(loader.load());

            view = loader.getController();

            view.setPlayerName(model.getUsername());

            ClientJava.APPLICATION_STAGE.setScene(gameScene);

            setEnterWordBT();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }

        startGame();
    } // end of init

    /**
     * one time method to handle the game until the game is finished
     */
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
                    updateData();
                    showRoundPopup();
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

    /**
     * initiates the countdown sequence indicating the round is starting
     */
    private void startCountdown() {
        while (true) {
            try {
                remainingTime = model.getRemainingRoundTime();

                Platform.runLater(() -> view.setRemainingTime(remainingTime));

                Thread.sleep(100);
            } catch (GameTimeOut gameTimeOut) {
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // end of startCountdown

    private void setEnterWordBT() {
        view.getEnterWordBT().setOnAction(event -> {
            view.addEntryToWordPanel(view.getInput());
            view.clearInputField();
        });
    }

    /**
     * updates the necessary data in the game UI for the current round
     */
    private void updateData() {
        Platform.runLater(() -> {
            // set current round number
            view.setRoundNumber(model.getRound().roundNumber);

            // set game points
            String[] playerDatas = model.getRound().playersData;
            for (String entry : playerDatas) {
                String username = entry.split("-")[0];
                String points = entry.split("-")[2];

                if (username.equals(model.getUsername())) {
                    view.setGamePoints(points);
                    break;
                }
            }

            // update character set
            view.updateCharacterSetPanel(model.getRound().characterSet);

            // clear word entries
            view.clearWordEntriesPanel();

            // TODO: update scoreboard
            view.updateScoreboard(model.getRound().playersData);
        });
    } // end of updateData

    /**
     * displays the round popup for the current round
     */
    private void showRoundPopup() {
        initiateDelay(1000);

        roundPopup.setCurrentRound(model.getRound().roundNumber);
        roundPopup.showPopup();
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                roundPopup.hidePopup();
            }
        }, 3000);
    } // end of showRoundPopup

    /**
     * blocks the thread from running in a given amount of time
     *
     * @param millis the amount of delay in milliseconds
     */
    private void initiateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // end of initiateDelay
} // end of GamePage class
