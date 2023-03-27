package com.springboot.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * 密码加密工具类，使用AES算法进行 加密 和 解密
 */
public class PasswordEncryptionUtil {

    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding"; // AES算法，ECB模式，PKCS5填充
    private static final String DEFAULT_KEY = "YourSecretKey123"; // 默认密钥

    /**
     * 对密码进行加密
     *
     * @param password 明文密码
     * @param key      密钥，可以为空，为空则使用默认密钥
     * @return 加密后的密文密码
     */
    public static String encryptPassword(String password, String key) {
        if (key == null || key.trim().isEmpty()) {
            key = DEFAULT_KEY;
        }
        // 将密钥转换为字节数组，并创建一个 AES 密钥
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        byte[] encrypted = new byte[0];

        try {
            // 创建一个 Cipher 实例并指定算法、模式和填充方式
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            // 对明文密码进行加密
            encrypted = cipher.doFinal(password.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将加密后的字节数组转换为 Base64 编码的字符串
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 对密码进行解密
     *
     * @param encryptedPassword 密文密码
     * @param key               密钥，可以为空，为空则使用默认密钥
     * @return 解密后的明文密码
     */
    public static String decryptPassword(String encryptedPassword, String key) {
        if (key == null || key.trim().isEmpty()) {
            key = DEFAULT_KEY;
        }
        // 将密钥转换为字节数组，并创建一个 AES 密钥
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        byte[] decrypted = new byte[0];

        try {
            // 创建一个 Cipher 实例并指定算法、模式和填充方式
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            // 将 Base64 编码的字符串转换为加密后的字节数组
            byte[] encrypted = Base64.getDecoder().decode(encryptedPassword);
            // 对密文密码进行解密
            decrypted = cipher.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将解密后的字节数组转换为字符串
        return new String(decrypted);
    }

}
