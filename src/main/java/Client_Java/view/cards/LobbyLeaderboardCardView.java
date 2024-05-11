package Client_Java.view.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyLeaderboardCardView {
    @FXML
    private Label rankLB, usernameLB, pointsLB;

    public LobbyLeaderboardCardView() {}

    public void setRank(int rank) {
        rankLB.setText(String.valueOf(rank));
    }

    public void setUsername(String username) {
        usernameLB.setText(username);
    }

    public void setPoints(String points) {
        pointsLB.setText(points);
    }
} // end of LobbyLeaderboardCardView class
