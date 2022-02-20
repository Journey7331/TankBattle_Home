package util;

import java.util.Random;

public class RandomUtil
{
	private static Random random = new Random();
	
	
	// 3 [0,3) 0,1,2
	public static int nextInt(int value)
	{
		return random.nextInt(value);
	}
	// 0~1
	public static double nextDouble()
	{
		return random.nextDouble();
	}
}
