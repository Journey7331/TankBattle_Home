package sprite;

import enums.TankType;
import layer.MapLayer;
/**
 * ̹�˻���
 */
public abstract class AbstractTankSprite extends AbstractMovableSprite
{

	private static final long serialVersionUID = -6776121224293973019L;
	/**
	 * �Ƿ����ܱ�����״̬
	 */
	private volatile boolean isProtected = true;
	/**
	 * �ܱ�����ʱ��(400���ػ�ʱ��)
	 */
	private int protectedTime = 400;
	/**
	 * �Ƿ��Ƿ����ӵ��� 
	 */
	private volatile boolean isShooting = false;
	/**
	 * ����ֵ
	 */
	private int life = 0;
	/**
	 * �Ե��ܵ���
	 */
	private int starCount = 0;
	/**
	 * �Ƿ��ܴ���
	 */
	private boolean canEatFe = false;
	
	/**
	 * ��ǰ������ӵ���(��ͼ����ʾ�ĵ�ǰ̹�˷�����ӵ���)
	 */
	private int bulletCount = 0;

	public AbstractTankSprite(MapLayer mapLayer)
	{
		super(mapLayer);
	}

	/**
	 * ̹������
	 */
	private TankType tankType;
	/**
	 * ����
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
