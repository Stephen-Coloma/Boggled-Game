package Server_Java.model;

import Server_Java.model.implementations.BoggledApp.AccountDoesNotExist;
import Server_Java.model.implementations.BoggledApp.AlreadyLoggedIn;
import Server_Java.model.implementations.BoggledApp.Player;

import java.sql.*;
import java.util.List;

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

    /**
     * Logs in a player with the given username and password.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     * @return The Player object representing the logged-in player.
     * @throws AccountDoesNotExist If the account with the given username and password does not exist.
     * @throws AlreadyLoggedIn     If the account is already logged in.
     */
    public static Player login(String username, String password) throws AccountDoesNotExist, AlreadyLoggedIn {
        query = "SELECT * FROM players WHERE Username = ? AND Password = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int loggedInStatus = resultSet.getInt("loggedinstatus");
                if (loggedInStatus == 1) {
                    throw new AlreadyLoggedIn("Account already logged in");
                } else {
                    int id = resultSet.getInt("id");
                    String fn = resultSet.getString("fullname");
                    int points = resultSet.getInt("points");

                    loginHelper(id);

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
    /**
     * Method that logs out the player with the given player ID.
     *
     * @param pid The player ID of the player to be logged out.
     */
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
    /**
     * Helper method to update the logged-in status of a player after successful login.
     * @param pid The player ID of the player who is logging in.
     */
    private static void loginHelper(int pid){
        query = "UPDATE players SET loggedinstatus = 1 WHERE pid = ?";
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
    /**
     * Retrieves the ID of the last game played.
     * @return The ID of the last game played, or 0 if no games have been played yet.
     */
    public static int getLastGameId() {
        try {
            String query = "SELECT MAX(gid) AS max_gid FROM games";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("max_gid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**This method saves the game session to the database wherein each game has its gid, winner, and the total rounds played in the games table.
     * @param gid - game id
     * @param gameWinner - the player's pid who won the game
     * @param roundNumber - the total rounds played in the game*/
    public static void saveGame(int gid, int gameWinner, int roundNumber) {
        query = "INSERT INTO games(gid,gamewinner,totalrounds) " +
                "VALUES(?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gid);
            preparedStatement.setInt(2, gameWinner);
            preparedStatement.setInt(3, roundNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("successful insert");
            }else {
                System.out.println("unsuccessful insert");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayersPoints(List<Player> playersData) {
        //todo: assigned to @Jerwin Ramos, iterate each pid in the players data and add the points of the player to their stored points in the players table
    }

    /**This method adds all players who participated in the specific game in the gameplayers table.*/
    public static void addPlayerGameSessions(List<Player> playersData, int gid) {
        //todo: assigned to @Sanchie Earl Guzman, iterate each pid in the list and add it to the gid in the gameplayers table
    }

    /**todo: NOTEE!! Test your codes here*/
    public static void main(String[] args) {

    }
}
