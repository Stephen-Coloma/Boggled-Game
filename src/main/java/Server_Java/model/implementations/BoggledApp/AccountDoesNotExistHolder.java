package Server_Java.model.implementations.BoggledApp;

/**
* Client_Java.BoggledApp/AccountDoesNotExistHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Monday, April 22, 2024 7:22:27 PM CST
*/

public final class AccountDoesNotExistHolder implements org.omg.CORBA.portable.Streamable
{
  public AccountDoesNotExist value = null;

  public AccountDoesNotExistHolder ()
  {
  }

  public AccountDoesNotExistHolder (AccountDoesNotExist initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = AccountDoesNotExistHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    AccountDoesNotExistHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return AccountDoesNotExistHelper.type ();
  }

}
