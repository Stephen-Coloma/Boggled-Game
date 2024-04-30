package Client_Java;

import Client_Java.controller.LoginPage;
import Client_Java.model.ClientModel;
import Client_Java.model.LoginPageModel;
import Client_Java.view.LoginPageView;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {
    public static Stage APPLICATION_STAGE;

    public static void main(String[] args) {
        // initialize configurations
        ClientModel clientModel = new ClientModel();
        clientModel.init();

        launch(args);
    } // end of main

    @Override
    public void start(Stage stage) {
        ClientApp.APPLICATION_STAGE = stage;

        LoginPage loginPage = new LoginPage(new LoginPageModel(), new LoginPageView());
        loginPage.init();

        stage.setTitle("Boggled");
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    } // end of start
} // end of ClientApp class
