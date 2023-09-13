package org.example.steganography;

import java.awt.image.BufferedImage;

public interface SteganographyService {
    BufferedImage embedContent(String message, BufferedImage image) throws Exception;
    Content extractContent(BufferedImage image) throws Exception;
//    BufferedImage append(String message,BufferedImage image) throws Exception;
}
