/**
 * BudgetStatus.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午7:25:28
 */
package com.wiselink.model.budget;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

/**
 * @author leo
 */
public enum BudgetStatus {
    Normal  (0, "正常"),
    End     (1, "完成"),
    Abord   (2, "中止"),
    Invalid (-1, "无效");

    public final int code;
    public final String cname;

    BudgetStatus(int code, String name) {
        this.code = code;
        this.cname = name;
    }

    public String toJson() {
        return "{\"code\":" + code + ",\"name\":\"" + cname + "\"}";
    }

    @Override
    public String toString() {
        return toJson();
    }

    private static final String ALL;
    private static final Map<Integer, BudgetStatus> MAP;
    static {
        MAP = new HashMap<Integer, BudgetStatus>();
        JSONArray j = new JSONArray();
        for (BudgetStatus s: values()) {
            j.add(s.toJson());
            MAP.put(s.code, s);
        }
        ALL = j.toString();
    }

    public BudgetStatus fromCode(int code) {
        BudgetStatus r = MAP.get(code);
        return r == null ? Invalid : r;
    }

    public static String all() {
        return ALL;
    }
}
