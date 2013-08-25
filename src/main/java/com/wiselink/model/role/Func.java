/**
 * Func.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 下午12:55:26
 */
package com.wiselink.model.role;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 功能
 * 一个功能表示一个角色可以在系统中进行的事务操作
 * @author leo
 */
public class Func implements Jsonable {
    public int code;
    public String name;
    public String desc;
    public int moduleCode;

    public Func(){}

    public Func(int code, String name, String desc, int moduleCode) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.moduleCode = moduleCode;
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
