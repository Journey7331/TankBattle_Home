package msg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sprite.AbstractBaseSprite;

/**
 * 被打击的消息
 */
public class HitSpriteMsg
{
	private short id;

	public void writeOutputStream(ObjectOutputStream oos)
	{
		try
		{
			oos.writeShort(id);
			
		} catch (Exception e)
		{}
	}

	public static void readInputStream(ObjectInputStream ois, MsgInfo info, int size)
	{
		try
		{
			while(size > 0)
			{
				HitSpriteMsg msg = new HitSpriteMsg();
				msg.id = ois.readShort();
				info.hitMap.put(msg.id, info.getHitSprites().size());
				info.getHitSprites().add(msg);
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
	
	public HitSpriteMsg() {}
	public HitSpriteMsg(AbstractBaseSprite sprite)
	{
		this.id = sprite.getId();
	}


	
}
