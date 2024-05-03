package Client_Java.controller.cards;

import Client_Java.view.cards.LobbyLeaderboardCardView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

public class LobbyLeaderboardCard {

    public LobbyLeaderboardCard() {}

    public static Node createCard(int rank, Player player) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/LobbyLeaderBoardCard.fxml").toURI().toURL());

            Node card = loader.load();

            LobbyLeaderboardCardView view = loader.getController();

            view.getRankLB().setText(String.valueOf(rank));
            view.getUsernameLB().setText(player.username);
            view.getPointsLB().setText(String.valueOf(player.points));

            return card;
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    } // end of createCard
} // end of LobbyLeaderboardCard class
