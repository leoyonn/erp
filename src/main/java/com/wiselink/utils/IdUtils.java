/**
 * IdUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:25:24 PM
 */
package com.wiselink.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * TODO 公司、部门id
 * 
 * @author leo
 */
public class IdUtils {
    private static final int LEN_USER_ID = 12; 
    private static final int LEN_USER_ACCOUNT_MIN = 3; 
    private static final int LEN_USER_ACCOUNT_MAX = 30; 
    private static final Pattern USER_ID_REGEX = Pattern.compile("\\d{" + LEN_USER_ID + "}");
    private static final Pattern USER_ACCOUNT_REGEX = Pattern.compile(
            "^[a-zA-Z][\\da-zA-Z-_]{" + (LEN_USER_ACCOUNT_MIN - 1) + "," + (LEN_USER_ACCOUNT_MAX - 1) + "}");
    private static final Pattern CORP_ID_REGEX = Pattern.compile("\\d{" + 6 + "}0{4}");
    private static final Pattern DEPT_ID_REGEX = Pattern.compile("\\d{" + 8 + "}0{2}");

    /**
     * 是否一个合法的用户id：十位数字
     * @param userId
     * @return
     */
    public static boolean isUserIdLegal(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return false;
        }
        return USER_ID_REGEX.matcher(userId).matches();
    }

    /**
     * 是否一个合法的用户账号：
     * <ul>
     * <li>仅包含英文、中划线、下划线、数字
     * <li>第一位必须是英文字母
     * <li>最短3位，最长30位
     * </ul>
     * 
     * @param account
     * @return
     */
    public static boolean isUserAccountLegal(String account) {
        if (StringUtils.isEmpty(account)) {
            return false;
        }
        return USER_ACCOUNT_REGEX.matcher(account).matches();
    }

    /**
     * 是否一个合法的公司id：
     * <ul>
     * <li>仅包含数字
     * <li>共10位
     * <li>1-2位为省分有效位，3-4位为市分有效位，5-6为县支及以下有效位，7-10为0
     * </ul>
     * @return
     */
    public static boolean isCorpIdLegal(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        return CORP_ID_REGEX.matcher(id).matches();
    }

    /**
     * @return
     */
    public static boolean isDeptIdLegal(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        return DEPT_ID_REGEX.matcher(id).matches();
    }
}
