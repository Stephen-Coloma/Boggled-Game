package Client_Java.controller.popups;

import Client_Java.view.popups.RoundPopupView;
import Client_Java.view.popups.RoundWinnerPopupView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class RoundWinnerPopup {
    private RoundWinnerPopupView view;
    private Stage popupStage;

    public RoundWinnerPopup() {}

    public void init() {
        try {
            popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/RoundWinnerPopup.fxml").toURI().toURL());

            Scene roundWinnerScene = new Scene(loader.load());

            view = loader.getController();

            popupStage.setFullScreen(false);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(roundWinnerScene);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    public void setRoundWinner(String username) {
        view.setUsername(username);
    }

    public void showPopup() {
        Platform.runLater(() -> popupStage.show());
    }

    public void hidePopup() {
        Platform.runLater(() -> popupStage.hide());
    }
} // end of RoundWinnerPopup class
