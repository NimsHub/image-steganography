package org.example;

import org.example.encryption.AESEncryption;
import org.example.steganography.LSBSteganography;
import org.example.steganography.SteganographyService;

import java.awt.image.BufferedImage;

import static org.example.utils.ImageUtil.loadImage;
import static org.example.utils.ImageUtil.saveImage;

public class Main {
    public static void main(String[] args) throws Exception {

        AESEncryption aesEncryption = new AESEncryption();

        SteganographyService steganographyService = new LSBSteganography(aesEncryption);

        // Message to embed
        String messageToEmbed = "Hello, welcome to the encryption world";

        // Load the image
        BufferedImage image = loadImage("path_to_image");

        // Embed the message
        BufferedImage modifiedImage = steganographyService.embedMessage(messageToEmbed, image);

        // Save the modified image
        saveImage(modifiedImage,"path_to_desired_location");

        // load the saved modified image
        BufferedImage modImage = loadImage("path_to_image");

        // Extract the message
        String extractedMessage = steganographyService.extractMessage(modImage);
        System.out.println("Extracted Message: " + extractedMessage);

    }
}