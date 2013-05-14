/**
 * HelloController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-3 下午10:24:49
 */
package com.wiselink.controllers;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.log4j.Logger;

/**
 * 
 * @author leo
 */
@Path("")
public class HelloController {
    protected static final Logger LOG = Logger.getLogger(HelloController.class);

    @Get("")
    public String index() {
        LOG.debug("{HelloController.index} debug");
        LOG.info("{HelloController.index} info");
        LOG.warn("{HelloController.index} warn");
        LOG.error("{HelloController.index} error");
        LOG.fatal("{HelloController.index} fatal");
        System.out.println("xxx1");
        System.err.println("xxx2");
        System.out.println(ClassLoader.getSystemResource("log4j.properties"));
        System.out.println(ClassLoader.getSystemResource("log4j.xml"));

        return "@This is a blank page. please find the api document...";
    }
}
