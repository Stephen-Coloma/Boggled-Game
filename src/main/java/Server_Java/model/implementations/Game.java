package Server_Java.model.implementations;

import Server_Java.model.ServerJDBC;
import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class Game {
    private int gid;
    private List<Player> playersData;
    private String characterSet;
    private int roundNumber;
    private Player roundWinner;
    private Player gameWinner;
    private static int roundLength = ServerModel.roundLength;
    private CountDownLatch submissionLatch;
    private int totalSubmission;
    //EXTRA Fields
    static HashMap<Integer, List<String>> listOfUniqueStringsPerPlayer = new HashMap<>();
    static Set<String> duplicatedWords = new HashSet<>();

    public Game(int gid, Player player){
        this.gid = gid;
        playersData = new ArrayList<>();

        //resetting the data for a new game
        player.points = 0;
        player.roundsWon = 0;

        //adding the player who initiated the game into the list of players
        playersData.add(player);

        //instantiating the latch
        submissionLatch = new CountDownLatch(1);
        totalSubmission = 0;

        roundNumber = 0;
    }

    /**This method adds the player who  wants to join to the game in the waiting state.
     * @param player - player to join*/
    public void addPlayerToGame(Player player){
        //reset data
        player.points = 0;
        player.roundsWon = 0;

        playersData.add(player);
    }


    /**This method manages the call from the GameManagerImpl playFirstRoundMethod() wherein the game is in ongoing state.
     * Note that it only returns the Round object when all players called the playFirstRoundMethod() to assure that they
     * will get uniform data.
     * @return round*/
    public Round getFirstRound(){
        Round returnedRound = null;
        try {
            synchronized (this){
                totalSubmission++;
                //if all players submitted the call
                if (totalSubmission == playersData.size()){
                    //prepare the returned round
                    returnedRound = prepareReturnedFirstRound();

                    //release the latch
                    submissionLatch.countDown();
                }
            }
            //hold the thread if not all players submitted the call
            submissionLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }

        //reset the latch and total submission
        submissionLatch = new CountDownLatch(1);
        totalSubmission = 0;

        return returnedRound;
    }

    /**This method is a helper method that prepares the first round of the game
     * @return ROUND - a copy of the necessary data from the game.*/
    private Round prepareReturnedFirstRound() {
        Round round = new Round();
        round.gid = this.gid;
        round.playersData = this.playersData.toArray(new Player[playersData.size()]);
        this.characterSet = generateCharacterSet();
        round.characterSet = this.characterSet;
        round.roundLength = roundLength;
        this.roundNumber++;
        round.roundNumber = this.roundNumber;
        //optional for first round
        round.roundWinner = null;
        round.gameWinner = null;

        return round;
    }


    /**This method is called when the player calls the submitAndLoadNextRound() method from the GameManagerImpl class.
     * This method accepts all the answers from the players including their pid.
     * It processes their answers by identifying all the unique words a player had submitted using the collectAnswers() method.
     * If all the players submitted their answers, it computes the scores for the round for each player by calling the evaluateRound() method.
     * Lastly, it prepares the round data to be received by all the players.
     * prepareFinishedGameData() method is called to prepare the Round object that has a game winner.
     * prepareReturnedNextRound() method is called to prepare the Round object that does not have a game winner yet.
     * @param answersArray - array of strings a player submitted
     * @param pid - the id of the player
     * @return Round - returns the Round object for the necessary data to be handled by each client.*/
    public Round getNextRound(String[] answersArray, int pid){
        /*Algorithm
        * 1. Prepare the latch and submissions
        * 2. Collect all answers and
        * 2. Compute for scores
        * 3. Check who is the winner for the round
        * 4. Check if that winner has already 3 wins, then, end the game nulling the round winner and making the roundwinner to game winner
        * 5. If the round winner does not have 3 wins yet, prepare for next round
        * 6. */

        Round returnedRound = null;
        try {
            synchronized (this){
                totalSubmission++;
                //collect those words
                collectAnswers(answersArray, pid);

                //if all players submitted the call
                if (totalSubmission == playersData.size()){
                    //evaluating the answers, where round winner or game winner will be determined
                    evaluateRound();

                    //check if the round winner has 3 wins already
                    if (roundWinner.roundsWon == 3 && roundWinner != null){
                        gameWinner = roundWinner;
                        returnedRound = prepareFinishedGameData();

                    }else {
                        returnedRound = prepareReturnedNextRound();
                    }

                    //release the latch
                    submissionLatch.countDown();
                }
            }
            //hold the thread if not all players submitted the call
            submissionLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }

        //reset the latch and total submission, and the round winner, and the uniqueStringsPerPlayer and duplicateWords
        submissionLatch = new CountDownLatch(1);
        totalSubmission = 0;
        roundWinner = null;
        listOfUniqueStringsPerPlayer = new HashMap<>();
        duplicatedWords = new HashSet<>();

        return returnedRound;
    }

    /**This private method evaluates the answers of each player by computing their round points using the length of each unique strings they submitted.
     * This method also determines the round winner for the round - a player that has the highest points.
     * If two players have the highest scores, then the round winner would be null.
     * It then updates the playersData list.*/
    private void evaluateRound() {
        Player currentRoundWinner = null;
        int maxPoints = 0;
        boolean isRoundWinnerFound = false;

        // Compute the scores
        for (Map.Entry<Integer, List<String>> entry : listOfUniqueStringsPerPlayer.entrySet()) {
            int points = 0;
            int pid = entry.getKey();

            // Calculate points for the current player
            if (entry.getValue().size() > 0){
                for (String string : entry.getValue()) {
                    points += string.length();
                }
            }

            // Update player's points
            for (Player player : playersData) {
                if (player.pid == pid) {
                    player.points+=points;

                    // Check if the player is the round winner
                    if (points > maxPoints) {
                        maxPoints = points;
                        currentRoundWinner = player;
                        isRoundWinnerFound = true;
                    } else if (points == maxPoints) {
                        // If two or more players have the same score, round winner is null
                        currentRoundWinner = null;
                        isRoundWinnerFound = false;
                    }
                    break;
                }
            }
        }

        // Set the round winner only if a winner is found and there are players to compare
        if (isRoundWinnerFound) {
            roundWinner = currentRoundWinner;
            roundWinner.roundsWon++;
        } else {
            roundWinner = null;
        }
    }

    /**This method collects all the answers from the players and checks if their answers are unique(no other players had answered the same word/words).
     * It then adds the data to the listOfUniqueStringsPerPlayer Hashmap to save each list of unique strings per player.
     * @param answersArray - array of strings a player submitted
     * @param pid - the id of the player*/
    private void collectAnswers(String[] answersArray, int pid){
        List<String> answersList = new ArrayList<>(Arrays.asList(answersArray));
        List<String> copy = new ArrayList<>(answersList);

        for (List<String> playerStringList : listOfUniqueStringsPerPlayer.values()) {
            for (String ans: copy) {
                if (ServerModel.isFoundInWordBank(ans)){
                    if (playerStringList.contains(ans) || duplicatedWords.contains(ans) ){
                        //remove from the lists and add to set
                        playerStringList.remove(ans);
                        answersList.remove(ans);
                        if (!duplicatedWords.contains(ans)){
                            duplicatedWords.add(ans);
                        }
                    }
                }else {
                    answersList.remove(ans);
                }
            }
        }

        listOfUniqueStringsPerPlayer.put(pid, answersList);
    }

    /**This method prepares the Finished Game Data to be returned to each of the client. This is called when there is a game winner already.
     * @return Round - round object to be returned and fetched by all the clients.*/
    private Round prepareFinishedGameData(){
        //save first to the database the data
        ServerJDBC.saveGame(gid, gameWinner.pid, roundNumber);
        ServerJDBC.updatePlayersPoints(playersData);
        ServerJDBC.addPlayerGameSessions(playersData, gid);

        //preparing the  round data
        Round round = new Round();
        round.gid = this.gid;
        round.playersData = this.playersData.toArray(new Player[playersData.size()]);
        round.characterSet = null;
        round.roundLength = 0;
        round.roundNumber = this.roundNumber;
        round.roundWinner = null;
        round.gameWinner = this.gameWinner; //this will be the basis on how client will handle the returning round object
        return round;
    }

    /**This method is called to prepare the round object to be returned to each of the client.
     * This is called when the game continues for the next round (i.e) there is no game winner yet.
     * @return Round - round object to be fetched by all clients. This does not have a game winner yet.
     * And if the roundWinner is null, that means that the round is draw.*/
    private Round prepareReturnedNextRound(){
        //handle data where there is no round winner, show draw in the client side
        Round round = new Round();
        round.gid = this.gid;
        round.playersData = this.playersData.toArray(new Player[playersData.size()]);
        this.characterSet = generateCharacterSet();
        round.characterSet = this.characterSet;
        roundNumber++;
        round.roundNumber = this.roundNumber;
        round.roundLength = this.roundLength;

        //round can have round winner or draw, if draw, roundWinner = null
        if (roundWinner != null){
            round.roundWinner = this.roundWinner;
        }

        //returned next round automatically does not have any game winner
        round.gameWinner = null;
        return round;
    }

    /**This method will be used by GameManagerImpl wherein it checks if the game is valid.
     * If yes, it transfers the game from waiting state into ongoing games.
     * Note: For a game to be valid, it must have more than 1 player.
     * @return true - game is valid, false otherwise
     * */
    public boolean isGameValid(){
        return (playersData.size() > 1);
    }

    /**This method generates a random set of characters consists of 7 vowels and 13 consonants in string form */
    private String generateCharacterSet() {
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";
        StringBuilder charSet = new StringBuilder(20);

        for (int i = 0; i < 7; i++) {
            charSet.append(vowels.charAt((int) (Math.random() * vowels.length())));
        }
        for (int i = 0; i < 13; i++) {
            charSet.append(consonants.charAt((int) (Math.random() * consonants.length())));
        }
        // shuffle the characters to randomize
        List<Character> charList = charSet.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(charList);
        return charList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    /**=====================GETTERS AND SETTERS=====================*/

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public List<Player> getPlayersData() {
        return playersData;
    }

    public void setPlayersData(List<Player> playersData) {
        this.playersData = playersData;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Player getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(Player roundWinner) {
        this.roundWinner = roundWinner;
    }

    public Player getGameWinner() {
        return gameWinner;
    }

    public void setGameWinner(Player gameWinner) {
        this.gameWinner = gameWinner;
    }

    public CountDownLatch getSubmissionLatch() {
        return submissionLatch;
    }

    public void setSubmissionLatch(CountDownLatch submissionLatch) {
        this.submissionLatch = submissionLatch;
    }

    public int getTotalSubmission() {
        return totalSubmission;
    }

    public void setTotalSubmission(int totalSubmission) {
        this.totalSubmission = totalSubmission;
    }
}
