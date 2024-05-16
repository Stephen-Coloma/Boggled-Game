package Server_Java.controller.mainmenu;

import Server_Java.model.EditPlayerPopUpModel;
import Server_Java.model.ServerJDBC;
import Server_Java.view.mainmenu.EditPlayerPopUpView;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class EditPlayerPopUpController {
    private EditPlayerPopUpModel model;
    private EditPlayerPopUpView view;

    public EditPlayerPopUpController(EditPlayerPopUpModel model, EditPlayerPopUpView view) {
        this.model = model;
        this.view = view;

        //todo: set up action for editing a player data
        this.view.getUsernameTF().setText(model.getPlayerData().getUsername());
        this.view.getPasswordTF().setText(model.getPlayerData().getPassword());
        this.view.getFullnameTF().setText(model.getPlayerData().getFullname());
        this.view.getGamepointsTF().setText(String.valueOf(model.getPlayerData().getPoints()));
        this.view.getGamesPlayedLbl().setText(String.valueOf(model.getPlayerData().getNumberOfGamesPlayed()));
    }
}
