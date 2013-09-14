/**
 * BudgetProgress.java
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
public enum BudgetProgress {
    New             (0, "新增"),
    BeforeSubmit    (1, "等待报批"),
    DeclineSubmit   (2, "拒绝报批"),
    BeforeApprove   (3, "等待审批"),
    Approved        (4, "已审批"),
    DeclineApprove  (5, "拒绝审批"),
    Alloc           (6, "已分配"),
    Invalid         (-1, "无效");

    public final int code;
    public final String cname;

    BudgetProgress(int code, String name) {
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
    private static final Map<Integer, BudgetProgress> MAP;
    static {
        MAP = new HashMap<Integer, BudgetProgress>();
        JSONArray j = new JSONArray();
        for (BudgetProgress p: values()) {
            j.add(p.toJson());
            MAP.put(p.code, p);
        }
        ALL = j.toString();
        
    }

    public BudgetProgress fromCode(int code) {
        BudgetProgress r = MAP.get(code);
        return r == null ? Invalid : r;
    }

    public static String all() {
        return ALL;
    }
}
