/**
 * Ut.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-21 上午11:14:48
 */
package com.wiselink.model;

import com.google.gson.Gson;

/**
 * @author leo
 */
public class Ut {
    public String id;
    public String name;
    public int age;
    public String tel;

    public Ut() {}
    
    public Ut(String id, String name, int age, String tel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.tel = tel;
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String toString() {
        return new Gson().toJson(this, getClass());
    }
    
}
