package sprite;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import connector._KeyAction;
import constant.Constant;
import enums.AudioType;
import enums.BombType;
import enums.BuffType;
import enums.DirectionType;
import enums.GameModel;
import enums.TankType;
import layer.MapLayer;
import msg.MsgInfo;
import msg.NewHeroMsg;
import util.AudioUtil;
/**
 * 英雄坦克
 */
public class HeroTankSprite extends AbstractTankSprite implements _KeyAction
{

	private static final long serialVersionUID = 1583290476831781427L;
	private int currentKeyCode = -1;
	
	
	public HeroTankSprite(MapLayer mapLayer,TankType tankType)
	{
		super(mapLayer);
		this.setTankType(tankType);
        this.initTank();
	
	}
	public HeroTankSprite(MapLayer mapLayer,NewHeroMsg msg)
	{
		super(mapLayer);
		this.setId(msg.getId());
		this.setTankType(TankType.valueOf(msg.getType()));;
        
		this.setDirectionType(DirectionType.valueOf(msg.getDir()));
		this.setW(64);
		this.setH(64);
		this.setSpeed(3);
		this.setLife(msg.getLife());
		this.setX(msg.getX());
		this.setY(msg.getY());

	}
	public HeroTankSprite(MapLayer mapLayer,HeroTankSprite sprite)
	{
		super(mapLayer);
		this.setTankType(sprite.getTankType());
        
		this.setDirectionType(DirectionType.UP);
		this.setW(64);
		this.setH(64);
		this.setSpeed(3);
		this.setLife(sprite.getLife());
		if(TankType.LEFT_HERO == this.getTankType())
		{
			this.setX(8 * Constant.MIN_UNIT + MapLayer.left);
		}
		else if(TankType.RIGHT_HERO == this.getTankType())
		{
			this.setX(16 * Constant.MIN_UNIT + MapLayer.left);
		}
		this.setY(24 * Constant.MIN_UNIT + MapLayer.top);
	
		this.setStarCount(sprite.getStarCount());
	}
	/**
	 * 初始化坦克
	 */
	public void initTank()
	{
		this.setDirectionType(DirectionType.UP);
		this.setW(64);
		this.setH(64);
		this.setSpeed(3);
		this.setLife(3);
		
		//我方坦克的位置初始化
		if(TankType.LEFT_HERO == this.getTankType())
		{
			this.setX(8 * Constant.MIN_UNIT + MapLayer.left);
		}
		else if(TankType.RIGHT_HERO == this.getTankType())
		{
			this.setX(16 * Constant.MIN_UNIT + MapLayer.left);
		}
		this.setY(24 * Constant.MIN_UNIT + MapLayer.top);
	}
	@Override
	public void draw(Graphics g)
	{
		int dx1 = this.getX();
		int dy1 = this.getY();
		int dx2 = dx1 + this.getW();
		int dy2 = dy1 + this.getH();
		
		int sx1 = 0;
		if(this.getTankType() == TankType.LEFT_HERO)
		{
			//上下左右
			sx1 = this.getDirectionType().ordinal() * this.getW();
		}
		else 
		{
			sx1 = (this.getDirectionType().ordinal()+4) *this.getW();
		}
		int sy1 = 0;
		int sx2 = sx1 + this.getW();
		int sy2 = sy1 + this.getW();
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		
		
		if(this.isProtected())
		{
			
			this.setProtectedTime(this.getProtectedTime()-1);
			
			sx1 = 320;
			sy1 = 192 + (this.getProtectedTime() / 4  % 2) * this.getH();
			sx2 = sx1 + this.getW();
			sy2 = sy1 + this.getH();
			
			g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
			
			if(this.getProtectedTime()==0)
			{
				this.setProtected(false);
			}
		}

	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		if(this.currentKeyCode ==-1)
		{
			return;
		}
		move();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if(Constant.selectModel == GameModel.PLAYERS )
		{
			if(this.getTankType() == TankType.LEFT_HERO)
			{
				if(KeyEvent.VK_A == keyCode || KeyEvent.VK_D == keyCode || KeyEvent.VK_W == keyCode || KeyEvent.VK_S == keyCode)
				{
					this.currentKeyCode = keyCode;
				}
			}
			else if(this.getTankType() == TankType.RIGHT_HERO)
			{
				if(KeyEvent.VK_UP == keyCode || KeyEvent.VK_DOWN == keyCode || KeyEvent.VK_LEFT == keyCode || KeyEvent.VK_RIGHT == keyCode)
				{
					this.currentKeyCode = keyCode;
				}
			}
			
		}
		else
		{
			this.currentKeyCode = keyCode;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if(Constant.selectModel == GameModel.PLAYERS )
		{
			if(this.getTankType() == TankType.LEFT_HERO)
			{
				if(KeyEvent.VK_A == keyCode || KeyEvent.VK_D == keyCode || KeyEvent.VK_W == keyCode || KeyEvent.VK_S == keyCode)
				{
					this.currentKeyCode = -1;
				}
				else if(KeyEvent.VK_SPACE == keyCode)
				{
					fire();
				}
			}
			else if(this.getTankType() == TankType.RIGHT_HERO)
			{
				if(KeyEvent.VK_UP == keyCode || KeyEvent.VK_DOWN == keyCode || KeyEvent.VK_LEFT == keyCode || KeyEvent.VK_RIGHT == keyCode)
				{
					this.currentKeyCode = -1;
				}
				else if(KeyEvent.VK_ENTER == keyCode)
				{
					fire();
				}
			}
			
		}
		else
		{
			this.currentKeyCode = -1;
			if(KeyEvent.VK_ENTER == keyCode || KeyEvent.VK_SPACE == keyCode)
			{
				fire();
			}
		}
		
	}
	@Override
	public void move()
	{
		int tempX = this.getX();
		int tempY = this.getY();
		DirectionType directionType = this.getDirectionType();
		if(Constant.selectModel == GameModel.PLAYERS )
		{
			if(this.getTankType() == TankType.LEFT_HERO)
			{
				if(this.currentKeyCode == KeyEvent.VK_W)
				{
					tempY = tempY - this.getSpeed();
					directionType = DirectionType.UP;
					
				}
				else if(this.currentKeyCode == KeyEvent.VK_S)
				{
					tempY = tempY + this.getSpeed();
					directionType = DirectionType.DOWN;
				}
				else if(this.currentKeyCode == KeyEvent.VK_A)
				{
					tempX = tempX - this.getSpeed();
					directionType = DirectionType.LEFT;
					
				}
				else if(this.currentKeyCode == KeyEvent.VK_D)
				{
					tempX= tempX + this.getSpeed();
					directionType = DirectionType.RIGHT;
				}
				
			}
			else if(this.getTankType() == TankType.RIGHT_HERO)
			{
				if(this.currentKeyCode == KeyEvent.VK_UP)
				{
					tempY = tempY - this.getSpeed();
					directionType = DirectionType.UP;
					
				}
				else if(this.currentKeyCode == KeyEvent.VK_DOWN)
				{
					tempY = tempY + this.getSpeed();
					directionType = DirectionType.DOWN;
				}
				else if(this.currentKeyCode == KeyEvent.VK_LEFT)
				{
					tempX = tempX - this.getSpeed();
					directionType = DirectionType.LEFT;
					
				}
				else if(this.currentKeyCode == KeyEvent.VK_RIGHT)
				{
					tempX= tempX + this.getSpeed();
					directionType = DirectionType.RIGHT;
				}
			}
		}
		else
		{
			if(this.currentKeyCode == KeyEvent.VK_UP)
			{
				tempY = tempY - this.getSpeed();
				directionType = DirectionType.UP;
				
			}
			else if(this.currentKeyCode == KeyEvent.VK_DOWN)
			{
				tempY = tempY + this.getSpeed();
				directionType = DirectionType.DOWN;
			}
			else if(this.currentKeyCode == KeyEvent.VK_LEFT)
			{
				tempX = tempX - this.getSpeed();
				directionType = DirectionType.LEFT;
				
			}
			else if(this.currentKeyCode == KeyEvent.VK_RIGHT)
			{
				tempX= tempX + this.getSpeed();
				directionType = DirectionType.RIGHT;
			}
			
		}
		if(!hitBorder(tempX, tempY, directionType) && !this.getMapLayer().tankHitMap(tempX, tempY, directionType))
		{
			this.setX(tempX);
			this.setY(tempY);
		}
		
		this.setDirectionType(directionType);
		
		int count = this.getMapLayer().getBuffSprites().size() - 1;
		for(int i=count;i>=0;i--)
		{
			BuffSprite sprite = this.getMapLayer().getBuffSprites().get(i);
			if(this.hitRect(sprite.getRect()))
			{
				eatBuff(sprite);
			}
		}
	}
	/**
	 * 吃宝物
	 */
	public void eatBuff(BuffSprite buffSprite)
	{
		buffSprite.setLive(false);
		if(buffSprite.getBuffType() == BuffType.ADD_LIFE)
		{
			this.setLife(this.getLife() + 1);
			AudioUtil.playAudio(AudioType.ADD_LIFE);
		}
		else if(buffSprite.getBuffType() == BuffType.STOP_ENEMY)
		{
			AudioUtil.playAudio(AudioType.EAT_BUFF);
			this.getMapLayer().stopEnemy();
			
		}
		else if(buffSprite.getBuffType() == BuffType.EAT_FE)
		{
			AudioUtil.playAudio(AudioType.EAT_BUFF);
			this.getMapLayer().protectedHome();
		}
		else if(buffSprite.getBuffType() == BuffType.KILL_ENEMY)
		{
			AudioUtil.playAudio(AudioType.BIG_BOMB);
			int count = this.getMapLayer().getEnemyTankSprites().size() - 1;
			for(int i=count;i>=0;i--)
			{
				EnemyTankSprite sprite = this.getMapLayer().getEnemyTankSprites().get(i);
				sprite.setLive(false);
				this.getMapLayer().addSprite(new BombSprite(sprite.getX(), sprite.getY(), BombType.BIG, false));
			}
		}
		else if(buffSprite.getBuffType() == BuffType.ADD_PROTECTED)
		{
			AudioUtil.playAudio(AudioType.EAT_BUFF);
			this.addProtected();
		}
		else if(buffSprite.getBuffType() == BuffType.EAT_STAR)
		{
			AudioUtil.playAudio(AudioType.EAT_BUFF);
			this.setStarCount(this.getStarCount()+1);
		}
	}
	/**
	 * 客户端吃宝物
	 */
	public void eatBuff(BuffType type)
	{
		if(type == BuffType.ADD_LIFE)
		{
			this.setLife(this.getLife() + 1);
		}
		else if(type == BuffType.STOP_ENEMY)
		{
			//this.getMapLayer().stopEnemy();
		}
		else if(type == BuffType.EAT_FE)
		{
			this.getMapLayer().protectedHome();
		}
		else if(type == BuffType.KILL_ENEMY)
		{
			int count = this.getMapLayer().getEnemyTankSprites().size() - 1;
			for(int i=count;i>=0;i--)
			{
				EnemyTankSprite sprite = this.getMapLayer().getEnemyTankSprites().get(i);
				sprite.setLive(false);
			}
		}
		else if(type == BuffType.ADD_PROTECTED)
		{
			this.addProtected();
		}
		else if(type == BuffType.EAT_STAR)
		{
			this.setStarCount(this.getStarCount()+1);
		}
	}
	@Override
	public void fire()
	{
		if(!this.isShooting())
		{
			AudioUtil.playAudio(AudioType.HERO_FIRE);
			this.getMapLayer().addSprite(new BulletSprite(getMapLayer(), this));
			this.setBulletCount(this.getBulletCount() + 1);
		}
		
	}
	@Override
	public boolean isShooting()
	{
		if(this.getStarCount() >=4 && this.getBulletCount() < (this.getStarCount() - 2))
		{
			return false;
		}
		
		return super.isShooting();
	}
	/**
	 * 添加保护状态
	 */
	public void addProtected()
	{
		this.setProtectedTime(400);
		this.setProtected(true);
	}
	@Override
	public void hit()
	{
		this.setLife(this.getLife() -1);
		if(this.getLife() <=0)
		{
			this.setLive(false);
		}
		else
		{
			this.currentKeyCode = -1;
			this.setDirectionType(DirectionType.UP);
			this.setSpeed(3);
			if(TankType.LEFT_HERO == this.getTankType())
			{
				this.setX(8 * Constant.MIN_UNIT + MapLayer.left);
			}
			else if(TankType.RIGHT_HERO == this.getTankType())
			{
				this.setX(16 * Constant.MIN_UNIT + MapLayer.left);
			}
			this.setY(24 * Constant.MIN_UNIT + MapLayer.top);
			this.setStarCount(0);
			addProtected();
		}
	}
	

}
