package org.example;

import org.example.encryption.AESEncryption;
import org.example.encryption.EncryptionService;
import org.example.steganography.Content;
import org.example.steganography.LSBSteganography;
import org.example.steganography.SteganographyService;

import java.awt.image.BufferedImage;

public class App {
    private final EncryptionService encryptionService = new AESEncryption();
    private final SteganographyService steganographyService = new LSBSteganography();

    private String password;

    public App(String password) throws Exception {
        this.password = password;
    }

    public App() throws Exception {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String extractMessage(BufferedImage image) throws Exception {
        Content content = steganographyService.extractContent(image);
        String encryptedKey = content.getKey();
        encryptionService.setKeyFromPassword(encryptionService.getKeyFromPassword(password));
        String key = encryptionService.decrypt(encryptedKey);
        encryptionService.setKey(key);
        return encryptionService.decrypt(content.getMessage());
    }

    public BufferedImage embedMessage(String message, BufferedImage image) throws Exception {

        String encryptedMessage = encryptionService.encrypt(message);
        String key = encryptionService.getKey();
        encryptionService.setKeyFromPassword(encryptionService.getKeyFromPassword(password));
        String encryptedKey = encryptionService.encrypt(key);
        String messageToEmbed = encryptedKey + encryptedMessage;
        System.out.println(encryptedKey.length());

        return steganographyService.embedContent(messageToEmbed, image);
    }

    public BufferedImage appendMessage(String message, BufferedImage image) throws Exception {
        return embedMessage(extractMessage(image) + message, image);
    }
}
