package util;

public class ByteUtil
{
	public static int byteArrayToInt(byte[] b,int offset)
	{
		return b[3+offset] & 0xFF | (b[2+offset] & 0xFF) << 8 | (b[1+offset] & 0xFF) << 16 | (b[offset] & 0xFF) << 24;
	}

	public static void intToByteArray(byte[]b,int i,int offset) 
	{
        b[offset] = (byte)((i >> 24) & 0xFF);
        b[offset + 1] = (byte)((i >> 16) & 0xFF);
        b[offset + 2] = (byte)((i >> 8) & 0xFF);
        b[offset + 3] = (byte)(i & 0xFF);
    }
	public static byte[] intToByteArray(int i) 
	{
		byte b [] = new byte[4];
        b[0] = (byte)((i >> 24) & 0xFF);
        b[1] = (byte)((i >> 16) & 0xFF);
        b[2] = (byte)((i >> 8) & 0xFF);
        b[3] = (byte)(i & 0xFF);
        
        return b;
    }
}
