package com.github.bingoohuang.mallscanner.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;

public class Pbe {
    public static String encrypt(String property, String password) {
        byte[] bytes = property.getBytes(Charsets.UTF_8);
        byte[] finalBytes = doFinal(password, Cipher.ENCRYPT_MODE, bytes);

        return DatatypeConverter.printBase64Binary(finalBytes);
    }

    public static String decrypt(String property, String password) {
        byte[] bytes = DatatypeConverter.parseBase64Binary(property);
        byte[] finalBytes = doFinal(password, Cipher.DECRYPT_MODE, bytes);

        return new String(finalBytes, Charsets.UTF_8);
    }

    static final byte[] SALT = {
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };

    static final String ALGORITHM = "PBEWITHMD5andDES";

    public static byte[] doFinal(String password, int encryptMode, byte[] bytes) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher pbeCipher = Cipher.getInstance(ALGORITHM);
            pbeCipher.init(encryptMode, key, new PBEParameterSpec(SALT, 20));
            return pbeCipher.doFinal(bytes);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }


}
