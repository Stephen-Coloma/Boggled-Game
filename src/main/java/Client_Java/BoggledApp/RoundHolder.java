package Client_Java.BoggledApp;

/**
* Client_Java.BoggledApp/RoundHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Tuesday, April 30, 2024 12:28:41 PM SGT
*/

public final class RoundHolder implements org.omg.CORBA.portable.Streamable
{
  public Client_Java.BoggledApp.Round value = null;

  public RoundHolder ()
  {
  }

  public RoundHolder (Client_Java.BoggledApp.Round initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Client_Java.BoggledApp.RoundHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Client_Java.BoggledApp.RoundHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Client_Java.BoggledApp.RoundHelper.type ();
  }

}
