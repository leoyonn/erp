/**
 * JsonableEnum.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午8:47:01
 */
package com.wiselink.base.jsonable;

/**
 * 
 * @author leo
 */
public interface JsonableEnum {
    public int getCode();
    public void setCode(int code);
    public JsonableEnum fromCode(int i);
}
