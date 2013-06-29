/**
 * AuthController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:20:11
 */
package com.wiselink.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;
import com.wiselink.base.AuthResult;
import com.wiselink.base.AuthStatus;
import com.wiselink.base.Constants;
import com.wiselink.controllers.annotations.Trimmed;
import com.wiselink.exception.ServiceException;
import com.wiselink.service.UserService;
import com.wiselink.utils.CookieUtils;
import com.wiselink.utils.HttpUtils;

/**
 * @author leo
 */
@Path("auth")
public class AuthController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    
    @SuppressWarnings("@Post")
    @Get("login")
    public String login(Invocation inv, @Trimmed @Param("user") String user, @Param("password") String password) {
        LOGGER.error("login: {}:{}", user, password);
        // TODO: permission check
        AuthResult authResult = userService.checkPassword(user, password, inv.getRequest().getRemoteAddr());
        if (AuthStatus.SUCCESS != authResult.stat) {
            return apiResult(ApiResult.authFailed(authResult.stat));
        }
        setCookie(inv, authResult);
        try {
            return successResult(userService.getUser(user).toJson());
        } catch (ServiceException e) {
            return failResult(ApiStatus.DATA_QUERY_FAILED, "读取用户数据失败");
        }
    }

    private boolean setCookie(Invocation inv, AuthResult auth) {
        int expire = Constants.COOKIE_EXPIRE_SECONDS_2WEEK;
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_USER_ID, auth.userId, expire, "/",
                HttpUtils.getRootDomain(inv.getRequest()));
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_USER_ID, auth.userId, expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_EXPIRE_TIME, String.valueOf(expire), expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_PASS_TOKEN, auth.passToken, expire, "/", "");
        return true;
    }
}
