package Client_Java.controller;

import Client_Java.view.RoundPopupView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RoundPopup {
    private Round round;
    private RoundPopupView view;

    public RoundPopup(Round round, RoundPopupView view) {
        this.round = round;
        this.view = view;
    }

    public void init() {
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/GameRoundPopup.fxml").toURI().toURL());

            Scene roundScene = new Scene(loader.load());

            view = loader.getController();

            setRoundLB();

            popupStage.setTitle("ROUND " + round.roundNumber);
            popupStage.setFullScreen(false);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(roundScene);
            popupStage.show();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setRoundLB() {
        view.getRoundNumberLB().setText("ROUND " + round.roundNumber);
    }
} // end of RoundPopup class
