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
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiResult;
import com.wiselink.base.AuthResult;
import com.wiselink.base.Constants;
import com.wiselink.controllers.annotations.Trimmed;
import com.wiselink.service.SecurityService;
import com.wiselink.utils.AuthUtils;
import com.wiselink.utils.CookieUtils;
import com.wiselink.utils.HttpUtils;
import com.wiselink.utils.IdUtils;

/**
 * @author leo
 */
@Path("auth")
public class AuthController {
    @Autowired
    private SecurityService securityService;
    
    // TODO return ApiResult?
    @Post("login")
    public String login(Invocation inv, @Trimmed @Param("user") String user, @Param("pass") String password) {
        // 1. get user id from user
        long userId = IdUtils.genUserId(user);
        // 2. permission check
        // TODO
        // 3. check password
        AuthResult authResult = securityService.checkPassword(userId, password);
        if (AuthResult.SUCCESS != authResult) {
            return ApiResult.authFailed(authResult).toJson();
        }
        // 4. save cookie
        setCookie(inv, userId, password);
        return null;

    }

    private boolean setCookie(Invocation inv, long userId, String password) {
        int expire = Constants.COOKIE_EXPIRE_SECONDS_2WEEK;
        String sessionCode = AuthUtils.generateRandomAESKey();
        CookieUtils.saveCookie(inv.getResponse(), Constants.KEY_USER_ID, String.valueOf(userId), expire, "/",
                HttpUtils.getRootDomain(inv.getRequest()));
        CookieUtils.saveCookie(inv.getResponse(), Constants.KEY_USER_ID, String.valueOf(userId), expire, "/", "");
        CookieUtils.saveCookie(inv.getResponse(), Constants.KEY_EXPIRE_TIME, String.valueOf(expire), expire, "/", "");
        String userIp = inv.getRequest().getRemoteAddr();
        String passToken = AuthUtils.genPassToken(userId, password, sessionCode, userIp);
        CookieUtils.saveCookie(inv.getResponse(), Constants.KEY_PASS_TOKEN, passToken, expire, "/", "");
        return true;
    }

}
