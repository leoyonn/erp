/**
 * IdUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:25:24 PM
 */
package com.wiselink.utils;

import com.wiselink.security.Encrypter;

/**
 * TODO 公司、部门id
 * @author leo
 */
public class IdUtils {
    /**
     * 根据userAccount生成userId
     * @param userAccount
     * @return
     */
    public static String genUserId(String userAccount) {
        return Encrypter.md5(userAccount);
    }
}
