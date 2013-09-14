/**
 * UserQueryParam.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-9-10 上午12:01:17
 */
package com.wiselink.model.param;

import java.util.List;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public class UserQueryParam implements Jsonable {
    public String name;
    public String corpId;
    public String deptId;
    public int posCode;
    public int froleCode;
    public int droleCode;
    public int from;
    public int to;
    public String myCorpId;
    public List<String> suppliers;
    public List<String> subcorps;

    public String getName() {
        return name;
    }

    public UserQueryParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getCorpId() {
        return corpId;
    }

    public UserQueryParam setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }

    public String getDeptId() {
        return deptId;
    }

    public UserQueryParam setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }

    public int getPosCode() {
        return posCode;
    }

    public UserQueryParam setPosCode(int posCode) {
        this.posCode = posCode;
        return this;
    }

    public int getFroleCode() {
        return froleCode;
    }

    public UserQueryParam setFroleCode(int froleCode) {
        this.froleCode = froleCode;
        return this;
    }

    public int getDroleCode() {
        return droleCode;
    }

    public UserQueryParam setDroleCode(int droleCode) {
        this.droleCode = droleCode;
        return this;
    }

    public int getFrom() {
        return from;
    }

    public UserQueryParam setFrom(int from) {
        this.from = from;
        return this;
    }

    public int getTo() {
        return to;
    }

    public UserQueryParam setTo(int to) {
        this.to = to;
        return this;
    }

    public String getMyCorpId() {
        return myCorpId;
    }

    public UserQueryParam setMyCorpId(String myCorpId) {
        this.myCorpId = myCorpId;
        return this;
    }

    public List<String> getSuppliers() {
        return suppliers;
    }

    public UserQueryParam setSuppliers(List<String> suppliers) {
        this.suppliers = suppliers;
        return this;
    }

    public List<String> getSubcorps() {
        return subcorps;
    }

    public UserQueryParam setSubcorps(List<String> subcorps) {
        this.subcorps = subcorps;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, UserQueryParam.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, UserQueryParam.class);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
