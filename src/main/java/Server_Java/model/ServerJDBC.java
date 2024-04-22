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
    private static String query;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player login(String username, String password) throws AccountDoesNotExist, AlreadyLoggedIn {
        query = "SELECT * FROM players WHERE Username = ? AND Password = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fn = resultSet.getString("fullname");
                int points = resultSet.getInt("points");
                int loggedInStatus = resultSet.getInt("loggedinstatus");
                if (loggedInStatus == 1) {
                    throw new AlreadyLoggedIn("Account already logged in");
                } else {
                    return new Player(id, fn, username, password, points, 0);
                }
            } else {
                throw new AccountDoesNotExist("Account Does Not Exist");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void logout(int pid) {
        query = "UPDATE players SET loggedinstatus = 0 WHERE pid = ?";
        try{
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, pid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception exception){
            exception.printStackTrace();
        }

    }


    public static int getLastGameId() {
        //todo: get the latest game id from the games table in boggleddb database, return 0 if there is non yet.
        return 0;
    }
}
