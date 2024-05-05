package Client_Java.controller.cards;

import Client_Java.view.cards.RankingCardView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

public class RankingCard {

    public RankingCard() {}

    public static Node createCard(String rank, String username, String winCount) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameRankingCard.fxml").toURI().toURL());

            Node card = loader.load();

            RankingCardView view = loader.getController();

            view.setRank(rank);
            view.setUsername(username);
            view.setWinCount(winCount);

            return card;
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
} // end of RankingCard class
