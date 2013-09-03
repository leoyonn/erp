/**
 * OrgType.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午12:54:10
 */
package com.wiselink.model.org;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 组织类型
 * @author leo
 */
public enum OrgType {
    Corp(0, "公司内部"),
    Supplier(1, "供货商"),
    Group(2, "集团"),
    Corp0(3, "总公司"),
    Corp1(4, "省分公司"),
    Corp2(5, "市分公司"),
    Corp3(6, "县支公司"),
    Dept(7, "部门"),
    Invalid(-1, "无效");

    public final String cname;
    public final int code;
    
    OrgType(int code, String name) {
        this.code = code;
        this.cname = name;
    }

    public String typeName() {
        return cname;
    }

    public String toJson() {
        JSONObject j = new JSONObject();
        j.put("code", code);
        j.put("name", name());
        j.put("cname", cname);
        return j.toString();
    }

    @Override
    public String toString() {
        return toJson();
    }

    /**
     * 获取所有的OrgType
     * @return
     */
    public static String allAsJson() {
        return allJson;
    }
    
    public static OrgType value(int code) {
        switch (code) {
            case 0: return Corp;
            case 1: return Supplier;
            case 2: return Group;
            case 3: return Corp0;
            case 4: return Corp1;
            case 5: return Corp2;
            case 6: return Corp3;
            case 7: return Dept;
            default: return Invalid;
        }
    }

    private static final String allJson;
    static {
        JSONArray j = new JSONArray();
        for (OrgType type: values()) {
            j.add(type.toJson());
        }
        allJson = j.toString();
    }
}
