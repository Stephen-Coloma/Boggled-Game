package Server_Java.model.implementations.BoggledApp;

/**
* BoggledApp/InvalidWordHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 3:27:36 PM SGT
*/

public final class InvalidWordHolder implements org.omg.CORBA.portable.Streamable
{
  public InvalidWord value = null;

  public InvalidWordHolder ()
  {
  }

  public InvalidWordHolder (InvalidWord initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = InvalidWordHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    InvalidWordHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return InvalidWordHelper.type ();
  }

}
