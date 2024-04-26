package Client_Java.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class LobbyPageView {
    @FXML
    private Button joinGameBT;
    @FXML
    private ImageView refreshLeaderboardsBT;

    public LobbyPageView() {}

    public Button getJoinGameBT() {
        return joinGameBT;
    }

    public ImageView getRefreshLeaderboardsBT() {
        return refreshLeaderboardsBT;
    }
} // end of LobbyPageView class
