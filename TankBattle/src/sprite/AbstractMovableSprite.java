package sprite;

import enums.DirectionType;
import layer.MapLayer;
/**
 * 有移动能力的精灵 
 */

public abstract class AbstractMovableSprite extends AbstractBaseSprite
{
	private static final long serialVersionUID = -3563431327253865827L;
	/**
	 * 速度
	 */
	private int speed;
	/**
	 * 方向
	 */
	private DirectionType directionType;
	
	private MapLayer mapLayer;
	/**
	 * 移动
	 */
	public abstract void move();
	
	
	public AbstractMovableSprite(MapLayer mapLayer)
	{
		this.mapLayer = mapLayer;
	}
	
	/**
	 * 是否与地图边框有碰撞
	 * x： 要检测的x
	 * y： 要检测的y
	 * directionType： 要检测的方向
	 * true： 有碰撞 false 没有碰撞
	 */
	public boolean hitBorder(int x,int y,DirectionType directionType)
	{
		//上边框
		if(directionType == DirectionType.UP)
		{
			if(y < MapLayer.top)
			{
				return true;
			}
		}
		//下边框
		else if(directionType == DirectionType.DOWN)
		{
			if( (y + getH()) > MapLayer.bottom)
			{
				return true;
			}
		}
		//左边框
		else if(directionType == DirectionType.LEFT)
		{
			if(x < MapLayer.left)
			{
				return  true;
			}
		}
		//右边框
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
