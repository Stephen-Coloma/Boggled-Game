package Server_Java.model.implementations.BoggledApp;

/**
* BoggledApp/GameTimeOutHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 3, 2024 4:29:38 PM SGT
*/

public final class GameTimeOutHolder implements org.omg.CORBA.portable.Streamable
{
  public GameTimeOut value = null;

  public GameTimeOutHolder ()
  {
  }

  public GameTimeOutHolder (GameTimeOut initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = GameTimeOutHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    GameTimeOutHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return GameTimeOutHelper.type ();
  }

}
