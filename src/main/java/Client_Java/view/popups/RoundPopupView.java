package Client_Java.view.popups;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RoundPopupView {
    @FXML
    private Text roundNumberLB;

    public RoundPopupView() {}

    public void setRoundNumber(int roundNumber) {
        roundNumberLB.setText("ROUND " + roundNumber);
    }
} // end of RoundPopupView class
