package org.example;

import org.example.steganography.LSBSteganography;
import org.example.steganography.SteganographyService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        SteganographyService steganographyService =  new LSBSteganography();

        // Load the image
        BufferedImage image = ImageIO.read(new File("/home/nims/Downloads/2023-06-27_20-40.png"));

        // Message to embed
        String messageToEmbed = "Hello";

        // Embed the message
        BufferedImage modifiedImage = steganographyService.embedMessage(messageToEmbed,image);

        // Save the modified image
        File outputFile = new File("/home/nims/Downloads/modified.png");
        ImageIO.write(modifiedImage, "png", outputFile);

        BufferedImage modImage = ImageIO.read(new File("/home/nims/Downloads/modified.png"));

        // Extract the message
        String extractedMessage = steganographyService.extractMessage(modImage);
        System.out.println("Extracted Message: " + extractedMessage);
    }
}