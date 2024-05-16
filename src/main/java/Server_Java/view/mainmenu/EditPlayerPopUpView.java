package Server_Java.view.mainmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPlayerPopUpView {

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField fullnameTF;

    @FXML
    private TextField gamepointsTF;

    @FXML
    private Label gamesPlayedLbl;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label noticeLbl;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField usernameTF;

    //set up action for buttons
    public void setUpActionSaveButton(EventHandler<ActionEvent> event) {
        this.saveBtn.setOnAction(event);
    }

    public void setUpActionCancelButton(EventHandler<ActionEvent> event) {
        this.cancelBtn.setOnAction(event);
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public void setCancelBtn(Button cancelBtn) {
        this.cancelBtn = cancelBtn;
    }

    public TextField getFullnameTF() {
        return fullnameTF;
    }

    public void setFullnameTF(TextField fullnameTF) {
        this.fullnameTF = fullnameTF;
    }

    public TextField getGamepointsTF() {
        return gamepointsTF;
    }

    public void setGamepointsTF(TextField gamepointsTF) {
        this.gamepointsTF = gamepointsTF;
    }

    public Label getGamesPlayedLbl() {
        return gamesPlayedLbl;
    }

    public void setGamesPlayedLbl(Label gamesPlayedLbl) {
        this.gamesPlayedLbl = gamesPlayedLbl;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Label getNoticeLbl() {
        return noticeLbl;
    }

    public void setNoticeLbl(Label noticeLbl) {
        this.noticeLbl = noticeLbl;
    }

    public TextField getPasswordTF() {
        return passwordTF;
    }

    public void setPasswordTF(TextField passwordTF) {
        this.passwordTF = passwordTF;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public void setSaveBtn(Button saveBtn) {
        this.saveBtn = saveBtn;
    }

    public TextField getUsernameTF() {
        return usernameTF;
    }

    public void setUsernameTF(TextField usernameTF) {
        this.usernameTF = usernameTF;
    }
}
