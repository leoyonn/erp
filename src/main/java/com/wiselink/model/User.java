/**
 * User.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:05:11
 */
package com.wiselink.model;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class User implements Jsonable {
    // auth
    public String id;
    public String account;
    public String name;
    public String password;

    // info
    public String avatar;
    public String email;
    public String phone;
    public String tel;
    public String desc;
    public String corpId;
    public String corp;
    public String deptId;
    public String dept;
    public String province; 
    public String city; 

    // op
    public long updateTime;
    public long createTime;
    public String opUserId;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public User setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public User setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public User setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getCorpId() {
        return corpId;
    }

    public User setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }

    public String getDeptId() {
        return deptId;
    }

    public User setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public User setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public User setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public User setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public User setOpUserId(String opUserId) {
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
