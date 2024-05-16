package Client_Java.controller;

import Client_Java.BoggledApp.GameTimeOut;
import Client_Java.BoggledApp.InvalidWord;
import Client_Java.ClientJava;
import Client_Java.controller.popups.GameWinnerPopup;
import Client_Java.controller.popups.RoundPopup;
import Client_Java.controller.popups.RoundWinnerPopup;
import Client_Java.model.GamePageModel;
import Client_Java.view.GamePageView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage {
    private final GamePageModel model;
    private GamePageView view;
    private RoundPopup roundPopup;
    private RoundWinnerPopup roundWinnerPopup;
    private GameWinnerPopup gameWinnerPopup;
    private static int remainingTime;
    private File roundSE, wordSE, roundWinnerSE, gameWinnerSE;

    public GamePage(GamePageModel model, GamePageView view) {
        this.model = model;
        this.view = view;

        roundPopup = new RoundPopup();
        roundWinnerPopup = new RoundWinnerPopup();
        gameWinnerPopup = new GameWinnerPopup();

        roundPopup.init();
        roundWinnerPopup.init();
        gameWinnerPopup.init();

        roundSE = new File("src/main/java/Client_Java/res/audio/round-sound.wav");
        wordSE = new File("src/main/java/Client_Java/res/audio/word-sound.wav");
        roundWinnerSE = new File("src/main/java/Client_Java/res/audio/round-winner-sound.wav");
        gameWinnerSE = new File("src/main/java/Client_Java/res/audio/game-winner-sound.wav");
    }

    /**
     * initializes the ui and loads it to the application stage
     */
    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameUI.fxml").toURI().toURL());

            Scene gameScene = new Scene(loader.load());

            gameScene.getStylesheets().add(new File("src/main/java/Client_Java/res/css/game.css").toURI().toURL().toExternalForm());

            view = loader.getController();

            view.setPlayerName(model.getUsername());

            ClientJava.APPLICATION_STAGE.setScene(gameScene);

            setEnterWordFunc();
            setUpExitApplication();
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
        view.clearInputField();
        view.enableInputField();

        while (true) {
            try {
                remainingTime = model.getRemainingRoundTime();

                Platform.runLater(() -> view.setRemainingTime(remainingTime));

                initiateDelay(100);
            } catch (GameTimeOut gameTimeOut) {
                view.disableInputField();
                break;
            }
        }
    } // end of startCountdown

    private void setEnterWordFunc() {
        view.getInputFieldTF().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String word = view.getInput().trim();
                try {
                    if (!word.isEmpty()) {
                        if (model.isAlreadySubmitted(word)) {
                            setNotice(word + " is already included in the word entries");
                        } else {
                            model.submitWord(word);
                            playSoundEffect(wordSE);
                            view.addEntryToWordPanel(word);
                        }
                    }
                } catch (InvalidWord e) {
                    // Display a notif that the word is invalid
                    setNotice(word + " is invalid");
                } finally {
                    view.clearInputField();
                }
            }
        });
    } // end of setEnterWordFunc

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

        // TODO: play audio
        playSoundEffect(roundSE);

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

        // TODO: play audio
        playSoundEffect(roundWinnerSE);

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

        //TODO: play audio
        playSoundEffect(gameWinnerSE);

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

    private void playSoundEffect(File audioFile) {
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    } // end of playSoundEffect

    private void setNotice(String message) {
        view.setNoticeMessage(message);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                view.setNoticeMessage("");
            }
        }, 3000);
    } // end of setNotice

    private void setUpExitApplication() {
        ClientJava.APPLICATION_STAGE.setOnCloseRequest(windowEvent -> {
            model.leaveGame();
            model.logout();
            System.exit(0);
        });
    } // end of setUpExitApplication
} // end of GamePage class
