/**
 * UserCoreInfo.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午4:10:05
 */
package com.wiselink.model.user;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 用户静态信息<p>
 * 用户信息{@link User} 由两部分组成，用户静态信息{@link UserInfo} 和用户角色信息{@link UserRole}
 * @author leo
 */
public class UserInfo implements Jsonable {
    public String id;
    public String account;
    public String name;
    public String password;
    public String avatar;
    public String email;
    public String phone;
    public String tel;
    public String desc;
    public String province; 
    public String city; 
    public long updateTime;
    public long createTime;
    public String opUserId;

    public UserInfo setId(String id) {
        this.id = id;
        return this;
    }

    public UserInfo setAccount(String account) {
        this.account = account;
        return this;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserInfo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserInfo setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public UserInfo setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public UserInfo setProvince(String province) {
        this.province = province;
        return this;
    }

    public UserInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public UserInfo setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public UserInfo setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public UserInfo setOpUserId(String opUserId) {
        this.opUserId = opUserId;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, getClass());
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, getClass());
    }

    @Override
    public String toString() {
        return toJson();
    }
}