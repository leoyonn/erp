/**
 * HttpUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 6:11:14 PM
 */
package com.wiselink.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
            return serverName;
        }
    }

    /**
     * post to #url with parameters #params
     * make sure value of params are url-encoded.
     * 
     * @param url
     * @param params
     * @return
     */
    public static String httpPost(String url, Map<String, String> params) {
        if (params == null) {
            return httpPost(url, new String[0]);
        }
        String[] paramArr = new String[params.size()];
        int i = 0;
        for (Map.Entry<String, String> e : params.entrySet()) {
            String param = e.getKey() + "=" + e.getValue();
            paramArr[i] = param;
            i++;
        }
        return httpPost(url, paramArr);
    }

    /**
     * post to #url with parameters #params, each param is "xxx=yyy"
     * make sure value of params are url-encoded.
     * 
     * @param url
     * @param params
     * @return
     */
    public static String httpPost(String url, List<String> params) {
        return httpPost(url, params == null ? new String[0] : params.toArray(new String[params.size()]));
    }

    /**
     * post to #url with parameters #params, each param is "xxx=yyy"
     * make sure value of params are url-encoded.
     * 
     * @param url
     * @param params
     * @return
     */
    public static String httpPost(String url, String[] params) {
        HttpURLConnection con = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            // con.setInstanceFollowRedirects(true);
            // con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String paramStr = "";
            for (String param : params) {
                if (param != null && !"".equals(param.trim())) {
                    paramStr += "&" + param;
                }
            }
            byte[] b = paramStr.getBytes("UTF-8");
            con.getOutputStream().write(b, 0, b.length);
            con.getOutputStream().flush();
            con.getOutputStream().close();
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

}
