package thread;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
/**
 * 播放音效的线程
 */
public class PlayAudioThread implements Runnable
{

	private String path ;
	
	public PlayAudioThread(String path)
	{
		this.path = path;
	}
	
	@Override
	public void run()
	{
		AudioInputStream audioInputStream = null;
		try
		{
			 audioInputStream =  AudioSystem.getAudioInputStream(new File(path));
		}  catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		AudioFormat audioFormat = audioInputStream.getFormat();
		
		SourceDataLine sourceDataLine = null;
		try
		{
			sourceDataLine  = AudioSystem.getSourceDataLine(audioFormat);
			sourceDataLine.open();
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
			return;
		}
		sourceDataLine.start();
		
		byte buf[] = new byte[512];
		int readLength = 0;
		try
		{
			while( (readLength =  audioInputStream.read(buf, 0, 512))!= -1)
			{
				sourceDataLine.write(buf, 0, readLength);
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			sourceDataLine.drain();
			sourceDataLine.close();
		}
		try
		{
			audioInputStream.close();
		} catch (Exception e)
		{}
		
	}
}
