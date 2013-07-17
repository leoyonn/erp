/**
 * Utils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午6:24:27
 */
package com.wiselink.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author leo
 */
public class Utils {

    /**
     * load a xml conf file from classpath:path
     * @param path
     * @return 
     */
    public static Document loadXmlDoc(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        SAXReader saxReader = new SAXReader();
        try {
            return saxReader.read(Utils.class.getResourceAsStream(path));
        } catch (DocumentException ex) {
            throw new RuntimeException(ex);
        }
    }

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

    /**
     * 工厂方法, 由对象 Class 创建一个 {@link ThreadLocal} 对象
     * 
     * @param clazz
     * @param initNum
     * @param maxNum
     * @return
     */
    public static <T> ThreadLocal<T> threadLocalFromClass(final Class<T> clazz) {
        return new ThreadLocal<T>(){
            @Override
            protected T initialValue() {
                try {
                    return clazz.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    /**
     * 从文件路径中获取后缀名
     * @param path
     * @return
     */
    public static String getFileExtFromPath(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }
        int idx = path.lastIndexOf('.');
        if (idx < 0 || idx == path.length() - 1) {
            return null;
        }
        String ext = path.substring(idx);
        if (ext.length() > 5 || ext.length() < 2 || ext.contains("/") || ext.contains("\\")) {
            return null;
        }
        return ext;
    }

    /**
     * features can be single-obj or obj-arrays, sum-up all counts.
     * 
     * @param features
     * @return
     */
    public static int fullSize(Object... features) {
        if (features == null) {
            return 0;
        }
        int size = 0;
        for (Object feature: features) {
            if (feature instanceof Object[]) {
                size += ((Object[]) feature).length;
            } else if (feature instanceof Collection) {
                size += ((Collection<?>) feature).size();
            } else {
                size++;
            }
        }
        return size;
    }
}
