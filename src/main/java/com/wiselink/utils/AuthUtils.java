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

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.StringUtils;

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
    public static String genPassToken(String userId, String password, String sessionCode,
            String userIp) throws SecurityException {
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
     * @param token
     * @return
     * @throws SecurityException
     */
    public static JSONObject checkPassToken(String token) throws SecurityException {
        return decryptPassToken(token);
    }

    /**
     * @param token
     * @return
     * @throws SecurityException
     */
    public static JSONObject decryptPassToken(String token) throws SecurityException {
        if (StringUtils.isBlank(token)) {
            throw new SecurityException("no pass token");
        }
        try {
            return JSONObject.fromObject(Encrypter.decryptAES(token));
        } catch (JSONException je) {
            throw new SecurityException("invalid pass token");
        }
    }

    /**
     * @param token
     * @return
     * @throws SecurityException
     */
    public static String getUserIdFromPassToken(JSONObject token) throws SecurityException {
        try {
            String userId = token.getString("u");
            if (StringUtils.isEmpty(userId)) {
                throw new SecurityException("invalid pass token: no user id found.");
            }
            return userId;
        } catch (JSONException e) {
            throw new SecurityException(e);
        }
    }

    /**
     * validate user's id and password from token. 
     * @param passToken
     * @param userId
     * @param pwdMd5
     * @return
     * @throws SecurityException
     */
    public static JSONObject validateToken(String passToken, String userId, String pwdMd5) throws SecurityException {
        String decryptedData = Encrypter.decryptAES(passToken);
        JSONObject json = JSONObject.fromObject(decryptedData);
        if (!userId.equals(json.getString("u"))) {
            return null;
        }
        String pwdSign = json.getString("p");
        if (!sha1HMAC(pwdMd5).equals(pwdSign)) {
            return null;
        }
        return json;
    }

    /**
     * @param data
     * @return
     */
    private static String sha1HMAC(String data) throws SecurityException {
        try {
            return Encrypter.encryptBASE64(Encrypter.encryptHMAC(data.getBytes("UTF-8"),
                    Constants.SECURITY_KEY));
        } catch (Exception e) {
            // InvalidKeyException UnsupportedEncodingException NoSuchAlgorithmException
            throw new SecurityException(e);
        }
    }

}
