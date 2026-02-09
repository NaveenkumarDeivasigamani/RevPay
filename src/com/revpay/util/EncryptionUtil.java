package com.revpay.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for AES encryption and decryption.
 */
public class EncryptionUtil {
	
    private static final String SECRET_KEY = "RevPaySecretKey1";
    
    private static final String ALGORITHM = "AES";
    
    public static byte[] encrypt(String data) throws Exception {
    	
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());

    }
    
    
    public static String decrypt(byte[] encryptedData) throws Exception {
    	
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        
        return new String(decryptedBytes);
        
    }
}

