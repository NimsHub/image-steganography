package org.example.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtil {
    public static String imageFormat;

    public static String getImageFormat(File imageFile) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            String formatName = reader.getFormatName();
            iis.close();
            return formatName.toLowerCase();
        }

        return null;
    }

    public static BufferedImage loadImage(String image) throws IOException {
        File imageFile = new File(image);
        imageFormat = getImageFormat(imageFile);
        return ImageIO.read(imageFile);
    }

    public static void saveImage(BufferedImage imgToSave, String location) throws IOException {
        File outputFile = new File(location + ".png");
        ImageIO.write(imgToSave, imageFormat, outputFile);
    }
}
