package Client_Java.BoggledApp;


/**
* BoggledApp/AlreadyLoggedIn.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 3, 2024 11:23:17 PM SGT
*/

public final class AlreadyLoggedIn extends org.omg.CORBA.UserException
{
  public String reason = null;

  public AlreadyLoggedIn ()
  {
    super(AlreadyLoggedInHelper.id());
  } // ctor

  public AlreadyLoggedIn (String _reason)
  {
    super(AlreadyLoggedInHelper.id());
    reason = _reason;
  } // ctor


  public AlreadyLoggedIn (String $reason, String _reason)
  {
    super(AlreadyLoggedInHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class AlreadyLoggedIn
