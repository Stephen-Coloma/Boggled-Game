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

    public void setNoticeText(String notice) {
        noticeLB.setText(notice);
    }

    public String getUsernameFieldValue() {
        return usernameField.getText();
    }

    public String getPasswordFieldValue() {
        return passwordField.getText();
    }

    public Button getLogInButton() {
        return logInButton;
    }
} // end of LoginPageView class
