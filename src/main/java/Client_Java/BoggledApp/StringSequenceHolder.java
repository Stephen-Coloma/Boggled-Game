package Client_Java.BoggledApp;


/**
* BoggledApp/StringSequenceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA IDL.idl
* Friday, May 10, 2024 6:34:33 AM SGT
*/


// sequence of strings
public final class StringSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public StringSequenceHolder ()
  {
  }

  public StringSequenceHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = StringSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    StringSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return StringSequenceHelper.type ();
  }

}
