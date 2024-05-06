package Client_Java.view.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyLeaderboardCardView {
    @FXML
    private Label rankLB, usernameLB, pointsLB;

    public LobbyLeaderboardCardView() {}

    public Label getRankLB() {
        return rankLB;
    }

    public Label getUsernameLB() {
        return usernameLB;
    }

    public Label getPointsLB() {
        return pointsLB;
    }
} // end of LobbyLeaderboardCardView class
