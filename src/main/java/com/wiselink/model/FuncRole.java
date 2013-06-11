/**
 * FuncRole.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午3:13:40
 */
package com.wiselink.model;

import java.util.List;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public class FuncRole implements Jsonable {
    public int code;
    public String name;
    public String desc;
    public String corpId;
    public String deptId;
    public long createTime;
    public String creatorId;
    public List<String> funcs;

    public FuncRole(int code, String name, String desc, String corpId, String deptId, long createTime, String creatorId) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.corpId = corpId;
        this.deptId = deptId;
        this.createTime = createTime;
        this.creatorId = creatorId;
    }
    
    public FuncRole setFuncs(List<String> funcs) {
        this.funcs = funcs;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, FuncRole.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, FuncRole.class);
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}