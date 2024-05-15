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
     * Method that gets a player object from the database
     * @param pid the id of the player
     * @return the player object
     * @throws AccountDoesNotExist if player does not exist
     */
    public static Player getPlayer(int pid) {
        query = "SELECT * FROM players WHERE pid = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pid);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fullname = resultSet.getString("fullname");
                String username = resultSet.getString("username");
                int points = resultSet.getInt("points");
                return new Player(pid, fullname, username, points);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

                    return new Player(id, fn, username, points);
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
        query = "UPDATE games " +
                "SET gamewinner = ?, totalrounds = ? " +
                "WHERE gid = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gameWinner);
            preparedStatement.setInt(2, roundNumber);
            preparedStatement.setInt(3, gid);

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

    /**This method just adds the gid into the games table so that it will not be taken by other starting games*/
    public static void saveGameId(int gid) {
        query = "INSERT INTO games(gid) " +
                "VALUES(?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gid);
            int rowsAffected = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method removes the game id saved and resets the auto-increment to (latest - 1)
     */
    public static void removeLatestGid() {
        query = "DELETE FROM games " +
                "WHERE gid = (SELECT max_gid FROM (SELECT MAX(gid) AS max_gid FROM games) AS temp)";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    } // end of removeLatestGid

    /**
     * Updates the points of players in the database.
     * This method updates the points of players in a database table named "players"
     * based on the provided list of player data.
     * @param playersData A list of Player objects containing the updated points for each player.
     * @throws SQLException If an SQL exception occurs while executing the update queries.
     */
    public static void updatePlayersPoints(List<Player> playersData) {
        query = "UPDATE players SET points = ?, numberofgamesplayed = numberofgamesplayed + 1 WHERE pid = ?";
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
    public static String[] fetchTopPlayers() {
        List<String> topPlayers = new ArrayList<>();
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

                Player player = new Player(id, fullName, username, points);
                topPlayers.add(player.username + "-" + player.points);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topPlayers.toArray(new String[0]);
    }

    /**
     * Checks if a username already exists in the database.
     *
     * @param username The username to check for existence.
     * @return {@code true} if the username exists, {@code false} otherwise.
     * @throws RuntimeException If an SQL exception occurs during the database operation.
     */
    public static boolean isUsernameExist(String username) {
        query = "SELECT username FROM players" +
                " WHERE username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Creates a new player account with the provided information.
     *
     * @param fullName The full name of the player.
     * @param username The username for the player account.
     * @param password The password for the player account.
     * @throws RuntimeException If an SQL exception occurs during the database operation.
     */
    public static void createPlayerAccount(String fullName, String username, String password) {
      query = "INSERT INTO players (fullname, username, password, points, loggedinstatus) "+
              "VALUES ( ?, ?, ?, ?, ?); ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    /**
     * This method fetches all the players data to be dispplayed on the server side
     *
     * @return the data to be displayed in the table
     */
    public static List<PlayerData> getPlayersList() {
        List<PlayerData> playersDataList  = new ArrayList<>();

        query = "SELECT pid, username, password, fullname, numberofgamesplayed, points " +
                "FROM players";


        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            //fetching all rows
            while (resultSet.next()){
                int pid = resultSet.getInt("pid");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullname = resultSet.getString("fullname");
                int numberOfGamesPlayed = resultSet.getInt("numberofgamesplayed");
                int points = resultSet.getInt("points");

                PlayerData playerData = new PlayerData(pid, username, password, fullname, numberOfGamesPlayed, points);
                playersDataList.add(playerData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return playersDataList;
    }

    /**This method deletes the player into the database.*/
    public static void deletePlayer(int pid) {
        query = "DELETE FROM players " +
                "WHERE pid = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pid);

            int rowsAffected = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**This method edits the player details in the database*/
    public static void savePlayerDetails(PlayerData playerData) {
        query = "UPDATE players " +
                "SET fullname = ?, username = ?, password = ?, points = ? " +
                "WHERE pid = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerData.getFullname());
            preparedStatement.setString(2, playerData.getUsername());
            preparedStatement.setString(3, playerData.getPassword());
            preparedStatement.setInt(4, playerData.getPoints());
            preparedStatement.setInt(5, playerData.getPid());

            int rowsAffected = preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static class PlayerData{
        private int pid;
        private String username;
        private String password;
        private String fullname;
        private int numberOfGamesPlayed;
        private int points;

        public PlayerData(int pid, String username, String password, String fullname, int numberOfGamesPlayed, int points) {
            this.pid = pid;
            this.username = username;
            this.password = password;
            this.fullname = fullname;
            this.numberOfGamesPlayed = numberOfGamesPlayed;
            this.points = points;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public int getNumberOfGamesPlayed() {
            return numberOfGamesPlayed;
        }

        public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
            this.numberOfGamesPlayed = numberOfGamesPlayed;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }

}
