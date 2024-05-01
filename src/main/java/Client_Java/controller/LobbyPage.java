package Client_Java.controller;

import Client_Java.ClientJava;
import Client_Java.model.LobbyPageModel;
import Client_Java.model.WaitingRoomSectionModel;
import Client_Java.view.LobbyPageView;
import Client_Java.view.WaitingRoomSectionView;
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

            ClientJava.APPLICATION_STAGE.setScene(LOBBY_SCENE);

            setUpExitApplication();
            setUpLeaderboardsBT();
            setUpJoinGameBT();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setUpExitApplication() {
        ClientJava.APPLICATION_STAGE.setOnCloseRequest(windowEvent -> {
            model.logout();
            System.exit(0);
        });
    } // end of setUpExitApplication

    private void setUpLeaderboardsBT() {
        view.getRefreshLeaderboardsBT().setOnMouseClicked(event -> {
            // TODO: refresh the leaderboards
            view.refreshLeaderboardTable(model.getLeaderboardPlayers());
        });
    } // end of setUpLeaderboardsBT

    private void setUpJoinGameBT() {
        view.getJoinGameBT().setOnAction(event -> {
            // TODO: add a mechanism to send a request to server that the player wants to play a game before loading the waiting room section
            int gid = model.startGame();
            WaitingRoomSection waitingRoomSection = new WaitingRoomSection(new WaitingRoomSectionModel(gid), new WaitingRoomSectionView());
            waitingRoomSection.init();
        });
    } // end of setUpJoinGameBT
} // end of LobbyPage class
