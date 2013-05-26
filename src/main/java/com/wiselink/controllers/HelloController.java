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

import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.LoginRequired;

/**
 * @author leo
 */
@Path("")
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Get("")
    public String index(Invocation inv) {
        LOGGER.debug("welcome to index");
        return "@json:" + new ApiResult(ApiStatus.SUCCESS, "welcome to picc home page.!!").toJson();
    }

    @LoginRequired
    @Get("shouldlogin")
    public String shouldlogin() {
        LOGGER.debug("{HelloController.index} debug in hello ");
        return "@Good! This api required login, and u got that!";
    }
}
