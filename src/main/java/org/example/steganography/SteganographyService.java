package org.example.steganography;

import java.awt.image.BufferedImage;

public interface SteganographyService {
    BufferedImage embedMessage(String message,BufferedImage image);
    String extractMessage(BufferedImage image);
}
