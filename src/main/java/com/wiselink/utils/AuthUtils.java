/**
 * AuthUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 6:20:40 PM
 */
package com.wiselink.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.codec.DecoderException;

import com.wiselink.base.Constants;
import com.wiselink.security.Encrypter;

/**
 * @author leo
 */
public class AuthUtils {
    private static Random random = new SecureRandom();

    /**
     * @return
     * @throws DecoderException
     */
    public static String generateRandomAESKey() {
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        String aesKey = Encrypter.encryptBASE64(bytes);
        Arrays.fill(bytes, (byte) 0);
        return aesKey;
    }

    /**
     * @param userId
     * @param password
     * @param sessionCode
     * @param userIp
     * @return
     */
    public static String genPassToken(long userId, String password, String sessionCode, String userIp) {
        JSONObject json = new JSONObject();
        json.put("p", sha1HMAC(password));
        json.put("ip", userIp);
        json.put("u", String.valueOf(userId));
        json.put("s", sessionCode);
        json.put("t", System.currentTimeMillis() + "");
        json.put("v", "1.0");
        return Encrypter.encryptAES(json.toString());
    }

    /**
     * @param data
     * @return
     */
    private static String sha1HMAC(String data) {
        try {
            return Encrypter.encryptBASE64(Encrypter.encryptHMAC(data.getBytes("UTF-8"), Constants.SECURITY_KEY));
        } catch (Exception e) {
            // InvalidKeyException UnsupportedEncodingException NoSuchAlgorithmException
            throw new RuntimeException(e);
        }
    }

}
