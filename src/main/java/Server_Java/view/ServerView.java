package Server_Java.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class ServerView {
    private FXMLLoader loader;
    private final Stage stage;


    public ServerView(Stage stage) {
        this.stage = stage;

        Platform.runLater(()->{
            try {
                System.out.println("Loading Admin Interface");
                loader = new FXMLLoader(new File("src/main/java/Server_Java/res/fxmls/MainMenuAdminPage.fxml").toURI().toURL());
                BorderPane root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("BOGGLED GAME [ADMIN]");
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }



    public FXMLLoader getLoader() {
        return loader;
    }

    public Stage getStage() {
        return stage;
    }
}
