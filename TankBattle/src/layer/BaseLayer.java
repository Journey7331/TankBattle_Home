package layer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import connector._Draw;
import connector._KeyAction;
import constant.Constant;
import msg.MsgInfo;
import scene.StageScene;
import sprite.BombSprite;
import sprite.BuffSprite;
import sprite.BulletSprite;
import sprite.EnemyTankSprite;
import sprite.HeroTankSprite;
import util.RandomUtil;

public class BaseLayer implements _Draw,_KeyAction,Serializable
{
	private static final long serialVersionUID = 4357005221782434482L;
	private List<HeroTankSprite> heroTanks = new ArrayList<HeroTankSprite>();
	private List<BulletSprite> bulletSprites = new ArrayList<BulletSprite>();
	private List<BombSprite> bombSprites = new ArrayList<BombSprite>();
	private List<EnemyTankSprite> enemyTankSprites = new ArrayList<EnemyTankSprite>();
	private List<BuffSprite> buffSprites = new ArrayList<BuffSprite>(); 
	
	private StageScene stageScene;
	/**
	 * 是否是停止敌方坦克状态
	 */
	private boolean isStopEnemy = false;
	/**
	 * 停止的时间
	 */
	private int stopEnemyTime = 0;
	
	public BaseLayer(StageScene stageScene)
	{
		this.stageScene = stageScene;
	}
	public void addSprite(BuffSprite sprite)
	{
		this.buffSprites.add(sprite);
	}
	public void addSprite(HeroTankSprite sprite)
	{
		this.heroTanks.add(sprite);
	}
	public void addSprite(BulletSprite sprite)
	{
		this.bulletSprites.add(sprite);
	}
	public void addSprite(BombSprite sprite)
	{
		this.bombSprites.add(sprite);
	}
	public void addSprite(EnemyTankSprite sprite)
	{
		this.enemyTankSprites.add(sprite);
	}
	/**
	 * 添加停止状态
	 */
	public void stopEnemy()
	{
		isStopEnemy = true;
		stopEnemyTime = 500;
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		int count = heroTanks.size() - 1;
		for(int i=count;i>=0;i--)
		{
			HeroTankSprite sprite = heroTanks.get(i);
			if(sprite.isLive())
			{
				sprite.keyPressed(e);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int count = heroTanks.size() - 1;
		for(int i=count;i>=0;i--)
		{
			HeroTankSprite sprite = heroTanks.get(i);
			if(sprite.isLive())
			{
				sprite.keyReleased(e);
			}
		}
	}

	@Override
	public void draw(Graphics g)
	{
		int count = heroTanks.size() - 1;
		for(int i=count;i>=0;i--)
		{
			HeroTankSprite sprite = heroTanks.get(i);
			if(sprite.isLive())
			{
				sprite.draw(g);
			}
		}
		
		count = enemyTankSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			EnemyTankSprite sprite = enemyTankSprites.get(i);
			if(sprite.isLive())
			{
				sprite.draw(g);
			}
		}
		
		count = bulletSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			BulletSprite sprite = bulletSprites.get(i);
			if(sprite.isLive())
			{
				sprite.draw(g);
			}
		}
		count = bombSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			BombSprite sprite = bombSprites.get(i);
			if(sprite.isLive())
			{
				sprite.draw(g);
			}
		}
		count = buffSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			BuffSprite sprite = buffSprites.get(i);
			if(sprite.isLive())
			{
				sprite.draw(g);
			}
		}
	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		if(isStopEnemy)
		{
			stopEnemyTime--;
			if(stopEnemyTime == 0)
			{
				isStopEnemy = false;
			}
		}
		
		int count = bulletSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			BulletSprite sprite = bulletSprites.get(i);
			if(sprite.isLive())
			{
				sprite.dispatcher(info);
			}
			else
			{
				bulletSprites.remove(sprite);
				if(sprite.getTankSprite()!=null)
				{
					sprite.getTankSprite().setBulletCount(sprite.getTankSprite().getBulletCount() -1);
				}
			}
		}
		
		
		count = enemyTankSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			EnemyTankSprite sprite = enemyTankSprites.get(i);
			if(sprite.isLive())
			{
				if(!isStopEnemy)
				{
					sprite.dispatcher(info);
				}
			}
			else
			{
				enemyTankSprites.remove(sprite);
				if(!BuffSprite.isShow)
				{    //随机生成宝物
					if(RandomUtil.nextInt(4 + Constant.currentStage) == 0)
					{
						addSprite(new BuffSprite());
					}
				}
			}
		}
		
		count = bombSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			BombSprite sprite = bombSprites.get(i);
			if(sprite.isLive())
			{
				sprite.dispatcher(info);
			}
			else
			{
				bombSprites.remove(sprite);
			}
		}
		count = heroTanks.size() - 1;
		for(int i=count;i>=0;i--)
		{
			HeroTankSprite sprite = heroTanks.get(i);
			if(sprite.isLive())
			{
				sprite.dispatcher(info);
			}
			else
			{
				heroTanks.remove(sprite);
			}
		}
		count = buffSprites.size() - 1;
		for(int i=count;i>=0;i--)
		{
			BuffSprite sprite = buffSprites.get(i);
			if(sprite.isLive())
			{
				sprite.dispatcher(info);
			}
			else
			{
				buffSprites.remove(sprite);
			}
		}
		
	}
	public List<HeroTankSprite> getHeroTanks()
	{
		return heroTanks;
	}
	public List<BulletSprite> getBulletSprites()
	{
		return bulletSprites;
	}
	public List<BombSprite> getBombSprites()
	{
		return bombSprites;
	}
	public List<EnemyTankSprite> getEnemyTankSprites()
	{
		return enemyTankSprites;
	}
	public StageScene getStageScene()
	{
		return stageScene;
	}
	public void setStageScene(StageScene stageScene)
	{
		this.stageScene = stageScene;
	}
	public List<BuffSprite> getBuffSprites()
	{
		return buffSprites;
	}

}
