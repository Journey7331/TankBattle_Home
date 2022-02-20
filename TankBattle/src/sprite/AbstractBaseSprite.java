package sprite;

import java.awt.Rectangle;
import java.io.Serializable;

import connector._Draw;
/**
 * ����Ļ���
 */
public abstract class AbstractBaseSprite implements _Draw,Serializable
{
	private static final long serialVersionUID = 2983317253530388193L;
	/**
	 * ������ʾ��λ��
	 */
	private int x,y,w,h;
	/**
	 * �����Ƿ�������
	 */
	private boolean isLive = true;

	private static short AUTO_INCREMENT = 0;
	/**
	 * id ÿ������Ψһ
	 */
	private short id = 0;
	
	
	public AbstractBaseSprite()
	{
		AUTO_INCREMENT++;
		this.id = AUTO_INCREMENT;
	}
	public static void resetAutoIncrement()
	{
		AUTO_INCREMENT = 0;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getW()
	{
		return w;
	}

	public void setW(int w)
	{
		this.w = w;
	}

	public int getH()
	{
		return h;
	}

	public void setH(int h)
	{
		this.h = h;
	}

	public boolean isLive()
	{
		return isLive;
	}

	public void setLive(boolean isLive)
	{
		this.isLive = isLive;
	}
	public Rectangle getRect()
	{
		return new Rectangle(x, y, w, h);
	}
	/**
	 * �ж��Ƿ�������ཻ
	 * rect�� Ҫ�жϵľ���
	 * true�� �ཻ false ���ཻ
	 */
	public boolean hitRect(Rectangle rect)
	{
		return getRect().intersects(rect);
	}

	public short getId()
	{
		return id;
	}

	public void setId(short id)
	{
		this.id = id;
	}
	
}
