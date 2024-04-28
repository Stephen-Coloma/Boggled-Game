package Server_Java.view.mainmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateAccountConfirmationPopUpView {

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

}
