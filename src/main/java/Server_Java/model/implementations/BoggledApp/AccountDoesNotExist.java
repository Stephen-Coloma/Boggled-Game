package Server_Java.model.implementations.BoggledApp;


/**
* BoggledApp/AccountDoesNotExist.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 6:34:57 AM SGT
*/

public final class AccountDoesNotExist extends org.omg.CORBA.UserException
{
  public String reason = null;

  public AccountDoesNotExist ()
  {
    super(AccountDoesNotExistHelper.id());
  } // ctor

  public AccountDoesNotExist (String _reason)
  {
    super(AccountDoesNotExistHelper.id());
    reason = _reason;
  } // ctor


  public AccountDoesNotExist (String $reason, String _reason)
  {
    super(AccountDoesNotExistHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class AccountDoesNotExist
