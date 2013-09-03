/**
 * Supplier.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午7:04:01
 */
package com.wiselink.model.supplier;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.org.Corp;

/**
 * @author leo
 */
public class Supplier extends Corp implements Jsonable {
    public String mode;
    public String stype;
    public String email;
    public String status;
    public Timestamp startTime;
    public Timestamp endTime;

    public String getMode() {
        return mode;
    }

    public Supplier setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getStype() {
        return stype;
    }

    public Supplier setStype(String stype) {
        this.stype = stype;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Supplier setStatus(String status) {
        this.status = status;
        return this;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Supplier setStartTime(Timestamp startTime) {
        this.startTime = startTime;
        return this;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Supplier setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        return this;
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
