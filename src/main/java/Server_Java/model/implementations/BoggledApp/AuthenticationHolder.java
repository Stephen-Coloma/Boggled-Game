package Server_Java.model.implementations.BoggledApp;

/**
* BoggledApp/AuthenticationHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 3, 2024 11:23:36 PM SGT
*/


//interface for authentication
public final class AuthenticationHolder implements org.omg.CORBA.portable.Streamable
{
  public Authentication value = null;

  public AuthenticationHolder ()
  {
  }

  public AuthenticationHolder (Authentication initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = AuthenticationHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    AuthenticationHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return AuthenticationHelper.type ();
  }

}
