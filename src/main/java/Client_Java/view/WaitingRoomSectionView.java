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

    public void setRemainingTime(int remainingTime) {
        countdownLB.setText(String.valueOf(remainingTime));
    }

    public void setWaitingPlayersCount(int waitingPlayers) {
        playerCountLB.setText(String.valueOf(waitingPlayers));
    }

    public Button getCancelBT() {
        return cancelBT;
    }
} // end of WaitingRoomSectionView class
