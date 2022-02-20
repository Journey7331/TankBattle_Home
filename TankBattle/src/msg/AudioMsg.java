package msg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 播放音效的消息
 */

public class AudioMsg
{
	private byte type;

	public void writeOutputStream(ObjectOutputStream oos)
	{
		try
		{
			oos.writeByte(type);
			
		} catch (Exception e)
		{}
		
	}

	public static void readInputStream(ObjectInputStream ois, MsgInfo info, int size)
	{
		try
		{
			while(size > 0)
			{
				AudioMsg msg = new AudioMsg();
				msg.type = ois.readByte();
				info.getAudios().add(msg);
				size--;
			}
			
		} catch (Exception e) {
			
		}
		
	}
	public byte getType()
	{
		return type;
	}

	public void setType(byte type)
	{
		this.type = type;
	}
	
	public AudioMsg() {}
	public AudioMsg(int type)
	{
		this.type = (byte) type;
	}

}
