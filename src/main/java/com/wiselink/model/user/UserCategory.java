/**
 * UserCategory.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:26:26
 */
package com.wiselink.model.user;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 用户归属性质：1、公司内部；2、供货商
 * @author leo
 */
public class UserCategory implements Jsonable {
    public final static UserCategory Corp = new UserCategory(0, "Corp", "公司内部");
    public final static UserCategory Supplier = new UserCategory(1, "Supplier", "供货商");

    public final int code;
    public final String name;
    public final String cname;

    UserCategory(int code, String name, String cname) {
        this.code = code;
        this.cname = cname;
        this.name = name;
    }

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, getClass());
    }

    @Override
    public Jsonable fromJson(String json) {
        return fromJsonStatic(json);
    }

    public static UserCategory fromCode(int code) {
        switch(code) {
            case 0: return Corp;
            case 1: return Supplier;
            default: return null;
        }
    }

    public static Jsonable fromJsonStatic(String json) {
        return fromCode(JSONObject.fromObject(json).optInt("code", -1));
    }
}
