package sprite;

import java.awt.Graphics;
import java.util.List;

import constant.Constant;
import enums.BombType;
import enums.BulletType;
import enums.DirectionType;
import layer.MapLayer;
import msg.MsgInfo;
import msg.NewSpriteMsg;
/**
 * 子弹精灵
 */
public class BulletSprite extends AbstractMovableSprite
{
	private static final long serialVersionUID = 6284690928475034117L;
	private AbstractTankSprite tankSprite;
	private BulletType bulletType;
	private DirectionType directionType;
	
	public BulletSprite(MapLayer mapLayer,AbstractTankSprite sprite)
	{
		super(mapLayer);
		
		this.tankSprite = sprite;
		sprite.setShooting(true);
		this.directionType = sprite.getDirectionType();
		if(sprite instanceof HeroTankSprite)
		{
			this.bulletType = BulletType.HERO;
		}
		else
		{
			this.bulletType = BulletType.ENEMY;
		}
		this.setSpeed(7);
		this.setW(Constant.BULLET_SIZE);
		this.setH(Constant.BULLET_SIZE);
		if(DirectionType.UP == sprite.getDirectionType())
		{
			this.setX(sprite.getX() + sprite.getW() /2 - this.getW() /2);
			this.setY(sprite.getY());
		}
		else if(DirectionType.DOWN == sprite.getDirectionType())
		{
			this.setX(sprite.getX() + sprite.getW() / 2  - this.getW() / 2);
			this.setY(sprite.getY() + sprite.getH() - this.getH());
		}
		else if(DirectionType.LEFT == sprite.getDirectionType())
		{
			this.setX(sprite.getX());
			this.setY(sprite.getY() + sprite.getH() / 2 - this.getW() / 2);
		}
		else if(DirectionType.RIGHT == sprite.getDirectionType())
		{
			this.setX(sprite.getX() + sprite.getW() - this.getW());
			this.setY(sprite.getY() + sprite.getH() / 2 - this.getW() / 2);
		}
	}

	public BulletSprite(MapLayer mapLayer, NewSpriteMsg msg)
	{
		super(mapLayer);
		this.setId(msg.getId());
		this.directionType = DirectionType.valueOf(msg.getDir());
		this.setSpeed(7);
		this.setW(Constant.BULLET_SIZE);
		this.setH(Constant.BULLET_SIZE);
		this.setX(msg.getX());
		this.setY(msg.getY());
	}

	@Override
	public void draw(Graphics g)
	{
		int dx1 = this.getX();
		int dy1 = this.getY();
		int dx2 = dx1 + this.getW();
		int dy2 = dy1 + this.getH();
		
		int sx1 = 160 + this.directionType.ordinal() * Constant.BULLET_SIZE;
		int sy1 = 192;
		int sx2 = sx1 + this.getW();
		int sy2 = sy1 + this.getH();
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		move();

	}

	@Override
	public void move()
	{
		if(DirectionType.UP == this.directionType)
		{
			this.setY(this.getY() - this.getSpeed());
		}
		else if(DirectionType.DOWN == this.directionType)
		{
			this.setY(this.getY() + this.getSpeed());
		}
		else if(DirectionType.LEFT == this.directionType)
		{
			this.setX(this.getX() - this.getSpeed());
		}
		else if(DirectionType.RIGHT == this.directionType)
		{
			this.setX(this.getX() + this.getSpeed());
		}
		
		if(hitBorder(this.getX(), this.getY(), directionType) || this.getMapLayer().bulletHitMap(this.getX(), this.getY(), directionType,this.tankSprite))
		{
			this.tankSprite.setShooting(false);
			this.setLive(false);
			this.getMapLayer().addSprite(new BombSprite(this.getX(), this.getY(), BombType.NORAML, true));
			return;
		}
		if(bulletHitBullet())
		{
			return ;
		}
		if(this.bulletType == BulletType.HERO)
		{
			
			if(this.bulletHitTank(this.getMapLayer().getEnemyTankSprites(), true))
			{
				return;
			}
			
		}
		else
		{
			if(bulletHitTank(this.getMapLayer().getHeroTanks(), false))
			{
				return;
			}
		}
	}
	
	
	/**
	 * 判断子弹是否与其它子弹有碰撞
	 * true： 有 false 没有
	 */
	public boolean bulletHitBullet()
	{
		List<BulletSprite> list = this.getMapLayer().getBulletSprites();
		int count = list.size() -1;
		for(int i = count ;i>=0;i--)
		{
			BulletSprite sprite = list.get(i);
			if(this != sprite && hitRect(sprite.getRect()))
			{
				this.tankSprite.setShooting(false);
				sprite.getTankSprite().setShooting(false);
				
				
				this.setLive(false);
				sprite.setLive(false);
				
				
				this.getMapLayer().addSprite(new BombSprite(this.getX(), this.getY(), BombType.NORAML, (this.getBulletType() == BulletType.HERO || sprite.getBulletType() == BulletType.HERO)));
				
				return true;
			}
			
		}
		
		
		return false;
	}
	/**
	 * 判断子弹是否与传入的坦克是否有碰撞
	 * list： 坦克列表
	 * isPlayAudio： 是否播放音效
	 * true： 有碰撞 false 没碰撞
	 */
	public boolean bulletHitTank(List< ? extends AbstractTankSprite> list , boolean isPlayAudio)
	{
		int count = list.size() - 1;
		for(int i = count;i>=0;i--)
		{
			AbstractTankSprite sprite = list.get(i);
			if(!sprite.isProtected() && hitRect(sprite.getRect()))
			{
				this.tankSprite.setShooting(false);
				this.setLive(false);
				this.getMapLayer().addSprite(new BombSprite(this.getX(), this.getY(), BombType.NORAML, isPlayAudio));
				
				sprite.hit();
				
				return true;
			}
		}
		
		return false;
	}

	public AbstractTankSprite getTankSprite()
	{
		return tankSprite;
	}

	public BulletType getBulletType()
	{
		return bulletType;
	}

	public DirectionType getDirectionType()
	{
		return directionType;
	}
	
}
