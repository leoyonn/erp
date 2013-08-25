/**
 * FuncRole.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午3:13:40
 */
package com.wiselink.model.role;

import java.util.List;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.user.UserCard;
import com.wiselink.result.Checked;

/**
 * 功能角色，包括角色信息、角色功能(模块)、角色用户
 * @author leo
 */
public class FuncRole implements Jsonable {
    public FuncRoleInfo info;
    public List<Checked<Func>> funcs;
    public List<Checked<UserCard>> users;

    public FuncRole(FuncRoleInfo info) {
        this.info = info;
    }
    
    public FuncRole setFuncs(List<Checked<Func>> funcs) {
        this.funcs = funcs;
        return this;
    }
    
    public FuncRole setUsers(List<Checked<UserCard>> users) {
        this.users = users;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, FuncRole.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, FuncRole.class);
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}