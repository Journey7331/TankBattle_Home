package enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.RandomUtil;

/**
 * 精灵的方向
 */

public enum DirectionType
{
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
	public static DirectionType valueOf(int value)
	{
		if(value == 0)
		{
			return UP;
		}
		else if(value == 1)
		{
			return DOWN;
		}
		else if(value ==2)
		{
			return LEFT;
		}
		else if(value == 3)
		{
			return RIGHT;
		}
		
		throw new IllegalArgumentException("参数错误[ "+value+"]");
	}
	
	public static DirectionType randomType(int value)
	{
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(0,1,2,3));
		if(value >=0 && value <=3)
		{
			list.remove(value);
		}
		value = list.get(RandomUtil.nextInt(3));
		return valueOf(value);
	}
	public static DirectionType randomType()
	{
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(0,1,2,3));
		int value = list.get(RandomUtil.nextInt(4));
		return valueOf(value);
	}
}
