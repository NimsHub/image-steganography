package org.example.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

import static org.example.Constants.KEY_SIZE;
import static org.example.Constants.SALT;

public class AESEncryption implements EncryptionService {
    public AESEncryption() throws Exception {
        init();
    }

    private SecretKey key;

    public void init() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_SIZE);
        key = keyGenerator.generateKey();
    }

    @Override
    public String encrypt(String message) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, generateIv());
        byte[] cipherText = cipher.doFinal(message.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    @Override
    public String decrypt(String secretMessage) throws Exception {

        byte[] dataInBytes = decode(secretMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, generateIv());
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        return new IvParameterSpec(iv);
    }
    public SecretKey getKeyFromPassword(String password) throws Exception {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String getKey() {
        byte[] keyBytes = key.getEncoded();
        return encode(keyBytes);
    }
    public void setKey(String encodedKey) {
        byte[] decodedKeyBytes = decode(encodedKey);
        this.key = new javax.crypto.spec.SecretKeySpec(decodedKeyBytes, "AES");
    }
    public void setKeyFromPassword(SecretKey key){
        this.key = key;
    }
}
