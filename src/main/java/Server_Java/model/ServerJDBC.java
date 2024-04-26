package Server_Java.model;

import Server_Java.model.implementations.BoggledApp.AccountDoesNotExist;
import Server_Java.model.implementations.BoggledApp.AlreadyLoggedIn;
import Server_Java.model.implementations.BoggledApp.Player;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
                    int id = resultSet.getInt("pid");
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

    /**
     * Updates the points of players in the database.
     * This method updates the points of players in a database table named "players"
     * based on the provided list of player data.
     * @param playersData A list of Player objects containing the updated points for each player.
     * @throws SQLException If an SQL exception occurs while executing the update queries.
     */
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

    /**
     * This method retrieves player details for the top players, sorted in descending order by their points
     *
     * @return Array of Player objects representing the top players
     * @return Empty array if no players are found or in case of an error
     */
    public static Player[] fetchTopPlayers() {
        List<Player> topPlayers = new ArrayList<>();
        query = "SELECT * FROM players ORDER BY points DESC";
        //  DESC LIMIT 5;

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("pid");
                String fullName = resultSet.getString("fullname");
                String username = resultSet.getString("username");
                int points = resultSet.getInt("points");

                Player player = new Player(id, fullName, username, "", points, 0);
                topPlayers.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topPlayers.toArray(new Player[0]);
    }

    public static boolean isUsernameExist(String username) {
        //todo: fetch the sql database if the username exists, return true if yes, false if not
        return false;
    }

    public static void createPlayerAccount(String fullName, String username, String password) {
        //todo: make a player instance in the players database using the following fullname, username, password, points = 0, loggedinstatus = 0;
    }

    public static void main(String[] args) {

    }
}
