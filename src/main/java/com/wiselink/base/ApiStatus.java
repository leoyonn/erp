/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:26:54
 */
package com.wiselink.base;

import com.google.gson.Gson;

/**
 * @author leo
 */
public enum ApiStatus {
    SUCCESS(0, "成功"),
    INVALID(-1, "未知错误"),
    // 00xx: auth related
    AUTH_INVALID_USER(0001, "无效用户"),
    AUTH_WRONG_PASSWORD(0002, "密码错误"),
    AUTH_BLOCKED(00010, "用户已被禁用"),
    AUTH_DENIED(00011, "拒绝访问");

    private int code;

    private String msg;

    ApiStatus(int code, String msg) {
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

    public static ApiStatus fromAuthResult(AuthResult authResult) {
        ApiStatus status = ApiStatus.valueOf("AUTH_" + authResult.name());
        return status == null ? INVALID : status;
    }
}
