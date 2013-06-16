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
    public int id;
    /** use {@link OrgType#cname} */
    public String type;
    public String name;
    
    public Org() {}

    public Org(int orgId, String orgType, String orgName) {
        this.id = orgId;
        this.type = orgType;
        this.name = orgName;
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
