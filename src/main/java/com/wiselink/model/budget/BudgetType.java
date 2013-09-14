/**
 * BudgetType.java
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
public enum BudgetType {
    Annual  (0, "年度预算"),
    Inc     (1, "增量预算"),
    Invalid (-1, "无效");

    public final int code;
    public final String cname;

    BudgetType(int code, String name) {
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
    private static final Map<Integer, BudgetType> MAP;
    static {
        MAP = new HashMap<Integer, BudgetType>();
        JSONArray j = new JSONArray();
        for (BudgetType type: values()) {
            j.add(type.toJson());
            MAP.put(type.code, type);
        }
        ALL = j.toString();
    }

    public BudgetType fromCode(int code) {
        BudgetType r = MAP.get(code);
        return r == null ? Invalid : r;
    }

    public static String all() {
        return ALL;
    }
}
