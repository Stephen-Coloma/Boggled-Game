package Client_Java.model;

public class LoginPageModel {

    public Player login(String username, String password) throws AlreadyLoggedIn, AccountDoesNotExist {
        return ClientModel.authService.login(username, password);
    } // end of login
} // end of LoginPageModel class
