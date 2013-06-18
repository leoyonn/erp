/**
 * FuncRoleInfo.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 下午7:19:32
 */
package com.wiselink.model.role;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 功能角色信息
 * @author leo
 */
public class FuncRoleInfo implements Jsonable {
    public int code;
    public String name;
    public String desc;
    public String corpId;
    public String deptId;
    public long createTime;
    public String creatorId;

    public FuncRoleInfo(int code, String name, String desc, String corpId, String deptId, 
            long createTime, String creatorId) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.corpId = corpId;
        this.deptId = deptId;
        this.createTime = createTime;
        this.creatorId = creatorId;
    }

    public FuncRoleInfo() {}

    @Override
    public String toJson() {
        return new Gson().toJson(this, FuncRoleInfo.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, FuncRoleInfo.class);
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}
