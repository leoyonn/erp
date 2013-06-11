/**
 * UserCategory.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:26:26
 */
package com.wiselink.model.base;

import com.wiselink.utils.Utils;

/**
 * 用户归属性质：1、公司内部；2、供货商
 * @author leo
 */
public enum UserCategory {
    Corp(0, "公司内部"),
    Supplier(1, "供货商");

    public final int code;
    public final String desc;
    public final String json;

    UserCategory(int code, String desc) {
        this.code = code;
        this.desc = desc;
        json = Utils.buildEnumJson(code, name(), desc);
    }

    @Override
    public String toString() {
        return json;
    }
}
