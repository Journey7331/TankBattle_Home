package msg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MsgInfo
{
	private static MsgInfo instance;
	
	private List<NewHeroMsg> newHeros = new ArrayList<NewHeroMsg>();
	private List<NewSpriteMsg> newSprites = new ArrayList<NewSpriteMsg>();
	private List<MoveMsg> moveMsgs = new ArrayList<MoveMsg>();
	private List<HitSpriteMsg> hitSprites = new ArrayList<HitSpriteMsg>();
	private List<EatBuffMsg> eatBuffs = new ArrayList<EatBuffMsg>();
	private List<HitMapMsg> hitMaps = new ArrayList<HitMapMsg>();
	private List<AudioMsg> audios = new ArrayList<AudioMsg>();
	
	public Map<Short,Integer> moveMap = new HashMap<Short, Integer>();
	public Map<Short,Integer> hitMap = new HashMap<Short, Integer>();
	public Map<Short,Integer> eatMap = new HashMap<Short, Integer>();
	
	
	public void reset()
	{
		newHeros.clear();
		newSprites.clear();
		moveMsgs.clear();
		hitSprites.clear();
		eatBuffs.clear();
		hitMaps.clear();
		audios.clear();
		
		moveMap.clear();
		hitMap.clear();
		eatMap.clear();
	}
	public static MsgInfo parserPacket(byte[] body)
	{
		try
		{
			MsgInfo info = new MsgInfo();
			ByteArrayInputStream bis = new ByteArrayInputStream(body);
			ObjectInputStream ois = new ObjectInputStream(bis);
			int size = ois.readByte();
			if(size > 0)
			{
				NewHeroMsg.readInputStream(ois, info, size);
			}
			size = ois.readByte();
			if(size > 0)
			{
				NewSpriteMsg.readInputStream(ois, info, size);
			}
			size = ois.readByte();
			if(size > 0)
			{
				MoveMsg.readInputStream(ois, info, size);
			}
			size = ois.readByte();
			if(size > 0)
			{
				HitSpriteMsg.readInputStream(ois, info, size);
			}
			size = ois.readByte();
			if(size > 0)
			{
				EatBuffMsg.readInputStream(ois, info, size);
			}
			size = ois.readByte();
			if(size > 0)
			{
				HitMapMsg.readInputStream(ois, info, size);
			}
			size = ois.readByte();
			if(size > 0)
			{
				AudioMsg.readInputStream(ois, info, size);
			}
			
			return info;
			
			
		} catch (Exception e)
		{
			return null;
		}
		
	
	}
	public byte[] toPacket()
	{
		if(audios.size()==0&&hitMaps.size()==0 && newHeros.size() == 0 && newSprites.size() == 0 && moveMsgs.size() ==0 && hitSprites.size() ==0 && eatBuffs.size() ==0)
		{
			return null;
		}
		
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			if(newHeros.size() > 0)
			{
				oos.writeByte(newHeros.size());
				for(NewHeroMsg msg:newHeros)
				{
					msg.writeOutputStream(oos);
				}
				
			}
			else
			{
				oos.writeByte(0);
			}
			
			if(newSprites.size() > 0)
			{
				oos.writeByte(newSprites.size());
				for(NewSpriteMsg msg:newSprites)
				{
					msg.writeOutputStream(oos);
				}
				
			}
			else
			{
				oos.writeByte(0);
			}
			if(moveMsgs.size() > 0)
			{
				oos.writeByte(moveMsgs.size());
				for(MoveMsg msg:moveMsgs)
				{
					msg.writeOutputStream(oos);
				}
				
			}
			else
			{
				oos.writeByte(0);
			}
			if(hitSprites.size() > 0)
			{
				oos.writeByte(hitSprites.size());
				for(HitSpriteMsg msg:hitSprites)
				{
					msg.writeOutputStream(oos);
				}
				
			}
			else
			{
				oos.writeByte(0);
			}
			if(eatBuffs.size() > 0)
			{
				oos.writeByte(eatBuffs.size());
				for(EatBuffMsg msg:eatBuffs)
				{
					msg.writeOutputStream(oos);
				}
				
			}
			else
			{
				oos.writeByte(0);
			}
			if(hitMaps.size() > 0)
			{
				oos.writeByte(hitMaps.size());
				for(HitMapMsg msg:hitMaps)
				{
					msg.writeOutputStream(oos);
				}
				
			}
			else
			{
				oos.writeByte(0);
			}
			if(audios.size() > 0)
			{
				oos.writeByte(audios.size());
				for(AudioMsg msg:audios)
				{
					msg.writeOutputStream(oos);
				}
				
			}
			else
			{
				oos.writeByte(0);
			}
			
			oos.close();
			reset();
			return bos.toByteArray();
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	public static MsgInfo newInstance()
	{
		if(instance == null)
		{
			instance = new MsgInfo();
		}
		return instance;
	}
	public List<NewHeroMsg> getNewHeros()
	{
		return newHeros;
	}
	public void setNewHeros(List<NewHeroMsg> newHeros)
	{
		this.newHeros = newHeros;
	}
	public List<NewSpriteMsg> getNewSprites()
	{
		return newSprites;
	}
	public void setNewSprites(List<NewSpriteMsg> newSprites)
	{
		this.newSprites = newSprites;
	}
	public List<MoveMsg> getMoveMsgs()
	{
		return moveMsgs;
	}
	public void setMoveMsgs(List<MoveMsg> moveMsgs)
	{
		this.moveMsgs = moveMsgs;
	}
	public List<HitSpriteMsg> getHitSprites()
	{
		return hitSprites;
	}
	public void setHitSprites(List<HitSpriteMsg> hitSprites)
	{
		this.hitSprites = hitSprites;
	}
	public List<EatBuffMsg> getEatBuffs()
	{
		return eatBuffs;
	}
	public void setEatBuffs(List<EatBuffMsg> eatBuffs)
	{
		this.eatBuffs = eatBuffs;
	}
	public List<HitMapMsg> getHitMaps()
	{
		return hitMaps;
	}
	public void setHitMaps(List<HitMapMsg> hitMaps)
	{
		this.hitMaps = hitMaps;
	}
	public List<AudioMsg> getAudios()
	{
		return audios;
	}
	public void setAudios(List<AudioMsg> audios)
	{
		this.audios = audios;
	}
	private MsgInfo() {}
	
	
	
	
	
}
