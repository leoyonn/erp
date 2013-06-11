/**
 * CorpType.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午12:14:55
 */
package com.wiselink.model.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.wiselink.utils.Utils;

/**
 * 
 * @author leo
 */
public enum CorpType {
    CORP_L0(0, "总公司"),
    CORP_L1(1, "省公司"),
    CORP_L2(2, "市分公司"),
    CORP_L3(3, "县支分公司"),

    SUPPLIER(10, "供货商");

    public final  int code;
    public final String desc;
    public final String json;
    private static final String ALL;
    private static final Map<Integer, CorpType> codeMap;
    static {
        codeMap = new HashMap<Integer, CorpType>();
        JSONArray allJson = new JSONArray();
        for (CorpType v: values()) {
            allJson.add(v.json);
            codeMap.put(v.code, v);
        }
        ALL = allJson.toString();
    }

    CorpType(int code, String desc) {
        this.code = code;
        this.desc = desc;
        json = Utils.buildEnumJson(code, name(), desc);
    }

    public static String allAsJson() {
        return ALL;
    }

    public static CorpType fromCode(int code) {
        return codeMap.get(code);
    }
}