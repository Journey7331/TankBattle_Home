package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil
{
	public static Image loadImage(String name)
	{
		try
		{
			BufferedImage image = ImageIO.read(new File("res/"+name));
			return image;
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
