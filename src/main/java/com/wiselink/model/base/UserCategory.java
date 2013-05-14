/**
 * UserCategory.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:26:26
 */
package com.wiselink.model.base;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 用户归属性质：1、公司内部；2、供货商
 * @author leo
 */
public enum UserCategory implements Jsonable {
    CORP_L0(0, "总公司", "总公司"),
    CORP_L1(1, "省公司", "省公司"),
    CORP_L2(2, "市分公司", "市分公司"),
    CORP_L3(3, "县分公司", "县分公司"),
    CORP_L4(4, "乡镇分公司", "乡镇分公司"),
    CORP_L5(5, "村社分公司", "村社分公司"),
    
    VENDOR(8, "供货商", "供货商");

    private int code;

    private String name;

    private String desc;

    UserCategory(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
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

    @Override
    public String toJson() {
        return new Gson().toJson(this, getClass());
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, getClass());
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}
