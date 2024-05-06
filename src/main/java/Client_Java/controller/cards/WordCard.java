package Client_Java.controller.cards;

import Client_Java.view.cards.WordCardView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

public class WordCard {

    public WordCard() {}

    public static Node createCard(String word) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameWordCard.fxml").toURI().toURL());

            Node card = loader.load();

            WordCardView view = loader.getController();

            view.setWord(word);

            return card;
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    } // end of createCard

} // end of WordCard class
