package tool.agreement;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class DataBuffer {

	public int DATABUFFEREOF = 0x7fffffff;
	public int DATABUFFERMAXBUFFERSIZE = 40960;

	public char[] buffer;
	private char[] string_buffer;
	private int position;
	private int buffer_data_length;
	private int charsLength;

	public DataBuffer() {
		buffer = new char[DATABUFFERMAXBUFFERSIZE];
		position = 0;
		buffer_data_length = 0;
		charsLength = 0;
	}

	public void writeHeader(int head_Id) {
		WriteInt((int) 8);
		WriteInt(head_Id);
	}

	public void Seek(int pos) {
		if (pos < 0 || pos > DATABUFFERMAXBUFFERSIZE) {
			pos = DATABUFFEREOF;
		}
		position = pos;
		System.out.println("DataBuffer::Seek() pos = " + pos);
	}

	public int Tell() {
		return position;
	}

	public char ReadChar() {
//		System.out
//				.println(" ReadChar() buffer[position] = " + (int)buffer[position]);
		position=8;
//		System.out.println("buffer[position]="+buffer[position]);
		return buffer[position++];
	}

	public short ReadShort() {
		position += 2;
		return (short) ((buffer[position - 2] & 0xff) | ((buffer[position - 1] & 0xff) << 8));
	}

	public int ReadInt() {
		position += 4;
		return ((((buffer[position - 1] & 0xff) << 8 | (buffer[position - 2] & 0xff)) << 8) | (buffer[position - 3] & 0xff)) << 8
				| (buffer[position - 4] & 0xff);
	}

	public char[] ReadChars() {
		int len = ReadChar() & 0xFFFFFF;
//		System.out.println("charsLength="+len);
		charsLength = len; // added for continue play command, _CardCount =
							// readchars();
		return ReadRawBytes(len);
	}

	public char[] ReadRawBytes(int len) {
		// unsigned char* ret = new unsigned char[len];
		// System.arraycopy(buffer, position, ret, 0, len);
		// strncpy((char*)ret,(const char* )buffer+position, len);
//		System.out.println("DataBuffer::ReadRawBytes len = " + len);
		string_buffer = new char[len];
		
		if (len == 0)
			return null;
		for (int i = 0; i < len; i++) {
			string_buffer[i] = 0;
		}
		for (int i = 0; i < len; i++) {
			string_buffer[i] = buffer[position + i];
		}
		position += len;
//		System.out.println("String_buffer="+string_buffer);
		return string_buffer;
	};

	public String ReadString() {
		char[] a = ReadChars();
		if (a == null)
			return new String("");
		else
			return new String((char[]) a);
	}

	public String ReadString2() {
		int len = ReadShort() & 0xFFFFFF;
		char[] a = ReadRawBytes(len);
		if (a == null)
			return new String("");
		else
			return new String((char[]) a);
		// return string((char *)ReadRawBytes(len));
	}

	public void WriteChar(char value) {
		buffer[position++] = value;
		buffer_data_length += 2;// char2个字节
	}

	public void WriteShort(short value) {
		buffer[position++] = (char) value;
		buffer[position++] = (char) (value >> 8);
		buffer_data_length += 2;// short2个字节
	}

	public void WriteInt(int value) {
		buffer[position++] = (char) value;
		buffer[position++] = (char) (value >> 8);
		buffer[position++] = (char) (value >> 16);
		buffer[position++] = (char) (value >> 24);
		buffer_data_length += 4;// int4个字节
	}

	public void WriteLong(long value) {
		buffer[position++] = (char) value;
		buffer[position++] = (char) (value >> 8);
		buffer[position++] = (char) (value >> 16);
		buffer[position++] = (char) (value >> 24);
		buffer[position++] = (char) (value >> 32);
		buffer[position++] = (char) (value >> 40);
		buffer[position++] = (char) (value >> 48);
		buffer[position++] = (char) (value >> 56);
		buffer_data_length += 8;// long8个字节
	}

	// length为想要写入的长度，data_length为数据的长度
	public void WriteBytes(char[] data, int length, int data_length) {
		WriteBytes(data, 0, length, data_length);
	}

	// length为想要写入的长度，data_length为数据的长度
	public void WriteBytes(char[] data, int startPos, int length,
			int data_length) {
		// if(data==NULL || startPos<0 || length<0 || startPos>data_length)
		if (startPos < 0 || length < 0 || startPos > data_length) {
			return;
		}
		if (length > data_length - startPos) {
			length = data_length - startPos;
		}
//		if (length > 255) {
//			length = 255;
//		}
//		System.out.println("length="+length);
		WriteChar((char) length);
		// System.arraycopy(data, startPos, buffer, position, length);
		// strncpy((char *)buffer+position, (const char*)data+startPos, length);
		if (data != null) {
			for (int i = 0; i < length; i++) {
				buffer[position + i] = data[startPos + i];
//				System.out.println("position+i="+(position+i)+",startPos + i="+(startPos + i));
			}
		}
//		System.out.println("buffer="+new String(buffer));
		position += length;
		buffer_data_length += length;
	}

	public void WriteRawBytes(char[] data, int startPos, int length,
			int data_length) {
		// if(data==NULL || startPos<0 || length<=0 || startPos>data_length)
		if (startPos < 0 || length <= 0 || startPos > data_length) {
			return;
		}
		if (length > data_length - startPos) {
			length = data_length - startPos;
		}
		// System.arraycopy(data, startPos, buffer, position, length);
		// strncpy((char *)buffer+position, (const char*)data+startPos, length);
		if (data != null) {
			for (int i = 0; i < length; i++) {
				buffer[position + i] = data[startPos + i];
			}
		}
		position += length;
		buffer_data_length += length;
	}

	public void WriteString(String value) {
		if (value.isEmpty() || value.length() == 0) {
			WriteChar((char) 0);
		} else {
			char[] v = value.toCharArray();
			WriteBytes((char[]) v, 0, value.length(), value.length());
		}
	}

	public void WriteString2(String value) {
		if (value.isEmpty() || value.length() == 0) {
			WriteShort((short) 0);
		} else {
			// byte[] v = value.getBytes();
			char[] v = value.toCharArray();
			int data_length = value.length();
			WriteShort((short) value.length());
			WriteRawBytes((char[]) v, 0, value.length(), data_length);
		}
	}

	public void WriteBoolean(boolean v) {
		WriteChar((char) (v ? 1 : 0));
	}

	public boolean ReadBoolean() {
		return ReadChar() != 0;
	}

	public long ReadLong() {
		position += 8;
		return ((((((((buffer[position - 1] & 0xffL) << 8 | buffer[position - 2] & 0xffL) << 8 | buffer[position - 3] & 0xffL) << 8 | buffer[position - 4] & 0xffL) << 8 | buffer[position - 5] & 0xffL) << 8 | (buffer[position - 6] & 0xffL)) << 8) | (buffer[position - 7] & 0xffL)) << 8
				| (buffer[position - 8] & 0xffL);
	}

	public char[] getBuffer() {
		return buffer;
	}

	public int getWritedLen() {
		return buffer_data_length;
	}

	public int getCharsLength() {
		return charsLength;
	}

	public byte[] readByte() {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(buffer.length);
		cb.put(buffer);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	}
	public char[] getChars (byte[] bytes) {
	      Charset cs = Charset.forName ("UTF-8");
	      System.out.println("bytes.length="+bytes.length);
	      ByteBuffer bb = ByteBuffer.allocate (bytes.length);
	      bb.put (bytes);
	                 bb.flip ();
	       CharBuffer cb = cs.decode (bb);
	       buffer = cb.array();
	   return cb.array();
	}
	public String getString(){
		char[] c = buffer;
//		System.out.println((int)c[0]);
		char[] c1;
		c1 = Arrays.copyOfRange(c, 1, c.length);
//		System.out.print(new String(c1));
		return new String(c1);
	}
}
