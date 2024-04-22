package Server_Java.model.implementations.BoggledApp;


/**
* BoggledApp/RoundHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Monday, April 22, 2024 7:22:27 PM CST
*/

abstract public class RoundHelper
{
  private static String  _id = "IDL:BoggledApp/Round:1.0";

  public static void insert (org.omg.CORBA.Any a, Round that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Round extract (org.omg.CORBA.Any a)
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
          _tcOf_members0 = PlayerHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (PlayerListHelper.id (), "PlayerList", _tcOf_members0);
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
          _tcOf_members0 = PlayerHelper.type ();
          _members0[5] = new org.omg.CORBA.StructMember (
            "roundWinner",
            _tcOf_members0,
            null);
          _tcOf_members0 = PlayerHelper.type ();
          _members0[6] = new org.omg.CORBA.StructMember (
            "gameWinner",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (RoundHelper.id (), "Round", _members0);
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

  public static Round read (org.omg.CORBA.portable.InputStream istream)
  {
    Round value = new Round();
    value.gid = istream.read_long ();
    value.playersData = PlayerListHelper.read (istream);
    value.characterSet = istream.read_string ();
    value.roundLength = istream.read_long ();
    value.roundNumber = istream.read_long ();
    value.roundWinner = PlayerHelper.read (istream);
    value.gameWinner = PlayerHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Round value)
  {
    ostream.write_long (value.gid);
    PlayerListHelper.write (ostream, value.playersData);
    ostream.write_string (value.characterSet);
    ostream.write_long (value.roundLength);
    ostream.write_long (value.roundNumber);
    PlayerHelper.write (ostream, value.roundWinner);
    PlayerHelper.write (ostream, value.gameWinner);
  }

}
