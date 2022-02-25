package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ͼƬ���ع�����
 */
public class ImageUtil {
    public static Image loadImage(String name) {
        try {
            // java.awt.image.BufferedImage
            return ImageIO.read(new File("res/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
