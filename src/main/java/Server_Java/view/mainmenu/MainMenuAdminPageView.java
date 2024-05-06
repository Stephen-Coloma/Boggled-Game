package Server_Java.view.mainmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainMenuAdminPageView {

    @FXML
    private Button createAccountBtn;

    @FXML
    private Button editGameBtn;

    @FXML
    private Pane pane;

    @FXML
    private Button viewPlayersBtn;

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
