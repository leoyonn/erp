/**
 * Config.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 上午10:54:34
 */
package com.wiselink.base;

import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.wiselink.utils.PropertyUtils;

/**
 * @author leo
 */
public final class Config {
    private static Configuration conf;
    private static final Properties prop;
    private static final String WEB_HOME;
    static {
        prop = PropertyUtils.getPropertiesFromResource(Config.class, "/erp.properties");
        WEB_HOME = prop.getProperty("web.home");
        System.out.println("got prop:" + prop);
    }
    
    /**
     * 从一个xml文件load配置信息
     * 
     * @param xmlConfigFileName
     * @throws ConfigurationException
     */
    public static void init(String xmlConfigFileName) throws ConfigurationException {
        init(new XMLConfiguration(xmlConfigFileName));
    }

    /**
     * 从一个Configuration对象中load配置信息
     * 
     * @param conf
     */
    public static void init(Configuration conf) {
        Config.conf = conf;
//        dataSourceName = conf.getString("service.dataSource");
//        tableSpaceName = conf.getString("service.tableSpace");
    }
    
    public static Configuration getConf() {
        return conf;
    }

    /**
     * 当前运行环境根目录
     * @return
     */
    public static final String appHome() {
        return WEB_HOME;
    }

    /**
     * 获取当前运行环境目录中文件#file的绝对路径
     * @param file
     * @return
     */
    public static String path(String file) {
        return WEB_HOME + "/" + file;
    }
}