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
    private final LoginPageModel model;
    private LoginPageView view;
    private final LobbyPage lobbyPage;
    private final LobbyPageModel lobbyPageModel;

    private Timer timer = new Timer();


    public LoginPage(LoginPageModel model, LoginPageView view) {
        this.model = model;
        this.view = view;

        lobbyPageModel = new LobbyPageModel();
        LobbyPageView lobbyPageView = new LobbyPageView();
        lobbyPage = new LobbyPage(lobbyPageModel, lobbyPageView);
        lobbyPage.init();
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/LogInUI.fxml").toURI().toURL());

            LOGIN_SCENE = new Scene(loader.load());

            LOGIN_SCENE.getStylesheets().add(new File("src/main/java/Client_Java/res/css/login.css").toURI().toURL().toExternalForm());

            view = loader.getController();

            ClientJava.APPLICATION_STAGE.setScene(LOGIN_SCENE);

            setUpLoginButton();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setUpLoginButton() {
        view.getLogInButton().setOnAction(event -> {
            try {
                Player player = model.login(view.getUsernameFieldValue(), view.getPasswordFieldValue());

                lobbyPageModel.setPlayer(player);
                lobbyPage.switchScene();
                lobbyPage.displayInitialLeaderboards();
                lobbyPage.setUpRank();
            } catch (AlreadyLoggedIn exception1) {
                setNotice("Account is already logged in");
            } catch (AccountDoesNotExist exception2) {
                setNotice("Invalid credentials");
            }
        });
    } // end of setUpLoginButton

    private void setNotice(String text) {
        timer.cancel();

        view.setNoticeText(text);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.setNoticeText("");
            }
        }, 5000);
    } // end of setNoticeText
} // end of LoginPage class
