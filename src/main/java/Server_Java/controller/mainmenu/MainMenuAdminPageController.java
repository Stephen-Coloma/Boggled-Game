package Server_Java.controller.mainmenu;

import Server_Java.model.mainmenu.CreateAccountModel;
import Server_Java.view.mainmenu.CreateAccountView;
import Server_Java.view.mainmenu.MainMenuAdminPageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.File;

public class MainMenuAdminPageController {
    private MainMenuAdminPageView view;

    public MainMenuAdminPageController(MainMenuAdminPageView view) {
        this.view = view;

        //setting up the action
        view.setUpActionCreateAccountButton(event -> {
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
    }
}
