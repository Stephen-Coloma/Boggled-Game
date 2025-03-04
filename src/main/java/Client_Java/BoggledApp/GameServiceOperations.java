package Client_Java.BoggledApp;


/**
* BoggledApp/GameServiceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 3:26:55 PM SGT
*/


//interface for most of the game methods
public interface GameServiceOperations 
{
  int startGame (int pid);
  int getRemainingWaitingTime () throws GameTimeOut;
  int getNumberOfPlayersJoined ();
  Round playRound (int gid) throws GameNotFound;
  int getRemainingRoundTime (int gid) throws GameTimeOut;
  void submitWord (String word, int pid, int gid) throws InvalidWord;
  String getRoundWinner (int gid);
  String getGameWinner (int gid);
  boolean roundEvaluationDone (int gid);
  void leaveGame (int pid, int gid);
  String[] getLeaderboards ();
} // interface GameServiceOperations
