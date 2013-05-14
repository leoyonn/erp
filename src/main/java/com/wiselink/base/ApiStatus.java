/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:26:54
 */
package com.wiselink.base;

import com.google.gson.Gson;

/**
 * 
 * @author leo
 */
public class ApiStatus {
    private int code;
    private String msg;
    
    public ApiStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toJson() {
        return new Gson().toJson(this, ApiStatus.class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
