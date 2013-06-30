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

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
