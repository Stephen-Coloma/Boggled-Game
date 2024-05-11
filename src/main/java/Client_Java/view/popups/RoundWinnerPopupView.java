package Client_Java.view.popups;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RoundWinnerPopupView {
    @FXML
    private Text usernameLB;

    public RoundWinnerPopupView() {}

    public void setUsername(String username) {
        usernameLB.setText(username);
    }
} // end of RoundWinnerPopupView class
