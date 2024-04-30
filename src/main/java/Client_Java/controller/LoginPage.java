package Client_Java.controller;

import Client_Java.BoggledApp.AccountDoesNotExist;
import Client_Java.BoggledApp.AlreadyLoggedIn;
import Client_Java.BoggledApp.Player;
import Client_Java.ClientJava;
import Client_Java.model.LobbyPageModel;
import Client_Java.model.LoginPageModel;
import Client_Java.view.LobbyPageView;
import Client_Java.view.LoginPageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LoginPage {
    public static Scene LOGIN_SCENE;
    private LoginPageModel model;
    private LoginPageView view;
    private Timer timer = new Timer();


    public LoginPage(LoginPageModel model, LoginPageView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/LogInUI.fxml").toURI().toURL());

            LOGIN_SCENE = new Scene(loader.load());

            view = loader.getController();

            ClientJava.APPLICATION_STAGE.setScene(LOGIN_SCENE);

            setUpLoginButton();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setUpLoginButton() {
        view.getLogInButton().setOnAction(event -> {
            // TODO: Use the model to authenticate the player in order to proceed to the lobby
            try {
                Player player = model.login(view.getUsernameField().getText(), view.getPasswordField().getText());

                LobbyPage lobbyPage = new LobbyPage(new LobbyPageModel(player), new LobbyPageView());
                lobbyPage.init();
            } catch (AlreadyLoggedIn exception1) {
                setNoticeText("Account is already logged in");
            } catch (AccountDoesNotExist exception2) {
                setNoticeText("Invalid credentials");
            }
        });
    } // end of setUpLoginButton

    private void setNoticeText(String text) {
        timer.cancel();

        view.getNoticeLB().setText(text);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.getNoticeLB().setText("");
            }
        }, 5000);
    } // end of setNoticeText
} // end of LoginPage class
