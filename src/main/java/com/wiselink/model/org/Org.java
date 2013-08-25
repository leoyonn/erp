/**
 * Org.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午12:53:18
 */
package com.wiselink.model.org;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 组织结构，如部门、分公司等
 * @author leo
 */
public class Org implements Jsonable {
    public String id;
    /** use {@link OrgType#cname} */
    public String type;
    public String name;
    public String desc;
    
    public Org() {}

    public Org(String orgId, String orgType, String orgName, String desc) {
        this.id = orgId;
        this.type = orgType;
        this.name = orgName;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public Org setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Org setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public Org setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Org setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, Org.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, Org.class);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
