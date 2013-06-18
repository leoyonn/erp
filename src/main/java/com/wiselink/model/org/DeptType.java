/**
 * DeptType.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-18 下午10:55:02
 */
package com.wiselink.model.org;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 部门大类
 * 
 * @author leo
 */
public enum DeptType {
    Office("办公室"),
    Tech("技术部"),
    Finance("财务部"),
    Claim("理赔部"),
    Service("客服部");

    public final String cname;

    DeptType(String name) {
        this.cname = name;
    }

    public String typeName() {
        return cname;
    }

    public String toJson() {
        JSONObject j = new JSONObject();
        j.put("name", name());
        j.put("cname", cname);
        return j.toString();
    }

    @Override
    public String toString() {
        return toJson();
    }

    private static final String allJson;
    static {
        JSONArray j = new JSONArray();
        for (DeptType type: values()) {
            j.add(type.toJson());
        }
        allJson = j.toString();
    }

    public static String allAsJson() {
        return allJson;
    }
}
