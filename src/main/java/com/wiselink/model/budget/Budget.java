/**
 * Budget.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-4 上午11:15:59
 */
package com.wiselink.model.budget;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class Budget implements Jsonable {
    public int code;
    public String type;
    public String progress; 
    public String status; 
    public String year;
    public String org;
    public Timestamp createTime;
    public String creatorId;
    public String amountApply;
    public String amountApprove;
    public String amountAlloc; 

    public Budget() {}

    public int getCode() {
        return code;
    }

    public Budget setCode(int id) {
        this.code = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Budget setType(String type) {
        this.type = type;
        return this;
    }

    public String getProgress() {
        return progress;
    }

    public Budget setProgress(String progress) {
        this.progress = progress;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Budget setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getYear() {
        return year;
    }

    public Budget setYear(String year) {
        this.year = year;
        return this;
    }

    public String getOrg() {
        return org;
    }

    public Budget setOrg(String org) {
        this.org = org;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Budget setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Budget setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getAmountApply() {
        return amountApply;
    }

    public Budget setAmountApply(String amountApply) {
        this.amountApply = amountApply;
        return this;
    }

    public String getAmountApprove() {
        return amountApprove;
    }

    public Budget setAmountApprove(String amountApprove) {
        this.amountApprove = amountApprove;
        return this;
    }

    public String getAmountAlloc() {
        return amountAlloc;
    }

    public Budget setAmountAlloc(String amountAlloc) {
        this.amountAlloc = amountAlloc;
        return this;    }

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