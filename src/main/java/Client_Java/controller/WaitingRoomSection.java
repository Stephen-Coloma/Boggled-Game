package Client_Java.controller;

import Client_Java.ClientJava;
import Client_Java.model.WaitingRoomSectionModel;
import Client_Java.view.WaitingRoomSectionView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class WaitingRoomSection {
    private WaitingRoomSectionModel model;
    private WaitingRoomSectionView view;

    public WaitingRoomSection(WaitingRoomSectionModel model, WaitingRoomSectionView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Client_Java/res/fxmls/WaitingRoomPage.fxml").toURI().toURL());

            Scene waitingRoomScene = new Scene(loader.load());

            view = loader.getController();

            ClientJava.APPLICATION_STAGE.setScene(waitingRoomScene);

            setUpCancelBT();
            initiateCountdown();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    } // end of init

    private void setUpCancelBT() {
        view.getCancelBT().setOnAction(event -> {
            // TODO: either add a part here to stop the countdown or set it to the model
            model.getCountdownTimer().cancel();
            ClientJava.APPLICATION_STAGE.setScene(LobbyPage.LOBBY_SCENE);
        });
    } // end of setUpCancelBT

    private void initiateCountdown() {
        view.getCountdownLB().textProperty().bind(model.getDuration().asString());
        view.getPlayerCountLB().textProperty().bind(model.getPlayerCount().asString());
        model.startCountdown();
        model.listenForPlayerCounts();
    } // end of initiateCountdown
} // end of WaitingRoomSection class
