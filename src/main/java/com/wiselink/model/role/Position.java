/**
 * Position.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-17 下午1:50:27
 */
package com.wiselink.model.role;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 用户岗位描述
 * @author leo
 */
public class Position implements Jsonable {
    public int code;
    public String name;
    public String desc;

    /**
     * constructor
     * @param code
     * @param name
     * @param des
     */
    public Position(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public Position() {}

    @Override
    public String toJson() {
        return new Gson().toJson(this, Position.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, Position.class);
    }
}
