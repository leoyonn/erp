/**
 * Jsonable.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午4:02:12
 */
package com.wiselink.base.jsonable;

/**
 * 
 * @author leo
 */
public interface Jsonable {
    public String toJson();
    public Jsonable fromJson(String json);
}
