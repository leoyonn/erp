/**
 * SupplierMode.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午7:25:28
 */
package com.wiselink.model.supplier;

import net.sf.json.JSONArray;

/**
 * @author leo
 */
public enum SupplierMode {
    Direct("直购供货商"),
    Central("集中采购供货商"),
    General("直购集中采购综合提供商");

    public final String cname;

    SupplierMode(String name) {
        this.cname = name;
    }

    @Override
    public String toString() {
        return cname;
    }

    private static final String ALL;
    static {
        JSONArray j = new JSONArray();
        for (SupplierMode type: values()) {
            j.add(type.cname);
        }
        ALL = j.toString();
    }

    public static String all() {
        return ALL;
    }
}
