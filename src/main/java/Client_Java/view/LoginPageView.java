package Client_Java.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginPageView {
    @FXML
    private TextField usernameField, passwordField;
    @FXML
    private Button logInButton;

    public LoginPageView() {}

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getLogInButton() {
        return logInButton;
    }
} // end of LoginPageView class
