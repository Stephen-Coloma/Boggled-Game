package Client_Java.view;

import Client_Java.controller.cards.LobbyLeaderboardCard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LobbyPageView {
    @FXML
    private Button joinGameBT, logoutBT, refreshLeaderboardsBT;
    @FXML
    private Text rankLB, usernameLB, pointsLB;
    @FXML
    private FlowPane leaderboardPanel;

    public LobbyPageView() {}

    public void refreshLeaderboardTable(List<String> leaderboardEntries, String player) {
        List<Node> leaderboardCards = new ArrayList<>();

        for (int i = 1; i <= leaderboardEntries.size(); i++) {
            if (leaderboardEntries.get(i - 1).split("-")[0].equals(player)) {
                rankLB.setText(String.valueOf(i));
            }

            leaderboardCards.add(LobbyLeaderboardCard.createCard(i, leaderboardEntries.get(i - 1)));
        }
        leaderboardPanel.getChildren().clear();
        leaderboardPanel.getChildren().addAll(leaderboardCards);
    } // end of refreshLeaderboardTable

    public void setUsername(String username) {
        usernameLB.setText(username);
    }

    public void setPoints(int points) {
        pointsLB.setText(String.valueOf(points));
    }

    public Button getJoinGameBT() {
        return joinGameBT;
    }

    public Button getLogoutBT() {
        return logoutBT;
    }

    public Button getRefreshLeaderboardsBT() {
        return refreshLeaderboardsBT;
    }
} // end of LobbyPageView class
