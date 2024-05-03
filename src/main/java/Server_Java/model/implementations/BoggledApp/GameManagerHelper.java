package Server_Java.model.implementations.BoggledApp;


/**
* BoggledApp/GameManagerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 3, 2024 4:29:38 PM SGT
*/


//interface for most of the game methods
abstract public class GameManagerHelper
{
  private static String  _id = "IDL:BoggledApp/GameManager:1.0";

  public static void insert (org.omg.CORBA.Any a, GameManager that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static GameManager extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (GameManagerHelper.id (), "GameManager");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static GameManager read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_GameManagerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, GameManager value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static GameManager narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof GameManager)
      return (GameManager)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _GameManagerStub stub = new _GameManagerStub();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static GameManager unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof GameManager)
      return (GameManager)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _GameManagerStub stub = new _GameManagerStub();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
