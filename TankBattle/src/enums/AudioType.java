package enums;

public enum AudioType
{
	ADD_LIFE, BIG_BOMB, NORMAL_BOMB, EAT_BUFF, ENEMY_FIRE, HERO_FIRE, GMAE_OVER, PAUSE, SHOW_BUFF, GAME_START, HERO_DIE;

	public static AudioType valueOf(int value)
	{
		if (0 == value)
		{
			return ADD_LIFE;
		} else if (1 == value)
		{
			return BIG_BOMB;
		} else if (2 == value)
		{
			return NORMAL_BOMB;
		} else if (3 == value)
		{
			return EAT_BUFF;
		} else if (4 == value)
		{
			return ENEMY_FIRE;
		} else if (5 == value)
		{
			return HERO_FIRE;
		} else if (6 == value)
		{
			return GMAE_OVER;
		} else if (7 == value)
		{
			return PAUSE;
		} else if (8 == value)
		{
			return SHOW_BUFF;
		} else if (9 == value)
		{
			return GAME_START;
		} else if (10 == value)
		{
			return HERO_DIE;
		}
		throw new IllegalArgumentException("AudioType:非法参数[" + value + "]");
	};

	public static String getFileName(AudioType type)
	{
		if (ADD_LIFE == type)
		{
			return "add_life.wav";
		} else if (BIG_BOMB == type)
		{
			return "big_bomb.wav";
		} else if (NORMAL_BOMB == type)
		{
			return "normal_bomb.wav";
		} else if (EAT_BUFF == type)
		{
			return "eat_buff.wav";
		} else if (ENEMY_FIRE == type)
		{
			return "enemy_fire.wav";
		} else if (HERO_FIRE == type)
		{
			return "hero_fire.wav";
		} else if (GMAE_OVER == type)
		{
			return "game_over.wav";
		} else if (PAUSE == type)
		{
			return "pause.wav";
		} else if (SHOW_BUFF == type)
		{
			return "show_buff.wav";
		} else if (GAME_START == type)
		{
			return "game_start.wav";
		} else if (HERO_DIE == type)
		{
			return "hero_die.wav";
		}
		throw new IllegalArgumentException("AudioType:非法参数[" + type + "]");
	}

	public static String getFileName(byte type)
	{
		if (0 == type)
		{
			return "add_life.wav";
		} else if (1 == type)
		{
			return "big_bomb.wav";
		} else if (2 == type)
		{
			return "normal_bomb.wav";
		} else if (3 == type)
		{
			return "eat_buff.wav";
		} else if (4 == type)
		{
			return "enemy_fire.wav";
		} else if (5 == type)
		{
			return "hero_fire.wav";
		} else if (6 == type)
		{
			return "game_over.wav";
		} else if (7 == type)
		{
			return "pause.wav";
		} else if (8 == type)
		{
			return "show_buff.wav";
		} else if (9 == type)
		{
			return "game_start.wav";
		} else if (10 == type)
		{
			return "hero_die.wav";
		}

		throw new IllegalArgumentException("AudioType:非法参数[" + type + "]");
	}
}
