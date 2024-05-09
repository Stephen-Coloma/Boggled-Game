package Server_Java.model.implementations.BoggledApp;


/**
* BoggledApp/AuthenticationPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 6:34:57 AM SGT
*/


//interface for authentication
public abstract class AuthenticationPOA extends org.omg.PortableServer.Servant
 implements AuthenticationOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("login", new java.lang.Integer (0));
    _methods.put ("logout", new java.lang.Integer (1));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // BoggledApp/Authentication/login
       {
         try {
           String username = in.read_string ();
           String password = in.read_string ();
           Player $result = null;
           $result = this.login (username, password);
           out = $rh.createReply();
           PlayerHelper.write (out, $result);
         } catch (AccountDoesNotExist $ex) {
           out = $rh.createExceptionReply ();
           AccountDoesNotExistHelper.write (out, $ex);
         } catch (AlreadyLoggedIn $ex) {
           out = $rh.createExceptionReply ();
           AlreadyLoggedInHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // BoggledApp/Authentication/logout
       {
         int pid = in.read_long ();
         this.logout (pid);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:BoggledApp/Authentication:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Authentication _this() 
  {
    return AuthenticationHelper.narrow(
    super._this_object());
  }

  public Authentication _this(org.omg.CORBA.ORB orb) 
  {
    return AuthenticationHelper.narrow(
    super._this_object(orb));
  }


} // class AuthenticationPOA
