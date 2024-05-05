package Client_Java.view.cards;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WordCardView {
    @FXML
    private Text wordLB;

    public WordCardView() {}

    public void setWord(String word) {
        wordLB.setText(word);
    }
} // end of WordCardView class
