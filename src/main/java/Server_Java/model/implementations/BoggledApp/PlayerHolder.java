package Server_Java.model.implementations.BoggledApp;

/**
* BoggledApp/PlayerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Sunday, April 21, 2024 8:36:25 PM CST
*/

public final class PlayerHolder implements org.omg.CORBA.portable.Streamable
{
  public Player value = null;

  public PlayerHolder ()
  {
  }

  public PlayerHolder (Player initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = PlayerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    PlayerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return PlayerHelper.type ();
  }

}
