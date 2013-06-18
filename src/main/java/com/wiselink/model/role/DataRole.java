/**
 * DataRole.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午10:02:14
 */
package com.wiselink.model.role;

import java.util.List;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.org.Org;
import com.wiselink.model.user.UserCard;

/**
 * 数据角色，包括数据角色信息、数据权限范围、角色用户
 * @author leo
 */
public class DataRole implements Jsonable {
    /** 数据角色信息 */
    public DataRoleInfo info;
    /** 数据角色权限范围，参见{@link Org} */
    public List<Org> scopes;
    /** 数据角色中的用户 */
    public List<UserCard> users;

    public DataRole(DataRoleInfo info) {
        this.info = info;
    }

    public DataRole setScopes(List<Org> scopes) {
        this.scopes = scopes;
        return this;
    }

    public DataRole setUsers(List<UserCard> users) {
        this.users = users;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, DataRole.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, DataRole.class);
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}