package Client_Java.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GamePageView {
    @FXML
    private Text gamePointsLB, timeRemainingLB;
    @FXML
    private Button enterWordBT, clearInputBT;
    @FXML
    private TextField inputFieldTF;

    public GamePageView() {}

    public Text getGamePointsLB() {
        return gamePointsLB;
    }

    public Text getTimeRemainingLB() {
        return timeRemainingLB;
    }

    public Button getEnterWordBT() {
        return enterWordBT;
    }

    public Button getClearInputBT() {
        return clearInputBT;
    }

    public TextField getInputFieldTF() {
        return inputFieldTF;
    }
} // end of GamePageView class
