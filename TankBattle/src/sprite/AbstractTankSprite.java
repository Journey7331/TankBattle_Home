package sprite;

import enums.TankType;
import layer.MapLayer;
/**
 * 坦克基类
 */
public abstract class AbstractTankSprite extends AbstractMovableSprite
{

	private static final long serialVersionUID = -6776121224293973019L;
	/**
	 * 是否是受保护的状态
	 */
	private volatile boolean isProtected = true;
	/**
	 * 受保护的时间(400个重绘时间)
	 */
	private int protectedTime = 400;
	/**
	 * 是否是发射子弹中 
	 */
	private volatile boolean isShooting = false;
	/**
	 * 生命值
	 */
	private int life = 0;
	/**
	 * 吃的总的星
	 */
	private int starCount = 0;
	/**
	 * 是否能打铁
	 */
	private boolean canEatFe = false;
	
	/**
	 * 当前发射的子弹数(地图上显示的当前坦克发射的子弹数)
	 */
	private int bulletCount = 0;

	public AbstractTankSprite(MapLayer mapLayer)
	{
		super(mapLayer);
	}

	/**
	 * 坦克类型
	 */
	private TankType tankType;
	/**
	 * 开火
	 */
	public abstract void fire();
	public abstract void hit();

	public TankType getTankType()
	{
		return tankType;
	}

	public void setTankType(TankType tankType)
	{
		this.tankType = tankType;
	}

	public boolean isProtected()
	{
		return isProtected;
	}

	public void setProtected(boolean isProtected)
	{
		this.isProtected = isProtected;
	}

	public int getProtectedTime()
	{
		return protectedTime;
	}

	public void setProtectedTime(int protectedTime)
	{
		this.protectedTime = protectedTime;
	}

	public boolean isShooting()
	{
		return isShooting;
	}

	public void setShooting(boolean isShooting)
	{
		this.isShooting = isShooting;
	}

	public int getLife()
	{
		return life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}
	public int getStarCount()
	{
		return starCount;
	}
	public void setStarCount(int starCount)
	{
		this.starCount = starCount;
		if(starCount >=3)
		{
			this.setCanEatFe(true);
		}
		else
		{
			this.setCanEatFe(false);
		}
	}
	public boolean isCanEatFe()
	{
		return canEatFe;
	}
	public void setCanEatFe(boolean canEatFe)
	{
		this.canEatFe = canEatFe;
	}
	public int getBulletCount()
	{
		return bulletCount;
	}
	public void setBulletCount(int bulletCount)
	{
		this.bulletCount = bulletCount;
	}
	
	
}
