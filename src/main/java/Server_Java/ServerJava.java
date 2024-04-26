package Server_Java;

import Server_Java.controller.ServerController;
import Server_Java.model.ServerModel;
import Server_Java.view.ServerView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLOutput;

public class ServerJava extends Application {
    private ServerModel model;
    private ServerView view;
    private ServerController controller;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //a new thread that will handle clients
        Thread thread = new Thread(()->{
            model = new ServerModel();
        });
        thread.setDaemon(true);
        thread.start();

        //main thread that will handle server UI
        view = new ServerView(stage);
        controller = new ServerController(model, view);
    }
}