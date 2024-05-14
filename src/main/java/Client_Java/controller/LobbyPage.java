package Client_Java.controller;

import Client_Java.ClientJava;
import Client_Java.model.LobbyPageModel;
import Client_Java.model.WaitingRoomSectionModel;
import Client_Java.view.LobbyPageView;
import Client_Java.view.WaitingRoomSectionView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.File;
import java.io.IOException;

public class LobbyPage {
    public static Scene LOBBY_SCENE;
    private final LobbyPageModel model;
    private LobbyPageView view;

    public LobbyPage(LobbyPageModel model, LobbyPageView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/GameLobbyUI.fxml").toURI().toURL());

            LOBBY_SCENE = new Scene(loader.load());

            LOBBY_SCENE.getStylesheets().add(new File("src/main/java/Client_Java/res/css/lobby.css").toURI().toURL().toExternalForm());

            view = loader.getController();

            setUpJoinGameBT();
            setUpLogoutBT();
            setUpLeaderboardsBT();
            setUpExitApplication();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    public void switchScene() {
        ClientJava.APPLICATION_STAGE.setScene(LOBBY_SCENE);
    } // end of switchScene

    private void setUpJoinGameBT() {
        view.getJoinGameBT().setOnAction(event -> {
            // TODO: add a mechanism to send a request to server that the player wants to play a game before loading the waiting room section
            int gid = model.startGame();
            WaitingRoomSection waitingRoomSection = new WaitingRoomSection(new WaitingRoomSectionModel(model.getPlayer(), gid), new WaitingRoomSectionView());
            waitingRoomSection.init();
        });
    } // end of setUpJoinGameBT

    private void setUpLogoutBT() {
        view.getLogoutBT().setOnAction(event -> {
            ClientJava.APPLICATION_STAGE.setScene(LoginPage.LOGIN_SCENE);
            model.logout();
        });
    } // end of setUpLogoutBT

    public void setUpRank() {
        view.setUsername(model.getPlayer().username);
        view.setPoints(model.getPlayer().points);
    }

    private void setUpLeaderboardsBT() {
        view.getRefreshLeaderboardsBT().setOnMouseClicked(event -> {
            setUpRank();
            view.refreshLeaderboardTable(model.getLeaderboardPlayers(), model.getPlayer().username);
        });
    } // end of setUpLeaderboardsBT

    public void displayInitialLeaderboards() {
        view.refreshLeaderboardTable(model.getLeaderboardPlayers(), model.getPlayer().username);
    } // end of displayInitialLeaderboards

    private void setUpExitApplication() {
        ClientJava.APPLICATION_STAGE.setOnCloseRequest(windowEvent -> {
            model.logout();
            System.exit(0);
        });
    } // end of setUpExitApplication
} // end of LobbyPage class
