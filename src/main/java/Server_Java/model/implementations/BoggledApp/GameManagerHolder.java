package Server_Java.model.implementations.BoggledApp;

/**
* BoggledApp/GameManagerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 3, 2024 11:23:36 PM SGT
*/


//interface for most of the game methods
public final class GameManagerHolder implements org.omg.CORBA.portable.Streamable
{
  public GameManager value = null;

  public GameManagerHolder ()
  {
  }

  public GameManagerHolder (GameManager initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = GameManagerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    GameManagerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return GameManagerHelper.type ();
  }

}
