/**
 * IdUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:25:24 PM
 */
package com.wiselink.utils;

import com.wiselink.security.Encrypter;

/**
 * @author leo
 */
public class IdUtils {
    public static long genUserId(String userAccount) {
        return Encrypter.md5(userAccount);
    }
}
