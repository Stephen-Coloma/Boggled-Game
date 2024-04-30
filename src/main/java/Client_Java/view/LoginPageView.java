package Client_Java.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginPageView {
    @FXML
    private TextField usernameField, passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Text noticeLB;

    public LoginPageView() {}

    @FXML
    public void initialize() {
        noticeLB.setText("");
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getLogInButton() {
        return logInButton;
    }

    public Text getNoticeLB() {
        return noticeLB;
    }
} // end of LoginPageView class
