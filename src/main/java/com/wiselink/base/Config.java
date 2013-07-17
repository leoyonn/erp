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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leo
 */
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private Configuration conf;
    private String appHome;
    private final static Config instance;
    static {
        System.out.println(Config.class.getResource("/"));
        // ApplicationContext ctx = new
        // ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        instance = new Config();
        instance.appHome = "G:/j4/erp/target/erp-0.0.1-SNAPSHOT/";
        // instance = ((Config)ctx.getBean("config"));
        // System.out.println(Config.class.getResource("/").getPath());
        LOGGER.info("got config: home:{}, appHome{}:", Config.class.getResource("/"), instance.appHome);
    }

    public static Config getInstance() {
        return instance;
    }
    
    public void setAppHome(String appHome) {
        this.appHome = appHome;
    }
    
    /**
     * 从一个xml文件load配置信息
     * 
     * @param xmlConfigFileName
     * @throws ConfigurationException
     */
    public void init(String xmlConfigFileName) throws ConfigurationException {
        init(new XMLConfiguration(xmlConfigFileName));
    }

    /**
     * 从一个Configuration对象中load配置信息
     * 
     * @param conf
     */
    public void init(Configuration conf) {
        this.conf = conf;
//        dataSourceName = conf.getString("service.dataSource");
//        tableSpaceName = conf.getString("service.tableSpace");
    }
    
    public Configuration getConf() {
        return conf;
    }

    /**
     * 当前运行环境根目录
     * @return
     */
    public final String appHome() {
        return appHome;
    }

    /**
     * 获取当前运行环境目录中文件#file的绝对路径
     * @param file
     * @return
     */
    public String path(String file) {
        return appHome + "" + file;
    }

    /**
     * 用户的头像地址
     * 
     * @param uid
     * @return
     */
    public String avatarUrl(String uid) {
        return "avatar/u." + uid + ".jpg";
    }
}