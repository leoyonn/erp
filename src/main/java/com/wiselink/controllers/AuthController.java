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
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;
import com.wiselink.base.AuthResult;
import com.wiselink.base.AuthStatus;
import com.wiselink.base.Constants;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.user.UserDeprecated;
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
    public String login(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("login param {}", param);
        JSONObject json = JSONObject.fromObject(param);
        String user = json.optString("user", "");
        String password = json.optString("password", "");
        if (StringUtils.isBlank(user) || StringUtils.isBlank(password)) {
            return failResult(ApiStatus.INVALID_PARAMETER, "用户名或密码为空");
        }
        // TODO: permission check
        AuthResult authResult = userService.checkPassword(user, password, inv.getRequest().getRemoteAddr());
        if (AuthStatus.SUCCESS != authResult.stat) {
            return apiResult(ApiResult.authFailed(authResult.stat));
        }
        setCookie(inv, authResult);
        UserDeprecated u = null;
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

    /**
     * 退出登录
     * @param inv
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("logout")
    public String logout(Invocation inv) {
        LOGGER.error("logout: {}", inv);
        CookieUtils.clearCookies(inv);
        return successResult("退出登录成功");
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

    @Get("check")
    public String check(Invocation inv) {
        String userId = CookieUtils.getUserId(inv);
        LOGGER.info("got user from cookie: {}", userId);
        UserDeprecated u = null;
        try {
            u = userService.getUserById(userId);
        } catch (ServiceException ex) {
            LOGGER.error("got user from cookie: " + userId, ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED, "读取用户数据失败");
        }
        if (u == null) {
            return failResult(ApiStatus.DATA_QUERY_FAILED, "读取用户数据失败");
        }
        return successResult(u);
    }
}
