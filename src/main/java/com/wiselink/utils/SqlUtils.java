/**
 * SqlUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-25 下午4:30:08
 */
package com.wiselink.utils;

import java.util.Collection;


/**
 * @author leo
 */
public class SqlUtils {
    /**
     * 将keys中的数组拼成用于SQL的key字符串
     * @param keys
     * @return
     */
    public static String keys(Collection<String> keys) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String key: keys) {
            sb.append("\"").append(key).append("\",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    /**
     * 将keys中的数组拼成用于SQL的value字符串
     * @param keys
     * @return
     */
    public static String values(Collection<String> keys) {
        StringBuilder sb = new StringBuilder();
        sb.append("VALUES(");
        for (String key: keys) {
            sb.append(":").append(key).append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    /**
     * 将keys中的数组拼成用于SQL的update:key-values字符串
     * @param keys
     * @return
     */
    public static String kvs(Collection<String> keys) {
        StringBuilder sb = new StringBuilder();
        for (String key: keys) {
            sb.append("\"").append(key).append("\"=").append(":").append(key).append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }
}
