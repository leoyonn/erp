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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author leo
 */
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private Configuration conf;
    private String appHome;
    private final static Config instance;
    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        instance = ((Config)ctx.getBean("config"));
        System.out.println(Config.class.getResource("/").getPath());
        LOGGER.info("got config: appHome:" + instance.appHome);
    }

    public static Config getInstance() {
        return instance;
    }
    
    public void setAppHome(String appHome) {
        this.appHome = appHome;
    }

    static {
//        appHome = System.getProperty("webapp.root") + "/WEB-INF/";
//        LOGGER.debug("{DEBUG} got WEB_HOME:" + WEB_HOME);
//        LOGGER.info("{INFO} got WEB_HOME:" + WEB_HOME);
//        LOGGER.warn("{WARN} got WEB_HOME:" + WEB_HOME);
//        LOGGER.error("{ERROR} got WEB_HOME:" + WEB_HOME);
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
}