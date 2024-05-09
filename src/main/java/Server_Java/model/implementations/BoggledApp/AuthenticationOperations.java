package Server_Java.model.implementations.BoggledApp;


/**
* BoggledApp/AuthenticationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 6:34:57 AM SGT
*/


//interface for authentication
public interface AuthenticationOperations 
{
  Player login (String username, String password) throws AccountDoesNotExist, AlreadyLoggedIn;
  void logout (int pid);
} // interface AuthenticationOperations
