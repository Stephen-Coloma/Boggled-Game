package Server_Java.model.implementations;

import Server_Java.model.ServerJDBC;
import Server_Java.model.implementations.BoggledApp.AccountDoesNotExist;
import Server_Java.model.implementations.BoggledApp.AlreadyLoggedIn;
import Server_Java.model.implementations.BoggledApp.AuthenticationPOA;
import Server_Java.model.implementations.BoggledApp.Player;

public class AuthenticationImpl extends AuthenticationPOA {
    @Override
    public Player login(String username, String password) throws AccountDoesNotExist, AlreadyLoggedIn {
        return ServerJDBC.login(username, password);
    }

    @Override
    public void logout(int pid) {
        ServerJDBC.logout(pid);
    }
}
