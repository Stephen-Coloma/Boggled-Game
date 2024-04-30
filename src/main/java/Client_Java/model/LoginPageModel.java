package Client_Java.model;

import Client_Java.BoggledApp.AccountDoesNotExist;
import Client_Java.BoggledApp.AlreadyLoggedIn;
import Client_Java.BoggledApp.Player;

public class LoginPageModel {

    public Player login(String username, String password) throws AlreadyLoggedIn, AccountDoesNotExist {
        return ClientModel.authService.login(username, password);
    } // end of login
} // end of LoginPageModel class
