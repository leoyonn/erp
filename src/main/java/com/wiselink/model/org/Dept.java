/**
 * Dept.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:58:21
 */
package com.wiselink.model.org;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.base.CorpType;

/**
 * 
 * @author leo
 */
public class Dept implements Jsonable {
    /** type should be {@link CorpType#name()} */
    public String type;
    public String id;
    public String name;
    public String address;
    public String tel;
    public String contact;

    public Dept() {}

    public Dept(String type, String id, String name, String address, String tel, String contact) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.contact = contact;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, Corp.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, Corp.class);
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}
