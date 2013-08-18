/**
 * SystemController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-3 上午10:37:10
 */
package com.wiselink.controllers;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author leo
 */
@Path("")
public class SystemController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Get("ping")
    public String ping() {
        LOGGER.info("ping");
        return successResult();
    }
}
