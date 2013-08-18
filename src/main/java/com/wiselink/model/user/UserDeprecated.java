/**
 * User.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:05:11
 */
package com.wiselink.model.user;

import net.sf.json.JSONObject;

import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class UserDeprecated implements Jsonable {
    public String id;
    public UserInfo info;
    public UserRole role;

    /**
     * constructor
     * @param id
     * @param info
     * @param role
     */
    public UserDeprecated(String id, UserInfo info, UserRole role) {
        this.id = id;
        this.info = info;
        this.role = role;
    }

    public UserDeprecated() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        if (info != null) {
            json.put("info", info.toJson());
        }
        if (role != null) {
            json.put("role", role.toJson());
        }
        return json.toString();
    }

    @Override
    public Jsonable fromJson(String json) {
        JSONObject j = JSONObject.fromObject(json);
        this.id = j.getString("id");
        this.info = (UserInfo) new UserInfo().fromJson(j.getString("info"));
        this.role = (UserRole) new UserRole().fromJson(j.getString("role"));
        return this;
    }

    @Override
    public String toString() {
        return toJson();
    }
}
