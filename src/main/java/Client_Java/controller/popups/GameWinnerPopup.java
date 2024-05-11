package Client_Java.controller.popups;

import Client_Java.view.popups.GameWinnerPopupView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class GameWinnerPopup {
    private GameWinnerPopupView view;
    private Stage popupStage;

    public GameWinnerPopup() {}

    public void init() {
        try {
            popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/GameWinnerPopup.fxml").toURI().toURL());

            Scene gameWinnerScene = new Scene(loader.load());

            view = loader.getController();

            popupStage.setFullScreen(false);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(gameWinnerScene);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    public void setGameWinner(String username) {
        view.setUsername(username);
    }

    public void setWinnerPoints(String points) {
        view.setPoints(points);
    }

    public void showPopup() {
        Platform.runLater(() -> popupStage.show());
    }

    public void hidePopup() {
        Platform.runLater(() -> popupStage.hide());
    }
} // end of GameWinnerPopup class
