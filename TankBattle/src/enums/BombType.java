package enums;

public enum BombType
{
	NORAML,
	BIG;
	
	public static BombType valueOf(int value)
	{
		if(0==value)
		{
			return NORAML;
		}
		else if(1==value)
		{
			return BIG;
		}
		throw new IllegalArgumentException("²ÎÊý´íÎó["+value+"]");
	}
}
