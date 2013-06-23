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
import com.wiselink.base.Constants;
import com.wiselink.controllers.annotations.Trimmed;
import com.wiselink.exception.ServiceException;
import com.wiselink.service.UserService;
import com.wiselink.utils.AuthUtils;
import com.wiselink.utils.CookieUtils;
import com.wiselink.utils.HttpUtils;
import com.wiselink.utils.IdUtils;

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
        // 1. get user id from user
        String userId = IdUtils.genUserId(user);
        // 2. permission check
        // TODO
        // 3. check password
        AuthResult authResult = userService.checkPassword(userId, password);
        if (AuthResult.SUCCESS != authResult) {
            return apiResult(ApiResult.authFailed(authResult));
        }
        // 4. save cookie
        setCookie(inv, userId, password);
        try {
            return successResult(userService.getUser(userId).toJson());
        } catch (ServiceException e) {
            return failResult(ApiStatus.DATA_QUERY_FAILED, "读取用户数据失败");
        }
    }

    private boolean setCookie(Invocation inv, String userId, String password) {
        int expire = Constants.COOKIE_EXPIRE_SECONDS_2WEEK;
        String sessionCode = AuthUtils.generateRandomAESKey();
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_USER_ID, userId, expire, "/",
                HttpUtils.getRootDomain(inv.getRequest()));
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_USER_ID, userId, expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_EXPIRE_TIME, String.valueOf(expire), expire, "/", "");
        String userIp = inv.getRequest().getRemoteAddr();
        String passToken = AuthUtils.genPassToken(userId, password, sessionCode, userIp);
        CookieUtils.saveCookie(inv.getResponse(), Constants.COOKIE_KEY_PASS_TOKEN, passToken, expire, "/", "");
        return true;
    }

}
