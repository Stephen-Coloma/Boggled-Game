package Client_Java.controller;

import Client_Java.model.LobbyPageModel;
import Client_Java.view.LobbyPageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class LobbyPage {
    public static Scene LOBBY_SCENE;
    private LobbyPageModel model;
    private LobbyPageView view;


    public LobbyPage(LobbyPageModel model, LobbyPageView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/GameLobbyUI.fxml").toURI().toURL());

            LOBBY_SCENE = new Scene(loader.load());

            view = loader.getController();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init
} // end of LobbyPage class
