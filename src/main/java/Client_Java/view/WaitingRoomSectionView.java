package Client_Java.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WaitingRoomSectionView {
    @FXML
    private Label countdownLB, playerCountLB;
    @FXML
    private Button cancelBT;

    public WaitingRoomSectionView() {}

    public Label getCountdownLB() {
        return countdownLB;
    }

    public Label getPlayerCountLB() {
        return playerCountLB;
    }

    public Button getCancelBT() {
        return cancelBT;
    }
} // end of WaitingRoomSectionView class
