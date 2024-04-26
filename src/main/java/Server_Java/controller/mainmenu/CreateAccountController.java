package Server_Java.controller.mainmenu;

import Server_Java.model.ServerJDBC;
import Server_Java.model.mainmenu.CreateAccountModel;
import Server_Java.view.mainmenu.CreateAccountView;

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

            }else if (!ServerJDBC.isUsernameExist(model.getUsername())){ //check if the username is NOT taken
                ServerJDBC.createPlayerAccount(model.getFullName(), model.getUsername(), model.getPassword());
                view.getNoticeLabel().setText("account created successfully");
                view.getNoticeLabel().setStyle("-fx-text-fill: green;"); // Change the text color to green
                view.getNoticeLabel().setVisible(true);

            }else {
                view.getNoticeLabel().setStyle("-fx-text-fill: red;"); // Change the text color to red
                view.getNoticeLabel().setText("username already taken");
                view.getNoticeLabel().setVisible(true);
                view.getUsernameTextField().setText("");
                view.getUsernameTextField().setPromptText("choose other username");
            }
        });
    }
}
