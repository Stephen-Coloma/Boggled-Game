package Client_Java.view;

import Client_Java.controller.cards.LetterCard;
import Client_Java.controller.cards.RankingCard;
import Client_Java.controller.cards.WordCard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GamePageView {
    @FXML
    private Text roundLB, playerNameLB, gamePointsLB, timeRemainingLB, noticeLB;
    @FXML
    private Button enterWordBT, clearInputBT;
    @FXML
    private TextField inputFieldTF;
    @FXML
    FlowPane characterSetPanel, wordEntryPanel, rankingPanel;

    @FXML
    public void initialize() {
        noticeLB.setText("");
        clearInputBT.setOnAction(event -> inputFieldTF.clear());
    }

    public GamePageView() {}

    public void updateCharacterSetPanel(String characterSet) {
        List<Node> letterCards = new ArrayList<>();

        for (char letter : characterSet.toUpperCase().toCharArray()) {
            letterCards.add(LetterCard.createCard(letter));
        }

        characterSetPanel.getChildren().clear();
        characterSetPanel.getChildren().addAll(letterCards);
    } // end of updateCharacterSetPanel

    public void updateScoreboard(String[] playerDatas) {
        List<Node> scoreboardCard = new ArrayList<>();

        for (int i = 0; i < playerDatas.length; i++) {
            String entry = playerDatas[i];
            String username = entry.split("-")[0];
            String winCount = entry.split("-")[1];

            scoreboardCard.add(RankingCard.createCard(String.valueOf(i + 1), username, winCount));
        }

        rankingPanel.getChildren().clear();
        rankingPanel.getChildren().addAll(scoreboardCard);
    } // end of updateScoreboard

    public String getInput() {
        return inputFieldTF.getText();
    }

    public void setRemainingTime(int remainingTime) {
        timeRemainingLB.setText(remainingTime + " seconds");
    } // end of setRemainingTime

    public void setPlayerName(String username) {
        playerNameLB.setText("PLAYER: " + username);
    }

    public void setRoundNumber(int roundNumber) {
        roundLB.setText("ROUND: " + roundNumber);
    }

    public void setGamePoints(String gamePoints) {
        gamePointsLB.setText(gamePoints);
    }

    public void setNoticeMessage(String message) {
        noticeLB.setText(message);
    }

    public void addEntryToWordPanel(String word) {
        wordEntryPanel.getChildren().add(WordCard.createCard(word));
    }

    public void clearWordEntriesPanel() {
        wordEntryPanel.getChildren().clear();
    }

    public void clearInputField() {
        inputFieldTF.clear();
    }

    public void enableInputField() {
        inputFieldTF.setDisable(false);
    }

    public void disableInputField() {
        inputFieldTF.setDisable(true);
    }

    public Button getEnterWordBT() {
        return enterWordBT;
    }
} // end of GamePageView class
