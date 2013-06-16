/**
 * OrgType.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午12:54:10
 */
package com.wiselink.model.org;

import net.sf.json.JSONObject;

/**
 * 组织类型
 * @author leo
 */
public enum OrgType {
    Group("集团"),
    Corp0("总公司"),
    Corp1("省分公司"),
    Corp2("市分公司"),
    Corp3("县支公司"),
    Dept("部门"),
    Supplier("供货商"),
    ;
    
    public final String cname;
    
    OrgType(String name) {
        this.cname = name;
    }

    public String typeName() {
        return cname;
    }

    public String toJson() {
        JSONObject j = new JSONObject();
        j.put("name", name());
        j.put("cname", cname);
        return j.toString();
    }

    @Override
    public String toString() {
        return toJson();
    }
}
