package org.example.encryption;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EncryptionService {
    String encrypt(String message) throws Exception;

    String decrypt(String secretMessage) throws Exception;

    String getKey();
    SecretKey getKeyFromPassword(String password) throws Exception;

    void setKey(String encodedKey);
    void setKeyFromPassword(SecretKey key);
}
