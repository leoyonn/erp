/**
 * Utils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午6:24:27
 */
package com.wiselink.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author leo
 */
public class Utils {

    /**
     * split string to list.
     * @param source
     * @param splitter
     * @return
     */
    public static List<String> split(String source, String splitter) {
        if (StringUtils.isBlank(source)) {
            return Collections.emptyList();
        }
        return Arrays.asList(source.split(splitter));
    }

    /**
     * split string and parse integers
     * @param source
     * @param splitter
     * @return
     */
    public static  List<Integer> parseArrayFromStrings(String source, String splitter) {
        if (StringUtils.isBlank(source)) {
            return Collections.emptyList();
        }
        String[] arr = source.split(splitter);
        List<Integer> r = new ArrayList<Integer>();
        for (String f: arr) {
            r.add(Integer.valueOf(f));
        }
        return r;
    }

    ////////////////////////////////String utils///////////////////////////////////////////
    public static String upperFirst(String s) {
        if (StringUtils.isEmpty(s) || s.charAt(0) > 'z' || s.charAt(0) < 'a') {
            return s;
        }
        char[] chars = s.toCharArray();
        chars[0] += 'A' - 'a';
        return String.valueOf(chars);
    }

    /**
     * @param code
     * @param name
     * @param desc
     * @return
     */
    public static String buildEnumJson(int code, String name, String desc) {
        JSONObject j = new JSONObject();
        j.put("code", code);
        j.put("name", name);
        j.put("desc", desc);
        return j.toString();
    }
}
