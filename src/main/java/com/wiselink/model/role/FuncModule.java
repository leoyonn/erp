/**
 * FuncModule.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 下午1:21:41
 */
package com.wiselink.model.role;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wiselink.base.jsonable.Jsonable;

/**
 * 功能模块
 * 一个功能模块中包含相关的一个或多个功能
 * @author leo
 */
public class FuncModule implements Jsonable {
    public int code;
    public String name;
    public String desc;
    private List<Func> funcs;

    public FuncModule(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    /**
     * 添加一个功能到模块
     * @param func
     */
    public void addFunc(Func func) {
        if (funcs == null) {
            funcs = new ArrayList<Func>();
        }
        funcs.add(func);
    }

    public List<Func> getFuncs() {
        return funcs;
    }
    
    public String toString() {
        return toJson();
    }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("name", name);
        json.put("desc", desc);
        if (funcs != null) {
            JSONArray farr = new JSONArray();
            for (Func f: funcs) {
                farr.add(f);
            }
            json.put(funcs, farr);
        }
        return json.toString();
    }

    @Override
    public Jsonable fromJson(String jsonStr) {
        JSONObject json = JSONObject.fromObject(jsonStr);
        this.code = json.getInt("code");
        this.name = json.getString("name");
        this.desc = json.getString("desc");
        JSONArray farr = json.optJSONArray("funcs");
        if (farr != null) {
            this.funcs = new ArrayList<Func>(farr.size());
            for (int i = 0; i < farr.size(); i++) {
                this.funcs.add((Func) new Func().fromJson(farr.getString(i)));
            }
        }
        return this;
    }
}
