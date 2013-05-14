/**
 * Utils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午6:24:27
 */
package com.wiselink.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author leo
 */
public class Utils {
    ////////////////////////////////String utils///////////////////////////////////////////
    public static String upperFirst(String s) {
        if (StringUtils.isEmpty(s) || s.charAt(0) > 'z' || s.charAt(0) < 'a') {
            return s;
        }
        char[] chars = s.toCharArray();
        chars[0] += 'A' - 'a';
        return String.valueOf(chars);
    }
}
