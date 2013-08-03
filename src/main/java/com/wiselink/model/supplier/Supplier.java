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

/**
 * @author leo
 */
public class Supplier implements Jsonable {
    public String id;
    public String name;
    public String desc;
    public String type;
    public String mode;
    public String contact;
    public String tel;
    public String email;
    public String status;
    public Timestamp startTime;
    public Timestamp endTime;
    public Timestamp createTime;
    public String creatorId;

    public String getId() {
        return id;
    }

    public Supplier setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Supplier setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Supplier setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getType() {
        return type;
    }

    public Supplier setType(String type) {
        this.type = type;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public Supplier setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public Supplier setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public Supplier setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Supplier setEmail(String email) {
        this.email = email;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Supplier setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Supplier setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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
