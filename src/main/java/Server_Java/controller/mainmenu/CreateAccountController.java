package Server_Java.controller.mainmenu;

import Server_Java.model.ServerJDBC;
import Server_Java.model.mainmenu.CreateAccountModel;
import Server_Java.view.mainmenu.CreateAccountConfirmationPopUpView;
import Server_Java.view.mainmenu.CreateAccountView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class CreateAccountController {
    private CreateAccountView view;
    private CreateAccountModel model;

    public CreateAccountController(CreateAccountModel model, CreateAccountView view) {
        this.model = model;
        this.view = view;

        //set up action
        view.setUpActionCreateAccountBtn(event -> {
            model.setFullName(view.getFullnameTextField().getText());
            model.setUsername(view.getUsernameTextField().getText());
            model.setPassword(view.getPasswordTextField().getText());

            if (model.getFullName().isEmpty() || model.getUsername().isEmpty() || model.getPassword().isEmpty()){
                view.getNoticeLabel().setStyle("-fx-text-fill: red;"); // Change the text color to red
                view.getNoticeLabel().setText("fill out all fields");
                view.getNoticeLabel().setVisible(true);

            }else if (model.getUsername().contains("-")){
                view.getNoticeLabel().setStyle("-fx-text-fill: red;"); // Change the text color to red
                view.getNoticeLabel().setText("no hyphen in usernames");
                view.getUsernameTextField().setText("");
                view.getUsernameTextField().setPromptText("choose other username");
                view.getNoticeLabel().setVisible(true);
            }else if (!ServerJDBC.isUsernameExist(model.getUsername())){ //check if the username is NOT taken
                showConfirmationPopUp();
            }else {
                view.getNoticeLabel().setStyle("-fx-text-fill: red;"); // Change the text color to red
                view.getNoticeLabel().setText("username already taken");
                view.getNoticeLabel().setVisible(true);
                view.getUsernameTextField().setText("");
                view.getUsernameTextField().setPromptText("choose other username");
            }
        });
    }

    private void showConfirmationPopUp() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Server_Java/res/fxmls/CreateAccountConfirmationPopup.fxml").toURI().toURL());
            Parent root = loader.load();

            CreateAccountConfirmationPopUpView popUpView = loader.getController();

            popUpView.setUpActionNoButton(event -> {
                ((Node) event.getSource()).getScene().getWindow().hide();
            });

            popUpView.setUpActionYesButton(event -> {
                //saving to database
                ServerJDBC.createPlayerAccount(model.getFullName(), model.getUsername(), model.getPassword());

                view.getNoticeLabel().setText("account created successfully");
                view.getNoticeLabel().setStyle("-fx-text-fill: green;"); // Change the text color to green
                view.getNoticeLabel().setVisible(true);

                // Schedule a task to hide the notice label after 5 seconds
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        view.getNoticeLabel().setVisible(false);
                    }
                }));
                timeline.play();

                //resetting the view
                view.getFullnameTextField().clear();
                view.getUsernameTextField().clear();
                view.getPasswordTextField().clear();
                ((Node) event.getSource()).getScene().getWindow().hide();
            });

            Scene scene = new Scene(root);

            Stage popUpStage = new Stage();
            popUpStage.setScene(scene);

            popUpStage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
