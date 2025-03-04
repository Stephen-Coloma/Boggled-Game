package Server_Java.model.implementations.BoggledApp;


/**
* BoggledApp/Round.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 3:27:36 PM SGT
*/

public final class Round implements org.omg.CORBA.portable.IDLEntity
{
  public int gid = (int)0;
  public String playersData[] = null;

  // string array of players with their respective rounds won e.g. "Leonhard-1"
  public String characterSet = null;
  public int roundNumber = (int)0;

  public Round ()
  {
  } // ctor

  public Round (int _gid, String[] _playersData, String _characterSet, int _roundNumber)
  {
    gid = _gid;
    playersData = _playersData;
    characterSet = _characterSet;
    roundNumber = _roundNumber;
  } // ctor

} // class Round
