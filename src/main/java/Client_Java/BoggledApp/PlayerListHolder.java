package Client_Java.BoggledApp;


/**
* Client_Java.BoggledApp/PlayerListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Tuesday, April 30, 2024 12:28:41 PM SGT
*/


// sequence of players
public final class PlayerListHolder implements org.omg.CORBA.portable.Streamable
{
  public Client_Java.BoggledApp.Player value[] = null;

  public PlayerListHolder ()
  {
  }

  public PlayerListHolder (Client_Java.BoggledApp.Player[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Client_Java.BoggledApp.PlayerListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Client_Java.BoggledApp.PlayerListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Client_Java.BoggledApp.PlayerListHelper.type ();
  }

}
