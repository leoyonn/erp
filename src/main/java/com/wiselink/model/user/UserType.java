/**
 * UserType.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午3:27:47
 */
package com.wiselink.model.user;

/**
 * 用户类别，比如采购员/省市县公司技术部领导等
 * @author leo
 */
public enum UserType {
    CEO(0, "总经理", "总经理"),
    TECH_LEAD(1, "技术领导", "技术领导"),
    TECH(2, "技术员", "技术员"),
    AUDIT(3, "审计员", "审计员"),
    BUYER(4, "采购员", "采购员");
    ;
    
    private int code;
    private String name;
    private String desc;

    UserType(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
