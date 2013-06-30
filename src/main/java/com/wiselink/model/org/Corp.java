/**
 * Corp.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午12:12:14
 */
package com.wiselink.model.org;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 公司信息
 * @author leo
 */
public class Corp extends Org {
    public String address;
    public String tel;
    public String contact;

    public Corp() {}

    public Corp(String id, String type, String name, String desc, String address, String tel, String contact) {
        super(id, type, name, desc);
        this.address = address;
        this.tel = tel;
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
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