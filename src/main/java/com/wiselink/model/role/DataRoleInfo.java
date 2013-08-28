/**
 * DataRoleInfo.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午1:06:38
 */
package com.wiselink.model.role;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 数据角色信息，包括code、名称、描述、级别等
 * @author leo
 */
public class DataRoleInfo implements Jsonable {
    public int code;
    public String name;
    public String desc;
    public int levelCode;
    public String levelName;
    public String corpId;
    public String deptId;
    public String creatorId;
    public Timestamp createTime;
    public Timestamp updateTime;

    public DataRoleInfo(int code, String name, String desc, int levelCode, String corpId, String deptId, 
            String creatorId, Timestamp createTime, Timestamp updateTime) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.levelCode = levelCode;
        this.corpId = corpId;
        this.deptId = deptId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DataRoleInfo() {}

    public DataRoleInfo setLevelName(String levelName) {
        this.levelName = levelName;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, DataRoleInfo.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, DataRoleInfo.class);
    }
    
    @Override
    public String toString() {
        return toJson();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public int getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(int levelCode) {
        this.levelCode = levelCode;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
