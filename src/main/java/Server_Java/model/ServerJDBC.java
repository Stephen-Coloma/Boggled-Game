package Server_Java.model;

import Server_Java.model.implementations.BoggledApp.AccountDoesNotExist;
import Server_Java.model.implementations.BoggledApp.AlreadyLoggedIn;
import Server_Java.model.implementations.BoggledApp.Player;

import java.sql.*;
import java.util.*;

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

    public static void saveGame(int gid, int gameWinner, int roundNumber) {
        //todo: assigned to @Cristian Barcellano Bsave game data to the games table
    }

    public static void updatePlayersPoints(List<Player> playersData) {
        query = "UPDATE players SET points = ? WHERE pid = ?";
        try{
            for (Player player : playersData){
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,player.points);
                preparedStatement.setInt(2,player.pid);

                int rowsAffected = preparedStatement.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method adds all players who participated in the specific game in the gameplayers table.
     *
     * @param playersData List of Player objects representing the players who participated in the game
     * @param gid The ID of the specific game session
     */
    public static void addPlayerGameSessions(List<Player> playersData, int gid) {

        query = "INSERT INTO gameplayers ( player, gamesession) "+
                "VALUES ( ?, ? ); ";

        try{
            preparedStatement = connection.prepareStatement(query);

            for (Player player : playersData){
                preparedStatement.setInt(1, player.pid);
                preparedStatement.setInt(2, gid);

                preparedStatement.executeUpdate();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Player[] fetchTopPlayers() {
        // TODO: 4/25/2024 @hannah ragudos, implement the sql statement that fetches the top players from the leaderboards
        // return would be the array of players
        return null;
    }

    /**todo: NOTEE!! Test your codes here*/
    public static void main(String[] args) {

    }
}
