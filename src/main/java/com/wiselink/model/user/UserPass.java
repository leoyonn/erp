/**
 * UserPass.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-29 下午12:11:26
 */
package com.wiselink.model.user;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public class UserPass  implements Jsonable {
    public String id;
    public String account;
    public String password;
    
    public UserPass(){}
    
    public UserPass(String id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, UserPass.class);
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, UserPass.class);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
