package Server_Java.model.implementations.BoggledApp;


/**
* Client_Java.BoggledApp/PlayerListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Monday, April 22, 2024 7:22:27 PM CST
*/


// sequence of players
public final class PlayerListHolder implements org.omg.CORBA.portable.Streamable
{
  public Player value[] = null;

  public PlayerListHolder ()
  {
  }

  public PlayerListHolder (Player[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = PlayerListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    PlayerListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return PlayerListHelper.type ();
  }

}
