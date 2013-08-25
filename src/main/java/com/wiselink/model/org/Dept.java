/**
 * Dept.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:58:21
 */
package com.wiselink.model.org;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public class Dept extends Org {
    public String deptType;
    public String corpId;
    public Corp corp;

    public Dept() {
        super.type = OrgType.Dept.cname;
    }

    public Dept(String id, String name, String desc, String deptType, String corpId) {
        super(id, OrgType.Dept.cname, name, desc);
        this.deptType = deptType;
        this.corpId = corpId;
    }

    public String getDeptType() {
        return deptType;
    }

    public Dept setDeptType(String deptType) {
        this.deptType = deptType;
        return this;
    }

    public String getCorpId() {
        return corpId;
    }

    public Dept setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }

    public Corp getCorp() {
        return corp;
    }

    public Dept setCorp(Corp corp) {
        this.corp = corp;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, Dept.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, Dept.class);
    }

    @Override
    public String toString() {
        return toJson();
    }
}