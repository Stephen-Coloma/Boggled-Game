package Server_Java.controller.mainmenu;

import Server_Java.model.ServerJDBC;
import Server_Java.model.ServerModel;
import Server_Java.model.mainmenu.EditGameConfigModel;
import Server_Java.view.mainmenu.EditGameConfigPopUpView;
import Server_Java.view.mainmenu.EditGameConfigView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class EditGameConfigController {
    private EditGameConfigModel model;
    private EditGameConfigView view;

    public EditGameConfigController(EditGameConfigModel model, EditGameConfigView view) {
        this.model = model;
        this.view = view;

        //set up actions for increments and decrements button
        this.view.getwLabel().setText(String.valueOf(model.getWaitingTime()));
        this.view.getrLabel().setText(String.valueOf(model.getRoundLength()));

        //setting up actions for increment decrement buttons
        setUpIncrementDecrementButtons();

        //setting up action for save button
        this.view.setUpActionSaveBtn(event -> {
            this.view.getWaitingTimeNoticeLabel().setVisible(false);
            this.view.getRoundLengthNoticeLabel().setVisible(false);

            if (model.getWaitingTime() != Integer.parseInt(this.view.getwLabel().getText()) || model.getRoundLength() != Integer.parseInt(this.view.getrLabel().getText())){
                showConfirmationPopUp();
            }else {
                this.view.getNoticeLabel().setText("No changes detected");
                this.view.getNoticeLabel().setVisible(true);
                view.getNoticeLabel().setStyle("-fx-text-fill: red;"); // Change the text color to red
            }
        });
    }

    private void setUpIncrementDecrementButtons() {
        //setting up the actions for waiting time decrement button
        this.view.setUpActionWDecrementBtn(event -> {
            int tempWaitingTime = Integer.parseInt(this.view.getwLabel().getText());
            if (tempWaitingTime > 1){
                tempWaitingTime--;
                view.getwLabel().setText(String.valueOf(tempWaitingTime));
            }else {
                view.getWaitingTimeNoticeLabel().setText("No less than 1 second");
                view.getWaitingTimeNoticeLabel().setVisible(true);
            }
            this.view.getNoticeLabel().setVisible(false);
        });

        //setting up the actions for waiting time increment button
        this.view.setUpActionWIncrementBtn(event -> {
            int tempWaitingTime = Integer.parseInt(this.view.getwLabel().getText());
            tempWaitingTime++;
            view.getwLabel().setText(String.valueOf(tempWaitingTime));
            view.getWaitingTimeNoticeLabel().setVisible(false);
            this.view.getNoticeLabel().setVisible(false);
        });

        //setting up the actions for round length decrement button
        this.view.setUpActionRDecrementBtn(event -> {
            int tempRoundLength = Integer.parseInt(this.view.getrLabel().getText());
            if (tempRoundLength > 1){
                tempRoundLength--;
                view.getrLabel().setText(String.valueOf(tempRoundLength));
            }else {
                view.getRoundLengthNoticeLabel().setText("No less than 1 second");
                view.getRoundLengthNoticeLabel().setVisible(true);
            }
            this.view.getNoticeLabel().setVisible(false);
        });

        //setting up the actions for round length increment button
        this.view.setUpActionRIncrementBtn(event -> {
            int tempRoundLength = Integer.parseInt(this.view.getrLabel().getText());
            tempRoundLength++;
            view.getrLabel().setText(String.valueOf(tempRoundLength));
            view.getRoundLengthNoticeLabel().setVisible(false);
            this.view.getNoticeLabel().setVisible(false);
        });
    }

    private void showConfirmationPopUp() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/java/Server_Java/res/fxmls/EditConfigConfirmationPopup.fxml").toURI().toURL());
            Parent root = loader.load();

            EditGameConfigPopUpView popUpView = loader.getController();

            popUpView.setUpActionNoButton(event -> {
                ((Node) event.getSource()).getScene().getWindow().hide();
            });

            popUpView.setUpActionYesButton(event -> {
                model.setWaitingTime(Integer.parseInt(this.view.getwLabel().getText()));
                model.setRoundLength(Integer.parseInt(this.view.getrLabel().getText()));

                model.saveGameConfig(); //saving the game configuration

                view.getNoticeLabel().setText("game configuration saved");
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
