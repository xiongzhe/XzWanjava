package com.xiongz.wanjava.common.utils;


import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author winkey
 * @date 2020/06/05
 * @describe 密码等加密解密
 */
public class ADESUtil {
    /**
     * AES/CBC/PKCS5Padding 加密
     */
    public static String encryptAES(String content, String key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            //IvParameterSpec iv = new IvParameterSpec(key.substring(16).getBytes(StandardCharsets.UTF_8));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes());
            return Base64.encodeToString(encrypted,Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * AES/CBC/PKCS5Padding 解密
     */
    public static String decryptAES(String content, String key) {
        try {
            byte[] byteMi = Base64.decode(content,Base64.NO_WRAP);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // IvParameterSpec iv = new IvParameterSpec(key.substring(16).getBytes(StandardCharsets.UTF_8));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
