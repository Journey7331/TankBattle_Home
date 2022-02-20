package msg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import layer.MapLayer;
import sprite.AbstractMovableSprite;
/**
 * 精灵移动的消息
 */
public class MoveMsg
{
	private short id;
	private short x;
	private short y;
	private byte dir;
	
	public MoveMsg(AbstractMovableSprite sprite)
	{
		this.id = sprite.getId();
		this.x = (short) sprite.getX();
		this.y = (short) sprite.getY();
		this.dir = (byte) sprite.getDirectionType().ordinal();
	}
	public void writeOutputStream(ObjectOutputStream oos)
	{
		try
		{
			oos.writeShort(id);
			oos.writeShort(x - MapLayer.left);
			oos.writeShort(y - MapLayer.top);
			oos.writeByte(dir);
			
		} catch (Exception e)
		{}
		
	}
	public static void readInputStream(ObjectInputStream ois, MsgInfo info, int size)
	{
		try
		{
			while(size > 0)
			{
				MoveMsg msg = new MoveMsg();
				msg.id = ois.readShort();
				msg.x = (short) (ois.readShort() + MapLayer.left);
				msg.y = (short) (ois.readShort() + MapLayer.top);
				msg.dir = ois.readByte();
				
				info.moveMap.put(msg.id, info.getMoveMsgs().size());
				info.getMoveMsgs().add(msg);
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
	public short getX()
	{
		return x;
	}
	public void setX(short x)
	{
		this.x = x;
	}
	public short getY()
	{
		return y;
	}
	public void setY(short y)
	{
		this.y = y;
	}
	public byte getDir()
	{
		return dir;
	}
	public void setDir(byte dir)
	{
		this.dir = dir;
	}
	public MoveMsg()
	{
		
	}
	
	
	
	
}
