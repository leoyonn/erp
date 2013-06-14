/**
 * Config.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 上午10:54:34
 */
package com.wiselink.base;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * @author leo
 */
public final class Config {
    private static final String APP_HOME = System.getProperty("user.dir");
    private static Configuration conf;
    
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
        return APP_HOME;
    }

    /**
     * 获取当前运行环境目录中文件#file的绝对路径
     * @param file
     * @return
     */
    public static String path(String file) {
        return APP_HOME + "/" + file;
    }
}