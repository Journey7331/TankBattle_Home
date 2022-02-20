package msg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 地图被击中的消息
 */
public class HitMapMsg
{
	private byte x;
	private byte y;
	public void writeOutputStream(ObjectOutputStream oos)
	{
		try
		{
			oos.writeByte(x);
			oos.writeByte(y);
			
		} catch (Exception e)
		{}
	}
	public static void readInputStream(ObjectInputStream ois, MsgInfo info, int size)
	{
		try
		{
			while(size > 0)
			{
				HitMapMsg msg = new HitMapMsg();
				msg.x = ois.readByte();
				msg.y = ois.readByte();
				info.getHitMaps().add(msg);
				size--;
			}
			
		} catch (Exception e)
		{}
		
	}
	public byte getX()
	{
		return x;
	}
	public void setX(byte x)
	{
		this.x = x;
	}
	public byte getY()
	{
		return y;
	}
	public void setY(byte y)
	{
		this.y = y;
	}
	
	public HitMapMsg() {}
	public HitMapMsg(int x,int y)
	{
		this.x = (byte) x;
		this.y = (byte) y;
	}
	
	
	
}
