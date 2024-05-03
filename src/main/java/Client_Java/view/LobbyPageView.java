package Client_Java.view;

import Client_Java.controller.cards.LobbyLeaderboardCard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import java.util.ArrayList;
import java.util.List;

public class LobbyPageView {
    @FXML
    private Button joinGameBT, refreshLeaderboardsBT;
    @FXML
    private FlowPane leaderboardPanel;

    public LobbyPageView() {}

    public void refreshLeaderboardTable(List<Player> playerList) {
        List<Node> leaderboardCards = new ArrayList<>();

        for (int i = 1; i <= playerList.size(); i++) {
            leaderboardCards.add(LobbyLeaderboardCard.createCard(i, playerList.get(i - 1)));
        }
        leaderboardPanel.getChildren().clear();
        leaderboardPanel.getChildren().addAll(leaderboardCards);
    } // end of refreshLeaderboardTable

    public Button getJoinGameBT() {
        return joinGameBT;
    }

    public Button getRefreshLeaderboardsBT() {
        return refreshLeaderboardsBT;
    }
} // end of LobbyPageView class
