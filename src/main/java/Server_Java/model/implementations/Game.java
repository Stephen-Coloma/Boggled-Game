package Server_Java.model.implementations;

import Server_Java.model.ServerModel;
import Server_Java.model.implementations.BoggledApp.Player;
import Server_Java.model.implementations.BoggledApp.Round;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Game {
    private int gid;
    private List<Player> playersData;
    private String characterSet;
    private int roundNumber;
    private Player roundWinner;
    private Player gameWinner;
    private static int ROUND_LENGTH = ServerModel.ROUND_LENGTH;
    private CountDownLatch submissionLatch;
    private int totalSubmission;

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
        round.roundLength = ROUND_LENGTH;
        this.roundNumber++;
        round.roundNumber = this.roundNumber;
        round.roundWinner = this.roundWinner;
        round.gameWinner = this.roundWinner;

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
        return null;
        //todo: create a generator for the character set stored in a string
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

    public static int getRoundLength() {
        return ROUND_LENGTH;
    }

    public static void setRoundLength(int roundLength) {
        ROUND_LENGTH = roundLength;
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
