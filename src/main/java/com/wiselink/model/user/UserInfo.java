/**
 * UserCoreInfo.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午4:10:05
 */
package com.wiselink.model.user;

import java.sql.Timestamp;

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
    //public String password;
    public String avatar;
    public String email;
    public String phone;
    public String tel;
    public String desc;
    public String province; 
    public String city; 
    public Timestamp createTime;
    public String creatorId;
    public Timestamp updateTime;
    public String operId;

    public UserInfo() {}
    

    /**
     * constructor
     * @param id
     * @param account
     * @param name
     * @param password
     * @param avatar
     * @param email
     * @param phone
     * @param tel
     * @param desc
     * @param province
     * @param city
     * @param createTime
     * @param creatorId
     * @param updateTime
     * @param operId
     */
    public UserInfo(String id, String account, String name, String password, String avatar, String email, String phone,
            String tel, String desc, String province, String city, Timestamp createTime, String creatorId,
            Timestamp updateTime, String operId) {
        this.id = id;
        this.account = account;
        this.name = name;
        // this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.tel = tel;
        this.desc = desc;
        this.province = province;
        this.city = city;
        this.createTime = createTime;
        this.creatorId = creatorId;
        this.updateTime = updateTime;
        this.operId = operId;
    }


    public String getId() {
        return id;
    }

    public UserInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public UserInfo setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public UserInfo setPassword(String password) {
//        this.password = password;
//        return this;
//    }

    public String getAvatar() {
        return avatar;
    }

    public UserInfo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public UserInfo setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public UserInfo setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public UserInfo setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public UserInfo setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public UserInfo setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public UserInfo setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getOperId() {
        return operId;
    }

    public UserInfo setOperId(String operId) {
        this.operId = operId;
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