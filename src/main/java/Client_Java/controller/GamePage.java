package Client_Java.controller;

import Client_Java.BoggledApp.GameTimeOut;
import Client_Java.BoggledApp.InvalidWord;
import Client_Java.ClientJava;
import Client_Java.controller.popups.GameWinnerPopup;
import Client_Java.controller.popups.RoundPopup;
import Client_Java.controller.popups.RoundWinnerPopup;
import Client_Java.model.GamePageModel;
import Client_Java.view.GamePageView;
import Client_Java.view.popups.RoundPopupView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage {
    private final GamePageModel model;
    private GamePageView view;
    private RoundPopup roundPopup;
    private RoundWinnerPopup roundWinnerPopup;
    private GameWinnerPopup gameWinnerPopup;
    private static int remainingTime;

    public GamePage(GamePageModel model, GamePageView view) {
        this.model = model;
        this.view = view;

        roundPopup = new RoundPopup();
        roundWinnerPopup = new RoundWinnerPopup();
        gameWinnerPopup = new GameWinnerPopup();

        roundPopup.init();
        roundWinnerPopup.init();
        gameWinnerPopup.init();
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
                // check if there is a game winner
                if (!model.getGameWinner().equals("None")) {
                    // TODO: display the game winner
                    finalizeGame();
                    Platform.runLater(() -> ClientJava.APPLICATION_STAGE.setScene(LobbyPage.LOBBY_SCENE));
                    break;
                }

                // check if there is a round winner
                if (!model.getRoundWinner().equals("None")) {
                    showRoundWinnerPopup();
                    initiateDelay(4000);
                }

                // obtain the current round
                model.obtainRound();

                // update the UI
                updateData();

                // display the round popup
                showRoundPopup();

                // start the game countdown timer
                startCountdown();

                // slows down the thread and waits until the game is finished evaluating the current round
                while (true) {
                    if (model.isDoneEvaluatingRound()) {
                        break;
                    }
                }
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

                initiateDelay(100);
            } catch (GameTimeOut gameTimeOut) {
                break;
            }
        }
    } // end of startCountdown

    private void setEnterWordBT() {
        view.getEnterWordBT().setOnAction(event -> {
            try {
                model.submitWord(view.getInput().trim());
                view.addEntryToWordPanel(view.getInput());
            } catch (InvalidWord e) {
                // Display a notif that the word is invalid
                view.setNoticeMessage(view.getInput() + " is invalid");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        view.setNoticeMessage("");
                    }
                }, 3000);
            } finally {
                view.clearInputField();
            }
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
            view.setGamePoints(model.getGamePoints(false));

            // update character set
            view.updateCharacterSetPanel(model.getRound().characterSet);

            // clear word entries
            view.clearWordEntriesPanel();

            // update scoreboard
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
     * displays the round winner popup
     */
    private void showRoundWinnerPopup() {
        initiateDelay(1000);

        roundWinnerPopup.setRoundWinner(model.getRoundWinner());
        roundWinnerPopup.showPopup();
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                roundWinnerPopup.hidePopup();
            }
        }, 3000);
    } // end of showRoundWinnerPopup

    /**
     * displays the game winner popup
     */
    private void showGameWinnerPopup() {
        initiateDelay(1000);

        gameWinnerPopup.setGameWinner(model.getGameWinner());
        gameWinnerPopup.setWinnerPoints(model.getGamePoints(true));
        gameWinnerPopup.showPopup();
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                gameWinnerPopup.hidePopup();
            }
        }, 3000);
    } // end of showGameWinnerPopup

    /**
     * delays the thread
     * @param millis the duration of the delay
     */
    private void initiateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // end of initiateDelay

    /**
     * finalizes the game by updating the points and scoreboard before displaying the game winner
     */
    private void finalizeGame() {
        model.obtainRound();

        Platform.runLater(() -> {
            view.setGamePoints(model.getGamePoints(false));
            view.updateScoreboard(model.getRound().playersData);
        });

        showGameWinnerPopup();
        initiateDelay(4000);
        model.leaveGame();
    } // end of finalizeGame
} // end of GamePage class
