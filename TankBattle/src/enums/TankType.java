package enums;

/**
 * 坦克的类型
 */
public enum TankType
{
	/**
	 * 一般的敌方坦克
	 */
	NORAML_ENEMY,
	/**
	 * 中级的敌方坦克
	 */
	MIDDLE_ENEMY,
	/**
	 * 高级的敌方坦克
	 */
	HEIGHT_ENEMY,
	/**
	 * 左边的玩家
	 */
	LEFT_HERO,
	/**
	 * 右边的玩家
	 */
	RIGHT_HERO;
	
	
	public static TankType valueOf(int value)
	{
		if(value ==0)
		{
			return NORAML_ENEMY;
		}
		else if(value == 1)
		{
			return MIDDLE_ENEMY;
		}
		else if(value ==2)
		{
			return HEIGHT_ENEMY;
		}
		else if(value == 3)
		{
			return LEFT_HERO;
		}
		else if(value ==4)
		{
			return RIGHT_HERO;
		}
		
		
		throw new IllegalArgumentException("参数错误["+value+"]");
	}
	
}
