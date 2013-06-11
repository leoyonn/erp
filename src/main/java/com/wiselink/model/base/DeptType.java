/**
 * DeptType.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午1:52:26
 */
package com.wiselink.model.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.wiselink.utils.Utils;

/**
 * 部门类型
 * @author leo
 */
public enum DeptType {
    OFFICE(0, "办公室"),
    TECH(1, "技术部"),
    FI(2, "财务部"),
    CLAIM(3, "理赔部"),
    SERVICE(4, "客服部");

    public final  int code;
    public final String desc;
    public final String json;
    private static final String ALL;
    private static final Map<Integer, DeptType> codeMap;
    static {
        codeMap = new HashMap<Integer, DeptType>();
        JSONArray allJson = new JSONArray();
        for (DeptType v: values()) {
            allJson.add(v.json);
            codeMap.put(v.code, v);
        }
        ALL = allJson.toString();
    }

    DeptType(int code, String desc) {
        this.code = code;
        this.desc = desc;
        json = Utils.buildEnumJson(code, name(), desc);
    }

    public static String allAsJson() {
        return ALL;
    }

    public static DeptType fromCode(int code) {
        return codeMap.get(code);
    }
}
