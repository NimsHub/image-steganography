package org.example.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

import static org.example.Constants.DATA_LENGTH;
import static org.example.Constants.KEY_SIZE;
public class AESEncryption implements EncryptionService {
    public AESEncryption() throws Exception {
        init();
    }

    private SecretKey key;
    private Cipher encryptionCipher;

    public void init() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_SIZE);
        key = keyGenerator.generateKey();
    }

    @Override
    public String encrypt(String message) throws Exception {

        byte[] dataInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return encode(encryptedBytes);

    }

    @Override
    public String decrypt(String secretMessage) throws Exception {

        byte[] dataInBytes = decode(secretMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
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
}
