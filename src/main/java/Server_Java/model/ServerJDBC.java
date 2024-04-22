package Server_Java.model;

import Server_Java.model.implementations.BoggledApp.AccountDoesNotExist;
import Server_Java.model.implementations.BoggledApp.AlreadyLoggedIn;
import Server_Java.model.implementations.BoggledApp.Player;

import java.sql.*;

public class ServerJDBC {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/boggleddb";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player login(String username, String password) throws AccountDoesNotExist, AlreadyLoggedIn {
        //todo: database queries for login
        return null;
    }

    public static void logout(int pid) {
        //todo: database queries for logout
    }

    public static int getLastGameId() {
        //todo: get the latest game id from the games table in boggleddb database, return 0 if there is non yet.
        return 0;
    }
}
