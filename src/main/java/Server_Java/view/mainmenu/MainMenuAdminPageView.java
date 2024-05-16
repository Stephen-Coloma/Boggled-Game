package Server_Java.view.mainmenu;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;

public class MainMenuAdminPageView {
    @FXML
    private Rectangle createAccRectangle, viewPlayersRectangle, editGameConfigRectangle;

    @FXML
    private Label createAccLB, viewPlayersLB, editGameConfigLB;

    @FXML
    private Button createAccountBtn;

    @FXML
    private Button editGameBtn;

    @FXML
    private Pane pane;

    @FXML
    private Button viewPlayersBtn;

    private HashMap<String, Boolean> menuButtons;

    @FXML
    public void initialize() {
        setButtonHashMap();
        setMenuButtonBehavior();
    }

    private void setButtonHashMap() {
        menuButtons = new HashMap<>();

        menuButtons.put("Create Account", false);
        menuButtons.put("View Players", false);
        menuButtons.put("Edit Game Config", false);
    } // end of setButtonHashMap

    private void setMenuButtonBehavior() {
        createAccountBtn.setOnMouseClicked(event -> {
            applyMenuAnimation(createAccLB, createAccRectangle, false);
            menuButtons.put("Create Account", true);
            revertPreviousButtonStyle("Create Account");
        });
        viewPlayersBtn.setOnMouseClicked(event -> {
            applyMenuAnimation(viewPlayersLB, viewPlayersRectangle, false);
            menuButtons.put("View Players", true);
            revertPreviousButtonStyle("View Players");
        });
        editGameBtn.setOnMouseClicked(event -> {
            applyMenuAnimation(editGameConfigLB, editGameConfigRectangle, false);
            menuButtons.put("Edit Game Config", true);
            revertPreviousButtonStyle("Edit Game Config");
        });
    }

    private void revertPreviousButtonStyle(String currentButton) {
        if (menuButtons.get("Create Account") && !"Create Account".equalsIgnoreCase(currentButton)) {
            applyMenuAnimation(createAccLB, createAccRectangle, true);
            menuButtons.put("Create Account", false);
            return;
        }
        if (menuButtons.get("View Players") && !"View Players".equalsIgnoreCase(currentButton)) {
            applyMenuAnimation(viewPlayersLB, viewPlayersRectangle, true);
            menuButtons.put("View Players", false);
            return;
        }
        if (menuButtons.get("Edit Game Config") && !"Edit Game Config".equalsIgnoreCase(currentButton)) {
            applyMenuAnimation(editGameConfigLB, editGameConfigRectangle, true);
            menuButtons.put("Edit Game Config", false);
            return;
        }
    } // end of revertPreviousButtonStyle

    private void applyMenuAnimation(Label label, Rectangle rectangle, boolean revert) {
        Timeline timeline = new Timeline();

        if (!revert) {
            label.setStyle("-fx-text-fill: white");
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(rectangle.widthProperty(), 55)),
                    new KeyFrame(Duration.millis(200), new KeyValue(rectangle.widthProperty(), 212))
            );
        } else {
            label.setStyle("-fx-text-fill: black");
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(rectangle.widthProperty(), 212)),
                    new KeyFrame(Duration.millis(50), new KeyValue(rectangle.widthProperty(), 55))
            );
        }
        timeline.play();
    }

    //set up action for buttons
    public void setUpActionCreateAccountButton(EventHandler<ActionEvent> event){
        this.createAccountBtn.setOnAction(event);
    }public void setUpActionEditGameButton(EventHandler<ActionEvent> event){
        this.editGameBtn.setOnAction(event);
    }public void setUpActionViewPlayersButton(EventHandler<ActionEvent> event){
        this.viewPlayersBtn.setOnAction(event);
    }

    public Button getCreateAccountBtn() {
        return createAccountBtn;
    }

    public void setCreateAccountBtn(Button createAccountBtn) {
        this.createAccountBtn = createAccountBtn;
    }

    public Button getEditGameBtn() {
        return editGameBtn;
    }

    public void setEditGameBtn(Button editGameBtn) {
        this.editGameBtn = editGameBtn;
    }

    public Button getViewPlayersBtn() {
        return viewPlayersBtn;
    }

    public void setViewPlayersBtn(Button viewPlayersBtn) {
        this.viewPlayersBtn = viewPlayersBtn;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
}
