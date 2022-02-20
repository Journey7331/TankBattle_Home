package util;

import constant.Constant;
import enums.AudioType;
import thread.PlayAudioThread;
/**
 * 音效工具类
 */
public class AudioUtil
{

	/**
	 * 播放音效
	 * audioType： 音效的类型
	 */
	public static void playAudio(AudioType audioType)
	{
		if(Constant.isPlayAudio)
		{
			new Thread(new PlayAudioThread("res/"+AudioType.getFileName(audioType))).start();
		}
		
	}
	public static void playAudio(byte type)
	{
		if(Constant.isPlayAudio)
		{
			new Thread(new PlayAudioThread("res/"+AudioType.getFileName(type))).start();
		}
	}
}
