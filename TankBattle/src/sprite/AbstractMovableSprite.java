package sprite;

import enums.DirectionType;
import layer.MapLayer;
/**
 * ���ƶ������ľ��� 
 */

public abstract class AbstractMovableSprite extends AbstractBaseSprite
{
	private static final long serialVersionUID = -3563431327253865827L;
	/**
	 * �ٶ�
	 */
	private int speed;
	/**
	 * ����
	 */
	private DirectionType directionType;
	
	private MapLayer mapLayer;
	/**
	 * �ƶ�
	 */
	public abstract void move();
	
	
	public AbstractMovableSprite(MapLayer mapLayer)
	{
		this.mapLayer = mapLayer;
	}
	
	/**
	 * �Ƿ����ͼ�߿�����ײ
	 * x�� Ҫ����x
	 * y�� Ҫ����y
	 * directionType�� Ҫ���ķ���
	 * true�� ����ײ false û����ײ
	 */
	public boolean hitBorder(int x,int y,DirectionType directionType)
	{
		//�ϱ߿�
		if(directionType == DirectionType.UP)
		{
			if(y < MapLayer.top)
			{
				return true;
			}
		}
		//�±߿�
		else if(directionType == DirectionType.DOWN)
		{
			if( (y + getH()) > MapLayer.bottom)
			{
				return true;
			}
		}
		//��߿�
		else if(directionType == DirectionType.LEFT)
		{
			if(x < MapLayer.left)
			{
				return  true;
			}
		}
		//�ұ߿�
		else if(directionType == DirectionType.RIGHT)
		{
			if((x+getW()) > MapLayer.right)
			{
				return true;
			}
		}
		
		
		return false;
	}
	public int getSpeed()
	{
		return speed;
	}
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public DirectionType getDirectionType()
	{
		return directionType;
	}
	public void setDirectionType(DirectionType directionType)
	{
		this.directionType = directionType;
	}

	public MapLayer getMapLayer()
	{
		return mapLayer;
	}

	public void setMapLayer(MapLayer mapLayer)
	{
		this.mapLayer = mapLayer;
	}
	
}
