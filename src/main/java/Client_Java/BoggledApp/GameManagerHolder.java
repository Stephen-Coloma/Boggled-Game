package Client_Java.BoggledApp;

/**
* Client_Java.BoggledApp/GameManagerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Thursday, April 25, 2024 5:31:14 PM CST
*/


//interface for most of the game methods
public final class GameManagerHolder implements org.omg.CORBA.portable.Streamable
{
  public Client_Java.BoggledApp.GameManager value = null;

  public GameManagerHolder ()
  {
  }

  public GameManagerHolder (Client_Java.BoggledApp.GameManager initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Client_Java.BoggledApp.GameManagerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Client_Java.BoggledApp.GameManagerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Client_Java.BoggledApp.GameManagerHelper.type ();
  }

}
