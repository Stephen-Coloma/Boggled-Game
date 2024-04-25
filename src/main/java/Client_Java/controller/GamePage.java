package Client_Java.controller;

import Client_Java.ClientApp;
import Client_Java.model.GamePageModel;
import Client_Java.view.GamePageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class GamePage {
    private GamePageModel model;
    private GamePageView view;

    public GamePage(GamePageModel model, GamePageView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/BoggledGameUI.fxml").toURI().toURL());

            Scene gameScene = new Scene(loader.load());

            view = loader.getController();

            ClientApp.APPLICATION_STAGE.setScene(gameScene);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init
} // end of GamePage class
