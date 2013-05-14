/**
 * User.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:05:11
 */
package com.wiselink.model;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.base.City;
import com.wiselink.model.base.DataRole;
import com.wiselink.model.base.FuncRole;
import com.wiselink.model.base.Provice;
import com.wiselink.model.base.UserCategory;
import com.wiselink.model.base.UserStatus;
import com.wiselink.model.base.UserType;

/**
 * @author leo
 */
public class User implements Jsonable {
    private String id;
    private String account;
    private String name;
    private String password;

    private UserCategory cat; 
    private UserType type;
    private DataRole drole; 
    private FuncRole frole; 
    private UserStatus stat; 

    private String avatar;
    private String email;
    private String phone;
    private String tel;
    private String desc;
    private String corpId;
    private String deptId;

    private Provice province; 
    private City city; 
    private long updateTime;
    private long createTime;
    private String opUserId;
    private transient String forTest;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserCategory getCat() {
        return cat;
    }
    public void setCat(UserCategory cat) {
        this.cat = cat;
    }
    public UserType getType() {
        return type;
    }
    public void setType(UserType type) {
        this.type = type;
    }
    public DataRole getDrole() {
        return drole;
    }
    public void setDrole(DataRole drole) {
        this.drole = drole;
    }
    public FuncRole getFrole() {
        return frole;
    }
    public void setFrole(FuncRole frole) {
        this.frole = frole;
    }
    public UserStatus getStat() {
        return stat;
    }
    public void setStat(UserStatus stat) {
        this.stat = stat;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getCorpId() {
        return corpId;
    }
    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }
    public String getDeptId() {
        return deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public Provice getProvince() {
        return province;
    }
    public void setProvince(Provice province) {
        this.province = province;
    }
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public long getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
    public long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public String getOpUserId() {
        return opUserId;
    }
    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getForTest() {
        return forTest;
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
