/**
 * DataRoleInfo.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午1:06:38
 */
package com.wiselink.model.role;

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
    public String corpId;
    public String deptId;
    public long createTime;
    public String creatorId;

    public DataRoleInfo(int code, String name, String desc, int levelCode, String corpId, String deptId, 
            long createTime, String creatorId) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.levelCode = levelCode;
        this.corpId = corpId;
        this.deptId = deptId;
        this.createTime = createTime;
        this.creatorId = creatorId;
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
}
