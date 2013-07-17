/**
 * CookieUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 6:00:06 PM
 */
package com.wiselink.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;

import com.wiselink.base.Constants;

/**
 * @author leo
 */
public class CookieUtils {
    public static String getCookie(Invocation inv, String key) {
        return getCookie(inv.getRequest(), key);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(key)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    public static void saveCookie(HttpServletResponse response, String key, String value) {
        saveCookie(response, key, value, -1, "/");
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, String path) {
        saveCookie(response, key, value, -1, path);
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, int second, String path) {
        saveCookie(response, key, value, second, path, "." + Constants.DOMAIN);
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, int second, String path,
            String domain) {
        response.addCookie(createCookie(key, value, second, path, domain, false));
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, int second, String path,
            String domain, boolean secure) {
        response.addCookie(createCookie(key, value, second, path, domain, secure));
    }

    public static void clearCookie(HttpServletResponse response, String key, int second, String path) {
        clearCookie(response, key, second, path, "." + Constants.DOMAIN);
    }

    public static void clearCookie(HttpServletResponse response, String key, int second, String path, String domain) {
        response.addCookie(createCookie(key, null, second, path, domain, false));
    }

    public static void expireCookie(HttpServletResponse response, String key, String path, String domain) {
        response.addCookie(createCookie(key, "EXPIRED", 0, path, domain, false));
    }

    /**
     * 清空cookie
     * @param inv
     */
    public static void clearCookies(Invocation inv) {
        Cookie[] cookies = inv.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setValue(null);
                inv.getResponse().addCookie(cookie);
            }
        }
    }


    private static Cookie createCookie(String key, String value, int maxAge, String path, String domain, boolean secure) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        // according to rfc2109, this attribute is optional
        if (secure) {
            cookie.setSecure(true);
        }
        return cookie;
    }

    /**
     * @param inv
     * @return
     */
    public static String getUserId(Invocation inv) {
        return getCookie(inv, Constants.COOKIE_KEY_USER_ID);
    }
}
