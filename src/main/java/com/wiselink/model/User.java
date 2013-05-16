/**
 * User.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:05:11
 */
package com.wiselink.model;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class User implements Jsonable {
    // auth
    private long id;
    private String account;
    private String name;
    private String password;

    // info
    private String avatar;
    private String email;
    private String phone;
    private String tel;
    private String desc;
    private String corpId;
    private String deptId;
    private String province; 
    private String city; 
    
    // op
    private Timestamp updateTime;
    private Timestamp createTime;
    private long opUserId;

    // role
    private String cat; // UserCategory.name()
    private String type;  // UserType.name()
    private String drole; // DataRole.name()
    private String frole; // FuncRole.name()
    private String stat; // UserStatus.name()

    public long getId() {
        return id;
    }

    public User setId(long id) {
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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public User setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public long getOpUserId() {
        return opUserId;
    }

    public User setOpUserId(long opUserId) {
        this.opUserId = opUserId;
        return this;
    }

    public String getCat() {
        return cat;
    }

    public User setCat(String cat) {
        this.cat = cat;
        return this;
    }

    public String getType() {
        return type;
    }

    public User setType(String type) {
        this.type = type;
        return this;
    }

    public String getDrole() {
        return drole;
    }

    public User setDrole(String drole) {
        this.drole = drole;
        return this;
    }

    public String getFrole() {
        return frole;
    }

    public User setFrole(String frole) {
        this.frole = frole;
        return this;
    }

    public String getStat() {
        return stat;
    }

    public User setStat(String stat) {
        this.stat = stat;
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
