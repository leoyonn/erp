/**
 * UserRole.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-25 下午4:12:55
 */
package com.wiselink.model;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class UserRole implements Jsonable {
    private String cat; // UserCategory.name()
    private String type;  // UserType.name()
    private String drole; // DataRole.name()
    private String frole; // FuncRole.name()
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

    public String getFrole() {
        return frole;
    }

    public UserRole setFrole(String frole) {
        this.frole = frole;
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
