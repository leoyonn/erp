/**
 * UserRaw.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-18 下午7:01:29
 */
package com.wiselink.model.user;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class UserRaw implements Jsonable {
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

    public int catCode = -1;
    public int posCode = -1;
    public int froleCode = -1;
    public int droleCode = -1;
    public int statCode = -1;
    public String corpId = "";
    public String deptId = "";

    public UserRaw() {}

    public String getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public UserRaw setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRaw setName(String name) {
        this.name = name;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserRaw setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRaw setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserRaw setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public UserRaw setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public UserRaw setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public UserRaw setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserRaw setCity(String city) {
        this.city = city;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public UserRaw setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public UserRaw setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public UserRaw setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getOperId() {
        return operId;
    }

    public UserRaw setOperId(String operId) {
        this.operId = operId;
        return this;
    }

    public int getCatCode() {
        return catCode;
    }

    public int getPosCode() {
        return posCode;
    }

    public int getFroleCode() {
        return froleCode;
    }

    public int getDroleCode() {
        return droleCode;
    }

    public int getStatCode() {
        return statCode;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getDeptId() {
        return deptId;
    }

    public UserRaw setId(String id) {
        this.id = id;
        return this;
    }
    public UserRaw setCatCode(int catCode) {
        this.catCode = catCode;
        return this;
    }

    public UserRaw setPosCode(int posCode) {
        this.posCode = posCode;
        return this;
    }

    public UserRaw setDroleCode(int droleCode) {
        this.droleCode = droleCode;
        return this;
    }

    public UserRaw setFroleCode(int froleCode) {
        this.froleCode = froleCode;
        return this;
    }

    public UserRaw setStatCode(int statCode) {
        this.statCode = statCode;
        return this;
    }
    
    public UserRaw setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }
    public UserRaw setDeptId(String deptId) {
        this.deptId = deptId;
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