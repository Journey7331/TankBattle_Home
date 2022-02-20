package enums;

public enum BuffType
{
	ADD_LIFE,
	STOP_ENEMY,
	EAT_FE,
	KILL_ENEMY,
	EAT_STAR,
	ADD_PROTECTED;
	
	public static BuffType valueOf(int value)
	{
		if(value == 0)
		{
			return ADD_LIFE;
		}
		else if(value ==1)
		{
			return STOP_ENEMY;
		}
		else if(value ==2)
		{
			return EAT_FE;
		}
		else if(value ==3)
		{
			return KILL_ENEMY;
		}
		else if(value ==4)
		{
			return EAT_STAR;
		}
		else if(value ==5)
		{
			return BuffType.ADD_PROTECTED;
		}
		
		throw new IllegalArgumentException("²ÎÊý´íÎó["+value+"]");
	}
}
