/**
 * UserRole.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-25 下午4:12:55
 */
package com.wiselink.model;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.base.DataRole;
import com.wiselink.model.base.UserCategory;
import com.wiselink.model.base.UserStatus;
import com.wiselink.model.base.UserType;
import com.wiselink.model.role.FuncRole;

/**
 * 用户的功能角色、数据角色、类型等 
 * @author leo
 */
public class UserRole implements Jsonable {
    /** cat should be set as {@link UserCategory#name()} */
    private String cat; // UserCategory.name()
    /** type should be set as {@link UserType#name()} */
    private String type;  // UserType.name()
    /** frole should be set as {@link FuncRole#name()} */
    private String froleId; // FuncRole.name()
    /** drole should be set as {@link DataRole#name()} */
    private String drole; // DataRole.name()
    /** stat should be set as {@link UserStatus#name()} */
    private String stat; // UserStatus.name()

    public String getCat() {
        return cat;
    }

    public UserRole setCat(String cat) {
        this.cat = cat;
        return this;
    }

    public String getType() {
        return type;
    }

    public UserRole setType(String type) {
        this.type = type;
        return this;
    }

    public String getDrole() {
        return drole;
    }

    public UserRole setDrole(String drole) {
        this.drole = drole;
        return this;
    }

    public String getFroleId() {
        return froleId;
    }

    public UserRole setFroleId(String froleId) {
        this.froleId = froleId;
        return this;
    }

    public String getStat() {
        return stat;
    }

    public UserRole setStat(String stat) {
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
