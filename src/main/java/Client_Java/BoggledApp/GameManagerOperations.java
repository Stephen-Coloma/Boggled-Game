package Client_Java.BoggledApp;


/**
* Client_Java.BoggledApp/GameManagerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Tuesday, April 30, 2024 12:28:41 PM SGT
*/


//interface for most of the game methods
public interface GameManagerOperations 
{
  int startGame (Client_Java.BoggledApp.Player player);
  int getWaitingTime (int gid);
  int getTotalPlayersJoined (int gid);
  Client_Java.BoggledApp.Round playFirstRound (int gid);

  //returns null if game status is false (no joined players). Otherwise, returns a round (for first round)
  Client_Java.BoggledApp.Round submitAndLoadNextRound (String[] answersArray, int pid, int gid) throws Client_Java.BoggledApp.NoPlayersLeft;
  Client_Java.BoggledApp.Player[] viewLeaderboards ();
} // interface GameManagerOperations
