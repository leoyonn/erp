/**
 * AuthResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:58:09 PM
 */
package com.wiselink.base;

/**
 * should map to {@link ApiStatus}
 * @author leo
 */
public enum AuthResult {
    SUCCESS(0, "认证成功"),
    INVALID_USER(1, "无效用户"),
    WRONG_PASSWORD(2, "密码错误"),

    BLOCKED(10, "用户已被禁用"),
    DENIED(11, "访问被拒绝"),
    LOGIN_REQUIRED(12, "接口需要登录才能访问"),
    
    SERVER_ERROR(20, "服务出现错误"),
    DB_ERROR(30, "数据库出现错误"),
    ;
    
    AuthResult(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    private int code;
    private String desc;
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String toString() {
        return toJson();
    }
    
    public String toJson() {
        return String.format("{\"code\":%d,\"desc\":\"%s\"}", code, desc);
    }
    
}
