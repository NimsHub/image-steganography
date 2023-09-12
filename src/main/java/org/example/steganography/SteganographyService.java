package org.example.steganography;

import java.awt.image.BufferedImage;

public interface SteganographyService {
    BufferedImage embedMessage(String message,BufferedImage image) throws Exception;
    String extractMessage(BufferedImage image) throws Exception;
    BufferedImage append(String message,BufferedImage image) throws Exception;
}
