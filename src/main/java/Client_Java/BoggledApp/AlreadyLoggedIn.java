package Client_Java.BoggledApp;


/**
* Client_Java.BoggledApp/AlreadyLoggedIn.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Tuesday, April 30, 2024 12:28:41 PM SGT
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
