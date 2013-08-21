/**
 * UserRaw.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-18 下午7:01:29
 */
package com.wiselink.model.user;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class UserCardRaw implements Jsonable {
    public String id;
    public String name;
    public int posCode;
    public String corpId;
    public String deptId;

    public UserCardRaw() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserCardRaw setName(String name) {
        this.name = name;
        return this;
    }

    public int getPosCode() {
        return posCode;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getDeptId() {
        return deptId;
    }

    public UserCardRaw setId(String id) {
        this.id = id;
        return this;
    }

    public UserCardRaw setPosCode(int posCode) {
        this.posCode = posCode;
        return this;
    }
    
    public UserCardRaw setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }
    public UserCardRaw setDeptId(String deptId) {
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