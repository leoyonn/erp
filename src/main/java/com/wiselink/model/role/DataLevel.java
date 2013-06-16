/**
 * DataLevel.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午10:02:24
 */
package com.wiselink.model.role;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public class DataLevel implements Jsonable {
    public int code;
    public String name;
    public String desc;

    /**
     * constructor
     * @param code
     * @param name
     * @param des
     */
    public DataLevel(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, DataLevel.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, DataLevel.class);
    }

}
