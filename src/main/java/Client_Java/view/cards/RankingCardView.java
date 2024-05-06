package Client_Java.view.cards;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RankingCardView {
    @FXML
    private Text rankLB, usernameLB, winCountLB;

    public RankingCardView() {}

    public void setRank(String rank) {
        rankLB.setText(rank);
    }

    public void setUsername(String username) {
        usernameLB.setText(username);
    }

    public void setWinCount(String winCount) {
        winCountLB.setText(winCount);
    }
} // end of RankingCardView class
