package org.example.encryption;

public interface EncryptionService {
    String encrypt(String message);
    String decrypt(String secretMessage);
}
