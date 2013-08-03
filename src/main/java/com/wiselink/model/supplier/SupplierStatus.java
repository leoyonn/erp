/**
 * SupplierStatus.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午7:31:23
 */
package com.wiselink.model.supplier;

import net.sf.json.JSONArray;

/**
 * @author leo
 */
public enum SupplierStatus {
    Normal("正常"),
    Stop("停止合作"),
    Delete("删除"),
    Block("黑名单");

    public final String cname;

    SupplierStatus(String name) {
        this.cname = name;
    }

    @Override
    public String toString() {
        return cname;
    }

    private static final String ALL;
    static {
        JSONArray j = new JSONArray();
        for (SupplierStatus type: values()) {
            j.add(type.cname);
        }
        ALL = j.toString();
    }

    public static String all() {
        return ALL;
    }
}
