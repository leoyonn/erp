/**
 * PropertyUtils.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-19 上午12:56:02
 */
package com.wiselink.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leo
 */
public class PropertyUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

    /**
     * Load Properties from resource specified by {@link pathInResource}, and
     * put all of them to system properties.
     * 
     * @param clazz
     *            class to load resource.
     * @param pathInResource
     *            path of the property file in resource. see
     *            {@link Class#getResourceAsStream(String)}
     * @return true if load successfully. otherwise false.
     */
    public static boolean loadPropertiesFromResource(Class<?> clazz, String pathInResource) {
        return getPropertiesFromStream(clazz.getResourceAsStream(pathInResource), true) != null;
    }

    /**
     * Get Properties from resource specified by {@link pathInResource}.
     * 
     * @param clazz
     *            class to load resource.
     * @param pathInResource
     *            path of the property file in resource. see
     *            {@link Class#getResourceAsStream(String)}
     * @return the properties instance or null if load failed.
     */
    public static Properties getPropertiesFromResource(Class<?> clazz, String pathInResource) {
        return getPropertiesFromStream(clazz.getResourceAsStream(pathInResource), false);
    }

    public static Properties getPropertiesFromFile(String filePath) {
        try {
            return getPropertiesFromStream(new FileInputStream(filePath), false);
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not open file: " + filePath, e);
            return null;
        }
    }

    /**
     * Load Properties from file specified by {@link filePath}, and put all of
     * them to system properties.
     * 
     * @param filePath
     *            path of the property file.
     * @return true if load successfully. otherwise false.
     */
    public static boolean loadPropertiesFromFile(String filePath) {
        try {
            return getPropertiesFromStream(new FileInputStream(filePath), true) != null;
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not open file: " + filePath, e);
            return false;
        }
    }

    /**
     * Load properties from stream, and return the {@link Properties} instance. <br>
     * The input stream will be closed by this method.
     * 
     * @param stream
     * @param putToSystemProperties
     * @return the Properties instance, or null if failed.
     */
    private static Properties getPropertiesFromStream(InputStream stream, boolean putToSystemProperties) {
        try {
            Properties properties = new Properties();
            properties.load(stream);
            if (putToSystemProperties) {
                System.getProperties().putAll(properties);
            }
            return properties;
        } catch (IOException e) {
            LOGGER.error("Could not open properties.", e);
            return null;
        } finally {
            if (stream != null) {
                try {
                    
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getPropertyInt(Properties prop, String key, int defaultValue) {
        String value = prop.getProperty(key);
        return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value.trim());
    }

    public static int getPropertyInt(String key, int defaultValue) {
        return getPropertyInt(System.getProperties(), key, defaultValue);
    }

    public static long getPropertyLong(Properties prop, String key, long defaultValue) {
        String value = prop.getProperty(key);
        return StringUtils.isEmpty(value) ? defaultValue : Long.parseLong(value.trim());
    }

    public static long getPropertyLong(String key, long defaultValue) {
        return getPropertyLong(System.getProperties(), key, defaultValue);
    }

    public static boolean getPropertyBoolean(Properties prop, String key, boolean defaultValue) {
        String value = prop.getProperty(key);
        return StringUtils.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value.trim());
    }

    public static boolean getPropertyBoolean(String key, boolean defaultValue) {
        return getPropertyBoolean(System.getProperties(), key, defaultValue);
    }

    public static String dumpProperties(Properties p, boolean sort) {
        return dumpProperties(p, "[[\n    ", "\n    ", "]]\n", sort);
    }

    public static String dumpProperties(Properties p, String head, String separator, String tail, boolean sort) {
        Collection<Entry<Object, Object>> entries = p.entrySet();
        if (sort) {
            ArrayList<Entry<Object, Object>> list = new ArrayList<Entry<Object, Object>>(p.entrySet());
            Collections.sort(list, new Comparator<Entry<Object, Object>>() {
                @Override
                public int compare(Entry<Object, Object> o1, Entry<Object, Object> o2) {
                    return o1.getKey().toString().compareTo(o2.getKey().toString());
                }
            });
            entries = list;
        }
        return head + StringUtils.join(entries, separator) + tail;
    }
}
