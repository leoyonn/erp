/**
 * UserCategory.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:26:26
 */
package com.wiselink.model.user;

import net.sf.json.JSONObject;

import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.utils.Utils;

/**
 * 用户归属性质：1、公司内部；2、供货商
 * @author leo
 */
public enum UserCategory implements Jsonable {
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

    @Override
    public String toJson() {
        return json;
    }

    @Override
    public Jsonable fromJson(String json) {
        return fromJsonStatic(json);
    }

    public static UserCategory fromCode(int code) {
        switch(code) {
            case 0: return Corp;
            case 1: return Supplier;
            default: throw new IllegalArgumentException("invalid code in parameter");
        }
    }

    public static Jsonable fromJsonStatic(String json) {
        return fromCode(JSONObject.fromObject(json).optInt("code", -1));
    }
}
