/**
 * UserCard.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午2:31:15
 */
package com.wiselink.model.user;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 用户简单名片，是{@link UserDeprecated}的子集
 * @author leo
 */
public class UserCard implements Jsonable {
    public String id;
    public String name;
    public String corp;
    public String dept;
    public String pos;
    
    public UserCard(){}
    
    public UserCard(String id, String name, String corp, String dept, String pos) {
        this.id = id;
        this.name = name;
        this.corp = corp;
        this.dept = dept;
        this.pos = pos;
    }

    public UserCard(UserCardRaw raw) {
        this.id = raw.id;
        this.name = raw.name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, UserCard.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, UserCard.class);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
