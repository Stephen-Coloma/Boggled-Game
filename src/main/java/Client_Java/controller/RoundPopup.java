package Client_Java.controller;

import Client_Java.view.RoundPopupView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class RoundPopup {
    private RoundPopupView view;
    private Stage popupStage;

    public RoundPopup(RoundPopupView view) {
        this.view = view;
    }

    public void init() {
        try {
            popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/GameRoundPopup.fxml").toURI().toURL());

            Scene roundScene = new Scene(loader.load());

            view = loader.getController();

            popupStage.setFullScreen(false);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(roundScene);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    public void setCurrentRound(int roundNumber) {
        view.getRoundNumberLB().setText("ROUND " + roundNumber);
    }

    public void showPopup() {
        Platform.runLater(() -> popupStage.show());
    }

    public void hidePopup() {
        Platform.runLater(() -> popupStage.hide());
    }
} // end of RoundPopup class
