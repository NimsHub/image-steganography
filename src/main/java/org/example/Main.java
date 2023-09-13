package org.example;

import org.example.encryption.AESEncryption;
import org.example.steganography.Content;
import org.example.steganography.LSBSteganography;
import org.example.steganography.SteganographyService;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import static org.example.utils.ImageUtil.loadImage;

public class Main {
    public static void main(String[] args) throws Exception {
        App app = new App("a");
//        // Message to embed
        String messageToEmbed = "Hello, welcome to the encryption world";
//
        String pngFile = "/run/media/nims/Files/Dev/IdeaProjects/image-steganography/src/main/resources/website.png";
        String embeddedImage = "/run/media/nims/Files/Dev/IdeaProjects/image-steganography/src/main/resources/modified.png";
//        // Load the image
//        BufferedImage originalImage = loadImage(jpegFile);

//        // Load the original image
        File inputFile = new File(pngFile);
        BufferedImage originalImage = ImageIO.read(inputFile);
//
//        // Perform modifications on the originalImage as needed
//
//        // Get the available ImageWriters for the original format
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = writers.next();
//
//        // Get the original image metadata
        ImageReader reader = ImageIO.getImageReader(writer);
        reader.setInput(ImageIO.createImageInputStream(inputFile));
        IIOMetadata metadata = reader.getImageMetadata(0);
//
//        // Create a FileImageOutputStream to write the image
        File outputFile = new File("/run/media/nims/Files/Dev/IdeaProjects/image-steganography/src/main/resources/modified.png");
        FileImageOutputStream output = new FileImageOutputStream(outputFile);

        // Embed the message
        BufferedImage modifiedImage = app.embedMessage(messageToEmbed, originalImage);

        writer.setOutput(output);
//
//        // Create an IIOImage with metadata preservation
        IIOImage imageWithMetadata = new IIOImage(modifiedImage, null, metadata);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//        // Write the image without compression
        writer.write(null, imageWithMetadata, param);
//
//        // Close the output stream
        output.close();


        // load the saved modified image
        BufferedImage modImage = loadImage(embeddedImage);

        // Extract the message
        String extractedMessage = app.extractMessage(modImage);
        System.out.println("Extracted Message: " + extractedMessage);

    }
}