package Server_Java.controller.mainmenu;

import Server_Java.model.ServerModel;
import Server_Java.model.mainmenu.CreateAccountModel;
import Server_Java.model.mainmenu.EditGameConfigModel;
import Server_Java.view.mainmenu.CreateAccountView;
import Server_Java.view.mainmenu.EditGameConfigView;
import Server_Java.view.mainmenu.MainMenuAdminPageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;

public class MainMenuAdminPageController {
    private MainMenuAdminPageView view;

    public MainMenuAdminPageController(MainMenuAdminPageView view) {
        this.view = view;

        //setting up the action
        this.view.setUpActionCreateAccountButton(event -> {
            //load the adding of account and place it to the pane
            try {
                FXMLLoader loader = new FXMLLoader(new File("src/main/java/Server_Java/res/fxmls/CreateAccountView.fxml").toURI().toURL());
                StackPane root = loader.load();
                view.getPane().getChildren().clear();
                view.getPane().getChildren().add(root);

                CreateAccountModel createAccountModel = new CreateAccountModel();
                CreateAccountView createAccountView = loader.getController();
                CreateAccountController createAccountController = new CreateAccountController(createAccountModel, createAccountView);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        //todo: load the view players list
        //todo: load the edit game configutations
        this.view.setUpActionEditGameButton(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(new File("src/main/java/Server_Java/res/fxmls/EditGameConfigView.fxml").toURI().toURL());
                Pane root = loader.load();
                view.getPane().getChildren().clear();
                view.getPane().getChildren().add(root);

                EditGameConfigModel editGameModel = new EditGameConfigModel(ServerModel.waitingTime,ServerModel.roundLength);
                EditGameConfigView editGameView = loader.getController();
                EditGameConfigController editGameController = new EditGameConfigController(editGameModel, editGameView);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}
