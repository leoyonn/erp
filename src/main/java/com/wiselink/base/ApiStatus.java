/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:26:54
 */
package com.wiselink.base;


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
    AUTH_DENIED(00011, "拒绝访问"),
    AUTH_LOGIN_REQUIRED(00012, "接口需要登录才能访问"),

    AUTH_SERVER_ERROR(00020, "授权服务出现错误"),
    AUTH_DB_ERROR(00030, "数据库出现错误"),

    INVALID_PARAMETER(00100, "无效参数"),
    
    DATA_INSERT_FAILED(00200, "添加数据失败"),
    DATA_QUERY_FAILED(00201, "数据检索失败"),
    DATA_EMPTY(00203, "没有更多数据"),
    
    SERVICE_ERROR(00300, "数据读写或服务出现错误，请联系管理员"),
    ;
    private int code;

    private String msg;

    ApiStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toJson() {
        return "{\"code\":" + code + ",\"msg\":\"" + msg + "\"}";
    }

    public String toString() {
        return toJson();
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
