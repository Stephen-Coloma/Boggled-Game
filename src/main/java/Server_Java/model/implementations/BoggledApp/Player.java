package Server_Java.model.implementations.BoggledApp;


/**
* BoggledApp/Player.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Thursday, May 2, 2024 1:34:29 PM SGT
*/

public final class Player implements org.omg.CORBA.portable.IDLEntity
{
  public int pid = (int)0;
  public String fullName = null;
  public String username = null;
  public int points = (int)0;

  public Player ()
  {
  } // ctor

  public Player (int _pid, String _fullName, String _username, int _points)
  {
    pid = _pid;
    fullName = _fullName;
    username = _username;
    points = _points;
  } // ctor

} // class Player
