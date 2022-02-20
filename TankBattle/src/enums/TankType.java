package enums;

/**
 * ̹�˵�����
 */
public enum TankType
{
	/**
	 * һ��ĵз�̹��
	 */
	NORAML_ENEMY,
	/**
	 * �м��ĵз�̹��
	 */
	MIDDLE_ENEMY,
	/**
	 * �߼��ĵз�̹��
	 */
	HEIGHT_ENEMY,
	/**
	 * ��ߵ����
	 */
	LEFT_HERO,
	/**
	 * �ұߵ����
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
		
		
		throw new IllegalArgumentException("��������["+value+"]");
	}
	
}
