package org.example.encryption;

public interface EncryptionService {
    String encrypt(String message) throws Exception;

    String decrypt(String secretMessage) throws Exception;

    String getKey();

    void setKey(String encodedKey);
}
