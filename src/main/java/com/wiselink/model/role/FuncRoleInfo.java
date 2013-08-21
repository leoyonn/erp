/**
 * FuncRoleInfo.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 下午7:19:32
 */
package com.wiselink.model.role;

import java.sql.Timestamp;

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
    public Timestamp createTime;
    public String creatorId;
    public Timestamp updateTime;

    public FuncRoleInfo(int code, String name, String desc, String corpId, String deptId, 
            String creatorId, Timestamp createTime, Timestamp updateTime) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.corpId = corpId;
        this.deptId = deptId;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public int getCode() {
        return code;
    }
    
    public FuncRoleInfo setCode(int code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public FuncRoleInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public FuncRoleInfo setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getCorpId() {
        return corpId;
    }

    public FuncRoleInfo setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }

    public String getDeptId() {
        return deptId;
    }

    public FuncRoleInfo setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public FuncRoleInfo setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public FuncRoleInfo setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public FuncRoleInfo setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
