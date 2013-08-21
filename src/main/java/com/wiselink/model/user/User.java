/**
 * User.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-20 下午11:45:06
 */
package com.wiselink.model.user;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * 
 * @author leo
 */
public class User  implements Jsonable {
    public String id;
    public String account;
    public String name;
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

    public UserCategory cat;
    public UserStatus stat;
    public Position pos;
    public FuncRoleInfo frole;
    public DataRoleInfo drole;
    public Corp corp;
    public Dept dept;

    public User() {}

    /**
     * @param raw
     */
    public User(UserRaw raw) {
        this.id = raw.id;
        this.account = raw.account;
        this.name = raw.name;
        this.avatar = raw.avatar;
        this.email = raw.email;
        this.phone = raw.phone;
        this.tel = raw.tel;
        this.desc = raw.desc;
        this.province = raw.province;
        this.city = raw.city;
        this.createTime = raw.createTime;
        this.creatorId = raw.creatorId;
        this.updateTime = raw.updateTime;
        this.operId = raw.operId;
    }

    public String getId() {
        return id;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public User setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public User setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getOperId() {
        return operId;
    }

    public User setOperId(String operId) {
        this.operId = operId;
        return this;
    }

    public UserCategory getCat() {
        return cat;
    }

    public User setCat(UserCategory cat) {
        this.cat = cat;
        return this;
    }

    public UserStatus getStat() {
        return stat;
    }

    public User setStat(UserStatus stat) {
        this.stat = stat;
        return this;
    }

    public Position getPos() {
        return pos;
    }

    public User setPos(Position pos) {
        this.pos = pos;
        return this;
    }

    public FuncRoleInfo getFrole() {
        return frole;
    }

    public User setFrole(FuncRoleInfo frole) {
        this.frole = frole;
        return this;
    }

    public DataRoleInfo getDrole() {
        return drole;
    }

    public User setDrole(DataRoleInfo drole) {
        this.drole = drole;
        return this;
    }

    public Corp getCorp() {
        return corp;
    }

    public User setCorp(Corp corp) {
        this.corp = corp;
        return this;
    }

    public Dept getDept() {
        return dept;
    }

    public User setDept(Dept dept) {
        this.dept = dept;
        return this;
    }

    public User setId(String id) {
        this.id = id;
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