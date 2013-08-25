/**
 * ErrorCode.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-24 下午7:34:30
 */
package com.wiselink.result;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author leo
 */
public enum ErrorCode {
    Unknown(-1, "未知错误"),
    Success(0, "成功"),

    // 101 - 200 认证相关错误
    InvalidUser(101, "无效用户"),
    WrongPassword(101, "密码错误"),
    Blocked(103, "用2户已被禁用"),
    AuthDenied(104, "权限限制，访问被拒绝"),
    LoginRequired(105, "接口需要登录才能访问"),

    // 201 - 300 接口、参数相关错误
    InvalidParam(201, "无效参数"),
    BlankParam(202, "参数为空"),
    
    // 301 - 400 数据错误
    DbError(310, "数据库错误"),
    DbInsertFail(303, "添加数据失败，要添加的数据可能已经存在"),
    DbUpdateFail(304, "更新数据失败，请检查参数内容"),
    DbDeleteFail(305, "删除数据失败，请确认无相关依赖"),
    DbQueryFail(306, "检索数据失败，请确认参数内容"),
    DbEmpty(307, "无与查询参数相关数据"),

    // 401 - 500 系统级错误
    ServerError(401, "服务系统错误"),
    FormatError(402, "系统参数错误，请联系开发人员");

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    private static final Map<Integer, ErrorCode> map;
    static {
        map = new HashMap<Integer, ErrorCode>();
        for (ErrorCode e: values()) {
            map.put(e.code(), e);
        }
    }

    public int code() {
        return code;
    }
    
    public String desc() {
        return desc;
    }

    public String toJson() {
        return "{\"code\":" + code + ",\"name\":\"" + name() + "\",\"desc\":\"" + desc + "\"}";
    }

    public static ErrorCode fromCode(int code) {
        return map.get(code);
    }
    
    public static void main(String[]args) {
        JSONObject j = JSONObject.fromObject(ErrorCode.InvalidUser.toJson());
        System.out.println(j);
    }
}
