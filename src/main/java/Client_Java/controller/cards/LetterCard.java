package Client_Java.controller.cards;

import Client_Java.view.cards.LetterCardView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

public class LetterCard {

    public LetterCard() {}

    public static Node createCard(char letter) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameLetterCard.fxml").toURI().toURL());

            Node card = loader.load();

            LetterCardView view = loader.getController();

            view.setLetter(letter);

            return card;
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    } // end of createCard
} // end of LetterCard class
