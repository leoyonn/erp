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

import com.wiselink.base.Constants;
import com.wiselink.result.Auth;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
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
            return failResult(ErrorCode.BlankParam, "用户名或密码为空");
        }
        // TODO: permission check
        OperResult<Auth> auth = userService.auth(user, password, inv.getRequest().getRemoteAddr());
        if (auth.error != ErrorCode.Success) {
            return apiResult(auth);
        }
        setAuthCookie(inv, auth.result);
        return apiResult(userService.getUserById(auth.result.id));
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

    /**
     * 检查登录情况
     * @param inv
     * @return
     */
    @Get("check")
    public String check(Invocation inv) {
        String userId = CookieUtils.getUserId(inv);
        LOGGER.info("got user from cookie: {}", userId);
        if (StringUtils.isBlank(userId)) {
            return failResult(ErrorCode.LoginRequired, "尚未登录");
        }
        return apiResult(userService.getUserById(userId));
    }

    private boolean setAuthCookie(Invocation inv, Auth auth) {
        int expire = Constants.COOKIE_EXPIRE_SECONDS_2WEEK;
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_USER_ID, auth.id, expire, "/",
                HttpUtils.getRootDomain(inv.getRequest()));
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_USER_ID, auth.id, expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_CORP_ID, auth.corpId, expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_DEPT_ID, auth.deptId, expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_EXPIRE_TIME, String.valueOf(expire), expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_PASS_TOKEN, auth.passToken, expire, "/", "");
        return true;
    }

}
