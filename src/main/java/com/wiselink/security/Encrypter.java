/**
 * Encrypter.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 6:31:50 PM
 */
package com.wiselink.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;

import com.wiselink.base.Constants;

/**
 * @author leo
 */
public class Encrypter {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";
    
    /**
     * MAC算法可选以下多种算法
     * 
     * <pre>
     * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";
    public static final String KEY_PBKDF2 = "PBKDF2WithHmacSHA1";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String KEY_SHA256 = "SHA-256";    
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final SecretKeySpec DEFAULT_AES_KEY_SPEC;
    static {
        DEFAULT_AES_KEY_SPEC = new SecretKeySpec(decryptBASE64(Constants.SECURITY_KEY), "AES");
    }


    /**
     * BASE64
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        try {
            return new String(Base64.encodeBase64(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // never happend
            return null;
        }
    }

    /**
     * MD5
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    /**
     * MD5
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[]... data) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        for (byte[] one: data) {
            if (ArrayUtils.isEmpty(one)) {
                continue;
            }

            md5.update(one);
        }
        return md5.digest();

    }

    /**
     * SHA
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }

    /**
     * 初始化HMAC密钥
     * 
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC-MD5
     * 
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(key, KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);

    }

    /**
     * HMAC-MD5
     * 
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);

    }

    /**
     * HMAC-SHA1
     * 
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] encryptHMACSha1(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        mac.update(data);
        return mac.doFinal();
    }

    /**
     * HMAC-SHA1
     * 
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] sha256Hash(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA256);
        sha.update(data);
        return sha.digest();
    }

    /**
     * 生成PBKDF2的key
     * 
     * @param data
     * @param salt
     * @param iterationCount
     * @param keyLength
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] createPBKDF2Key(char[] data, byte[] salt, int iterationCount, int keyLength)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeySpec spec = new javax.crypto.spec.PBEKeySpec(data, salt, iterationCount, keyLength);
        SecretKey sk = SecretKeyFactory.getInstance(KEY_PBKDF2).generateSecret(spec);
        return sk.getEncoded();
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws SecurityException
     */
    public static String decryptAES(String base64EncodeData) throws SecurityException {
        try {
            byte[] encryptedByte = decryptBASE64(base64EncodeData);
            byte[] decryptedByte = decryptAES(encryptedByte);
            return new String(decryptedByte, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(e);
        }
    }
    /**
     * @param data
     * @param key
     * @return
     * @throws SecurityException
     */
    public static byte[] decryptAES(byte[] cipherData) throws SecurityException {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, DEFAULT_AES_KEY_SPEC, iv);
            if (null == cipherData) {
                throw new IllegalBlockSizeException("no block data");
            }
            byte[] rawData = cipher.doFinal(cipherData);
            return rawData;
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (NoSuchPaddingException e) {
            throw new SecurityException(e);
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException(e);
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException(e);
        } catch (BadPaddingException e) {
            throw new SecurityException(e);
        }
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws SecurityException
     */
    public static String encryptAES(String data) throws SecurityException {
        try {
            byte[] rawData = data.getBytes(Constants.ENCODING);
            byte[] cipherData = encryptAES(rawData);
            return encryptBASE64(cipherData);
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(e);
        }
    }

    /**
     * 加密
     * 
     * @param data
     * @param key
     * @return
     * @throws SecurityException
     */
    public static byte[] encryptAES(byte[] rawData) throws SecurityException {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, DEFAULT_AES_KEY_SPEC, iv);
            return cipher.doFinal(rawData);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (NoSuchPaddingException e) {
            throw new SecurityException(e);
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException(e);
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException(e);
        } catch (BadPaddingException e) {
            throw new SecurityException(e);
        }
    }

    public static String md5(String msg) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(msg.getBytes());
        byte[] bKey = md5.digest();
        long l = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16)
                | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
        return String.valueOf(l);
    }
}
