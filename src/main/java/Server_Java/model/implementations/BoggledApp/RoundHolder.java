package Server_Java.model.implementations.BoggledApp;

/**
* BoggledApp/RoundHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 6:34:57 AM SGT
*/

public final class RoundHolder implements org.omg.CORBA.portable.Streamable
{
  public Round value = null;

  public RoundHolder ()
  {
  }

  public RoundHolder (Round initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = RoundHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    RoundHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return RoundHelper.type ();
  }

}
