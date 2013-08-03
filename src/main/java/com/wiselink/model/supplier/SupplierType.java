/**
 * SupplierType.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午7:13:03
 */
package com.wiselink.model.supplier;

import net.sf.json.JSONArray;

/**
 * @author leo
 */
public enum SupplierType {
    Limited("有限责任公司"),
    Unlimited("无限责任公司"),
    Individual("个体工商户"),
    Natural("自然人");

    public final String cname;

    SupplierType(String name) {
        this.cname = name;
    }

    @Override
    public String toString() {
        return cname;
    }

    private static final String ALL;
    static {
        JSONArray j = new JSONArray();
        for (SupplierType type: values()) {
            j.add(type.cname);
        }
        ALL = j.toString();
    }

    public static String all() {
        return ALL;
    }
}
