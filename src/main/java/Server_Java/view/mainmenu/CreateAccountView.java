package Server_Java.view.mainmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateAccountView {

    @FXML
    private Button createAccountBtn;

    @FXML
    private TextField fullnameTextField;

    @FXML
    private Label noticeLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    //set up action button
    public void setUpActionCreateAccountBtn(EventHandler<ActionEvent> event){
        this.createAccountBtn.setOnAction(event);
    }

    public Button getCreateAccountBtn() {
        return createAccountBtn;
    }

    public void setCreateAccountBtn(Button createAccountBtn) {
        this.createAccountBtn = createAccountBtn;
    }

    public TextField getFullnameTextField() {
        return fullnameTextField;
    }

    public void setFullnameTextField(TextField fullnameTextField) {
        this.fullnameTextField = fullnameTextField;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(Label noticeLabel) {
        this.noticeLabel = noticeLabel;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(TextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }
}
