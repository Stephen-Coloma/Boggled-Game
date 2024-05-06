package Client_Java.BoggledApp;


/**
* BoggledApp/AuthenticationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 3, 2024 11:23:17 PM SGT
*/


//interface for authentication
public interface AuthenticationOperations 
{
  Player login (String username, String password) throws AccountDoesNotExist, AlreadyLoggedIn;
  void logout (int pid);
} // interface AuthenticationOperations
