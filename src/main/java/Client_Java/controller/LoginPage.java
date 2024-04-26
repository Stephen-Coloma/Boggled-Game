package Client_Java.controller;

import Client_Java.ClientApp;
import Client_Java.model.LobbyPageModel;
import Client_Java.model.LoginPageModel;
import Client_Java.view.LobbyPageView;
import Client_Java.view.LoginPageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class LoginPage {
    public static Scene LOGIN_SCENE;
    private LoginPageModel model;
    private LoginPageView view;


    public LoginPage(LoginPageModel model, LoginPageView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/LogInUI.fxml").toURI().toURL());

            LOGIN_SCENE = new Scene(loader.load());

            view = loader.getController();

            ClientApp.APPLICATION_STAGE.setScene(LOGIN_SCENE);

            setUpLoginButton();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setUpLoginButton() {
        view.getLogInButton().setOnAction(event -> {
            // TODO: Use the model to authenticate the player in order to proceed to the lobby
            LobbyPage lobbyPage = new LobbyPage(new LobbyPageModel(), new LobbyPageView());
            lobbyPage.init();
        });
    } // end of setUpLoginButton
} // end of LoginPage class
