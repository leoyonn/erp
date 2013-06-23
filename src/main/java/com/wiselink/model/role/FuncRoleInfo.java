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

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
