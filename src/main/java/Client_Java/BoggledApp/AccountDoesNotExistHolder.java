package Client_Java.BoggledApp;

/**
* Client_Java.BoggledApp/AccountDoesNotExistHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Thursday, April 25, 2024 5:31:14 PM CST
*/

public final class AccountDoesNotExistHolder implements org.omg.CORBA.portable.Streamable
{
  public Client_Java.BoggledApp.AccountDoesNotExist value = null;

  public AccountDoesNotExistHolder ()
  {
  }

  public AccountDoesNotExistHolder (Client_Java.BoggledApp.AccountDoesNotExist initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Client_Java.BoggledApp.AccountDoesNotExistHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Client_Java.BoggledApp.AccountDoesNotExistHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Client_Java.BoggledApp.AccountDoesNotExistHelper.type ();
  }

}
