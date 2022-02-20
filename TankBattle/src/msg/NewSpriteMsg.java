package msg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import layer.MapLayer;
import sprite.BombSprite;
import sprite.BuffSprite;
import sprite.BulletSprite;
import sprite.EnemyTankSprite;
/**
 * 创建精灵消息
 */
public class NewSpriteMsg
{
	private short id;
	// 0 normal 1 middle 2 height 3 bullet 4 bomb 5 buff
	private byte type;
	private short x;
	private short y;
	private byte dir;
	
	public void writeOutputStream(ObjectOutputStream oos)
	{
		try
		{
			
			oos.writeShort(id);
			oos.writeByte(type);
			oos.writeShort(x - MapLayer.left);
			oos.writeShort(y - MapLayer.top);
			oos.writeByte(dir);;
			
		} catch (Exception e)
		{}
	}
	public static void readInputStream(ObjectInputStream ois, MsgInfo info, int size)
	{
		try
		{
			while(size > 0)
			{
				NewSpriteMsg msg = new NewSpriteMsg();
				msg.id = ois.readShort();
				msg.type = ois.readByte();
				msg.x = (short) (ois.readShort() + MapLayer.left);
				msg.y = (short) (ois.readShort() + MapLayer.top);
				msg.dir = ois.readByte();
				
				info.getNewSprites().add(msg);
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
	public NewSpriteMsg() {}
	public NewSpriteMsg(EnemyTankSprite sprite)
	{
		this.id = sprite.getId();
		this.type = (byte) sprite.getTankType().ordinal();
		this.x = (short) sprite.getX();
		this.y = (short) sprite.getY();
		this.dir = (byte) sprite.getDirectionType().ordinal();
	}
	public NewSpriteMsg(BulletSprite sprite)
	{
		this.id = sprite.getId();
		this.type = 3;
		this.x = (short) sprite.getX();
		this.y = (short) sprite.getY();
		this.dir = (byte) sprite.getDirectionType().ordinal();
	}
	public NewSpriteMsg(BombSprite sprite)
	{
		this.id = sprite.getId();
		this.type = 4;
		this.x = (short) sprite.getX();
		this.y = (short) sprite.getY();
		this.dir = (byte) sprite.getType().ordinal();
	}
	public NewSpriteMsg(BuffSprite sprite)
	{
		this.id = sprite.getId();
		this.type = 5;
		this.x = (short) sprite.getX();
		this.y = (short) sprite.getY();
		this.dir = (byte) sprite.getBuffType().ordinal();
	}
	
}
