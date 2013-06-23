/**
 * LoginRequiredChecker.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-26 下午1:06:44
 */
package com.wiselink.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.Constants;
import com.wiselink.dao.UserInfoDAO;
import com.wiselink.utils.AuthUtils;
import com.wiselink.utils.CookieUtils;

/**
 * handle login required checking.
 * @author leo
 */
public class LoginRequiredChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequiredInterceptor.class);

    @Autowired
    private UserInfoDAO userDao;

    public static class LoginRequiredCheckResult {
        public boolean success = false;
        public String ssecurity;
        public String uuid;
    }

    /**
     * check if request is with login.
     * @param request
     * @param response
     * @param isBrowser
     * @return
     */
    public LoginRequiredCheckResult doCheck(HttpServletRequest request, HttpServletResponse response, boolean isBrowser) {
        LOGGER.debug("enter doCheck,isBrowser:{}", isBrowser);
        LoginRequiredCheckResult result = new LoginRequiredCheckResult();
        result.success = false;

        // check token from cookie
        String token = CookieUtils.getCookie(request, Constants.COOKIE_KEY_PASS_TOKEN);
        JSONObject tokenJson = checkPassToken(token);
        if (token == null || tokenJson == null) {
            return result;
        }

        // check user id from cookie and token 
        String userIdFromCookie = CookieUtils.getCookie(request, "userId");
        String userIdFromToken = tokenJson.optString("u", "");
        if (StringUtils.isBlank(userIdFromCookie) || !userIdFromCookie.equals(userIdFromToken)) {
            LOGGER.error("invalid userId, in-cookie {}, in-serviceToken {}", userIdFromCookie, userIdFromToken);
            return result;
        }
        // check token and user id from request parameters
        if (isBrowser) {
            if (request.getMethod().toUpperCase().equals("POST")) {
                String tokenFromReq = request.getParameter(Constants.COOKIE_KEY_PASS_TOKEN);
                if (StringUtils.isEmpty(tokenFromReq) || !tokenFromReq.equals(token)) {
                    LOGGER.error("invalid service token, in-paramter {}, in-cookie {}", tokenFromReq, token);
                    return result;
                }
            }
            String userIdFromParam = request.getParameter("userId");
            if (StringUtils.isBlank(userIdFromParam) || !userIdFromParam.equals(userIdFromCookie)) {
                return result;
            }
        }
        // got check result
        if (result.success) {
            result.uuid = userIdFromCookie;
            result.ssecurity = tokenJson.getString("s");
        }
        LOGGER.debug("check login required success? {}", result.success);
        return result;
    }

    /**
     * check if pass token is valid.
     * @param token
     * @return
     */
    public JSONObject checkPassToken(String token) {
        try {
            JSONObject tokenJson = AuthUtils.checkPassToken(token);
            String userId = AuthUtils.getUserIdFromPassToken(tokenJson);
            String realPass = userDao.getPassword(userId);
            return AuthUtils.validateToken(token, userId, realPass);
        } catch (Exception ex) {
            // SQLException, SecurityException, JSONException
            LOGGER.error("check pass token got exeption!", ex);
            return null;
        }
    }
}
