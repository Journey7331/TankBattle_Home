package util;

import constant.Constant;
import enums.AudioType;
import thread.PlayAudioThread;
/**
 * ��Ч������
 */
public class AudioUtil
{

	/**
	 * ������Ч
	 * audioType�� ��Ч������
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
