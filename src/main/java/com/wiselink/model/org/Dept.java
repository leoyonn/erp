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

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
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
    
    public static void main(String[] args) {
        System.out.println(new Dept("1", "3", "4", "5", "6").toJson());
    }
}