package msg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import enums.BuffType;
import sprite.HeroTankSprite;

/**
 * ³Ô±¦ÎïÏûÏ¢
 */
public class EatBuffMsg
{
	private short id;
	private byte type;
	
	public void writeOutputStream(ObjectOutputStream oos)
	{
		try
		{
			oos.writeShort(id);
			oos.writeByte(type);
			
		} catch (Exception e)
		{}
		
	}
	public static void readInputStream(ObjectInputStream ois, MsgInfo info, int size)
	{
		try
		{
			while (size >0)
			{
				EatBuffMsg msg = new EatBuffMsg();
				msg.id = ois.readShort();
				msg.type = ois.readByte();
				
				info.eatMap.put(msg.id, info.getEatBuffs().size());
				info.getEatBuffs().add(msg);
				size--;
			}
			
		} catch (Exception e)
		{}
		
	}
	
	public short getId()
	{
		return id;
	}
	public void setId(short id)
	{
		this.id = id;
	}
	public byte getType()
	{
		return type;
	}
	public void setType(byte type)
	{
		this.type = type;
	}
	public EatBuffMsg() {}
	public EatBuffMsg(HeroTankSprite sprite,BuffType type)
	{
		this.id = sprite.getId();
		this.type = (byte) type.ordinal();
	}
	
	
	
	
	
}
