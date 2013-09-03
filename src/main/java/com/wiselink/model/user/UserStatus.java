/**
 * UserStatus.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午4:00:21
 */
package com.wiselink.model.user;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public class UserStatus implements Jsonable {
    public static final UserStatus Active = new UserStatus(0, "Active", "正常");
    public static final UserStatus Blocked = new UserStatus(1, "Blocked", "禁止");
    public static final UserStatus Invalid = new UserStatus(2, "Invalid", "无效");

    public final int code;
    public final String name;
    public final String cname;

    UserStatus(int code, String name, String cname) {
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

    public static UserStatus fromCode(int code) {
        switch (code) {
            case 0: return Active;
            case 1: return Blocked;
            case 2: return Invalid;
            default: return null;
        }
    }

    public static Jsonable fromJsonStatic(String json) {
        return fromCode(JSONObject.fromObject(json).optInt("code", -1));
    }
}

