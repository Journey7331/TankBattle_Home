package enums;

public enum GameModel
{
	PLAYER,
	PLAYERS,
	INTERNET;
	
	
	public static GameModel valueOf(int value)
	{
		if(value == 0)
		{
			return PLAYER;
		}
		else if(value ==1)
		{
			return PLAYERS;
		}
		else if(value ==2)
		{
			return INTERNET;
		}
		
		throw new IllegalArgumentException("²ÎÊý´íÎó["+value+"]");
	}
}
