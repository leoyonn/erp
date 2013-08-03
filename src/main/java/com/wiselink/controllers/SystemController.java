/**
 * SystemController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-3 上午10:37:10
 */
package com.wiselink.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;
import com.wiselink.base.AuthResult;
import com.wiselink.base.AuthStatus;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.user.User;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

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
        AuthResult authResult = userService.checkPassword(user, password, inv.getRequest().getRemoteAddr());
        if (AuthStatus.SUCCESS != authResult.stat) {
            return apiResult(ApiResult.authFailed(authResult.stat));
        }
        setCookie(inv, authResult);
        User u = null;
        try {
            u = userService.getUserById(authResult.userId);
        } catch (ServiceException e) {
            return failResult(ApiStatus.DATA_QUERY_FAILED, "读取用户数据失败");
        }
        if (u == null) {
            return failResult(ApiStatus.DATA_QUERY_FAILED, "读取用户数据失败");
        }
        return successResult(u.toJson());
    }

}
