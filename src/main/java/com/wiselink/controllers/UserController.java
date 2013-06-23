/**
 * UserController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午5:36:55
 */
package com.wiselink.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import com.wiselink.controllers.annotations.Trimmed;

/**
 * 
 * @author leo
 */
@Path("user")
public class UserController extends BaseController {
    // private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    /**
     * add an user into database.
     * @param inv
     * @param user
     * @param password
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newUser(Invocation inv, @Trimmed @Param("user") String user, @Param("password") String password) {
        return "@json:" + "";
    }

}
