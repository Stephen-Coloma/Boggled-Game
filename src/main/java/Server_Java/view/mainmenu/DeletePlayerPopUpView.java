package Server_Java.view.mainmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DeletePlayerPopUpView {

    @FXML
    private Text label;

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;


    //set up action for buttons
    public void setUpActionYesButton(EventHandler<ActionEvent> event) {
        this.yesButton.setOnAction(event);
    }

    public void setUpActionNoButton(EventHandler<ActionEvent> event) {
        this.noButton.setOnAction(event);
    }

    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
    }

    public Button getNoButton() {
        return noButton;
    }

    public void setNoButton(Button noButton) {
        this.noButton = noButton;
    }

    public Button getYesButton() {
        return yesButton;
    }

    public void setYesButton(Button yesButton) {
        this.yesButton = yesButton;
    }
}
