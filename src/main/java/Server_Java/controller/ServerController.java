package Server_Java.controller;

import Server_Java.controller.mainmenu.MainMenuAdminPageController;
import Server_Java.model.ServerModel;
import Server_Java.view.ServerView;
import Server_Java.view.mainmenu.MainMenuAdminPageView;
import javafx.application.Platform;

public class ServerController {
    private ServerModel model;
    private ServerView view;
    private MainMenuAdminPageView adminView;
    private MainMenuAdminPageController adminController;
    public ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this.view = view;

        Platform.runLater(()->{
            adminController = new MainMenuAdminPageController(view.getLoader().getController());
        });
    }
}
