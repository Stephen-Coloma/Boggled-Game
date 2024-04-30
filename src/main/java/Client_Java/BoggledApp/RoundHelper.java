package Client_Java.BoggledApp;


/**
* Client_Java.BoggledApp/RoundHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Tuesday, April 30, 2024 12:28:41 PM SGT
*/

abstract public class RoundHelper
{
  private static String  _id = "IDL:Client_Java.BoggledApp/Round:1.0";

  public static void insert (org.omg.CORBA.Any a, Client_Java.BoggledApp.Round that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Client_Java.BoggledApp.Round extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [7];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "gid",
            _tcOf_members0,
            null);
          _tcOf_members0 = Client_Java.BoggledApp.PlayerHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (Client_Java.BoggledApp.PlayerListHelper.id (), "PlayerList", _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "playersData",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "characterSet",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[3] = new org.omg.CORBA.StructMember (
            "roundLength",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[4] = new org.omg.CORBA.StructMember (
            "roundNumber",
            _tcOf_members0,
            null);
          _tcOf_members0 = Client_Java.BoggledApp.PlayerHelper.type ();
          _members0[5] = new org.omg.CORBA.StructMember (
            "roundWinner",
            _tcOf_members0,
            null);
          _tcOf_members0 = Client_Java.BoggledApp.PlayerHelper.type ();
          _members0[6] = new org.omg.CORBA.StructMember (
            "gameWinner",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (Client_Java.BoggledApp.RoundHelper.id (), "Round", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Client_Java.BoggledApp.Round read (org.omg.CORBA.portable.InputStream istream)
  {
    Client_Java.BoggledApp.Round value = new Client_Java.BoggledApp.Round ();
    value.gid = istream.read_long ();
    value.playersData = Client_Java.BoggledApp.PlayerListHelper.read (istream);
    value.characterSet = istream.read_string ();
    value.roundLength = istream.read_long ();
    value.roundNumber = istream.read_long ();
    value.roundWinner = Client_Java.BoggledApp.PlayerHelper.read (istream);
    value.gameWinner = Client_Java.BoggledApp.PlayerHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Client_Java.BoggledApp.Round value)
  {
    ostream.write_long (value.gid);
    Client_Java.BoggledApp.PlayerListHelper.write (ostream, value.playersData);
    ostream.write_string (value.characterSet);
    ostream.write_long (value.roundLength);
    ostream.write_long (value.roundNumber);
    Client_Java.BoggledApp.PlayerHelper.write (ostream, value.roundWinner);
    Client_Java.BoggledApp.PlayerHelper.write (ostream, value.gameWinner);
  }

}
