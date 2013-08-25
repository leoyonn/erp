/**
 * FuncChecked.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-25 下午3:28:13
 */
package com.wiselink.result;

import net.sf.json.JSONObject;

import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public class Checked<T extends Jsonable> implements Jsonable {
    public T data;
    public boolean checked;
    
    public Checked(T data, boolean checked) {
        this.data = data;
        this.checked = checked;
    }

    public T getData() {
        return data;
    }

    public Checked<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public Checked<T> setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    @Override
    public String toJson() {
        JSONObject j = new JSONObject();
        j.put("data", data.toJson());
        j.put("checked", checked);
        return j.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Jsonable fromJson(String json) {
        JSONObject j = JSONObject.fromObject(json);
        this.data = (T) this.data.fromJson(j.getString("data"));
        this.checked = j.getBoolean("checked");
        return this;
    }

    
}
