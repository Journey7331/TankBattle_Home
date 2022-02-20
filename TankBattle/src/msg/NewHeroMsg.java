package msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import layer.MapLayer;
import sprite.HeroTankSprite;
/**
 * 创建英雄消息
 */
public class NewHeroMsg
{
	private short id;
	private byte type;
	private short x;
	private short y;
	private byte dir;
	private byte life;
	
	public NewHeroMsg() {}
	public NewHeroMsg(HeroTankSprite sprite)
	{
		this.id = sprite.getId();
		this.type = (byte) sprite.getTankType().ordinal();
		this.x = (short) sprite.getX();
		this.y = (short) sprite.getY();
		this.dir = (byte) sprite.getDirectionType().ordinal();
		this.life = (byte) sprite.getLife();
	}
	public static void readInputStream(ObjectInputStream ois,MsgInfo info,int size)
	{
	
		try
		{
			while(size > 0 )
			{
				NewHeroMsg msg = new NewHeroMsg();
				msg.id = ois.readShort();
				msg.type = ois.readByte();
				msg.x = (short) (ois.readShort() + MapLayer.left);
				msg.y = (short) (ois.readShort() + MapLayer.top);
				msg.dir = ois.readByte();
				msg.life = ois.readByte();
				
				info.getNewHeros().add(msg);
				size--;
			}
			
		} catch (Exception e)
		{}
		
	}
	public void writeOutputStream(ObjectOutputStream oos)
	{
		try
		{
			oos.writeShort(id);
			oos.writeByte(type);
			oos.writeShort(x - MapLayer.left);
			oos.writeShort(y - MapLayer.top);
			oos.writeByte(dir);
			oos.writeByte(life);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
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
	public byte getLife()
	{
		return life;
	}
	public void setLife(byte life)
	{
		this.life = life;
	}
	@Override
	public String toString()
	{
		return "NewHeroMsg [id=" + id + ", type=" + type + ", x=" + x + ", y=" + y + ", dir=" + dir + ", life=" + life
				+ "]";
	}
	
	
}
