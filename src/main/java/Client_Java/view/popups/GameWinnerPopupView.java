package Client_Java.view.popups;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GameWinnerPopupView {
    @FXML
    private Text usernameLB, pointsLB;

    public GameWinnerPopupView() {}

    public void setUsername(String username) {
        usernameLB.setText(username);
    }

    public void setPoints(String points) {
        pointsLB.setText(points);
    }
} // end of GameWinnerPopupView class
