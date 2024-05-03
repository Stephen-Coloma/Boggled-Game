package Client_Java.controller.cards;

import Client_Java.view.cards.LobbyLeaderboardCardView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

public class LobbyLeaderboardCard {

    public LobbyLeaderboardCard() {}

    public static Node createCard(int rank, String player) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/LobbyLeaderBoardCard.fxml").toURI().toURL());

            Node card = loader.load();

            LobbyLeaderboardCardView view = loader.getController();

            String username = player.split("-")[0];
            String points = player.split("-")[1];

            view.getRankLB().setText(String.valueOf(rank));
            view.getUsernameLB().setText(username);
            view.getPointsLB().setText(String.valueOf(points));

            return card;
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    } // end of createCard
} // end of LobbyLeaderboardCard class
