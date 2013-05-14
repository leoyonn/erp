/**
 * HttpUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 6:11:14 PM
 */
package com.wiselink.utils;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

/**
 * @author leo
 */
public class HttpUtils {
    private final static Pattern PAT_DOMAIN = Pattern.compile("([^.]*)[.]([^.]*)$");

    public static String getRootDomain(ServletRequest request) {
        String serverName = request.getServerName();
        Matcher matcher = PAT_DOMAIN.matcher(serverName);
        if (matcher.find()) {
            String domain = String.format(".%s.%s", matcher.group(1), matcher.group(2));
            return domain;
        } else {
            throw new InvalidParameterException("invalid server name:" + serverName);
        }
    }

}
