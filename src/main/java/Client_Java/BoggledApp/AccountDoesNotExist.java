package Client_Java.BoggledApp;


/**
* Client_Java.BoggledApp/AccountDoesNotExist.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Thursday, April 25, 2024 5:31:14 PM CST
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
