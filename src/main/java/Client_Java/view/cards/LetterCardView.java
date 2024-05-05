package Client_Java.view.cards;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LetterCardView {
    @FXML
    private Text letterLB;

    public LetterCardView() {}

    public void setLetter(char letter) {
        letterLB.setText(String.valueOf(letter));
    } // end of setLetter
} // end of LetterCardView class
