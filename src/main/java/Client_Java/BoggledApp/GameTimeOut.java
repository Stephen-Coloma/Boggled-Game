package Client_Java.BoggledApp;


/**
* BoggledApp/GameTimeOut.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 6:34:33 AM SGT
*/

public final class GameTimeOut extends org.omg.CORBA.UserException
{
  public String reason = null;

  public GameTimeOut ()
  {
    super(GameTimeOutHelper.id());
  } // ctor

  public GameTimeOut (String _reason)
  {
    super(GameTimeOutHelper.id());
    reason = _reason;
  } // ctor


  public GameTimeOut (String $reason, String _reason)
  {
    super(GameTimeOutHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class GameTimeOut
