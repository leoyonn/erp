/**
 * HelloController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-3 下午10:24:49
 */
package com.wiselink.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiselink.base.Config;
import com.wiselink.controllers.annotations.LoginRequired;

/**
 * @author leo
 */
@Path("")
public class HelloController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Get("")
    public String index(Invocation inv) {
        LOGGER.info("welcome to index, appHome is: " + Config.getInstance().appHome());
        return successResult("welcome to picc home page!欢迎来到首页！");
    }

    @LoginRequired
    @Get("shouldlogin")
    public String shouldlogin() {
        LOGGER.info("{HelloController.index} debug in hello ");
        return successResult("Good! This api required login, and u got that!");
    }
}
