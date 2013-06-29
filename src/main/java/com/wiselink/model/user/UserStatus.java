/**
 * UserStatus.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午4:00:21
 */
package com.wiselink.model.user;

import net.sf.json.JSONObject;

import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.utils.Utils;

/**
 * 
 * @author leo
 */
public enum UserStatus implements Jsonable {
    Active(0, "正常"),
    Blocked(1, "禁止"),
    Invalid(2, "无效");

    public final int code;
    public final String desc;
    public final String json;

    UserStatus(int code, String desc) {
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

    public static UserStatus fromCode(int code) {
        switch (code) {
            case 0: return Active;
            case 1: return Blocked;
            case 2: return Invalid;
            default: throw new IllegalArgumentException("invalid code in parameter");
        }
    }

    public static Jsonable fromJsonStatic(String json) {
        return fromCode(JSONObject.fromObject(json).optInt("code", -1));
    }
}

