/**
 * UserRole.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-25 下午4:12:55
 */
package com.wiselink.model.user;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 用户的类别、岗位、功能角色、数据角色、类型等的raw id信息，用于数据库读取<p>
 * 读取后需要转换成{@link UserRole} 
 * @author leo
 */
public class UserRoleC implements Jsonable {
    public String id;
    public int catCode;
    public int posCode;
    public int froleCode;
    public int droleCode;
    public int statCode;
    public String corpId;
    public String deptId;

    public UserRoleC setId(String id) {
        this.id = id;
        return this;
    }
    public UserRoleC setCatCode(int catCode) {
        this.catCode = catCode;
        return this;
    }

    public UserRoleC setPosCode(int posCode) {
        this.posCode = posCode;
        return this;
    }

    public UserRoleC setDroleCode(int droleCode) {
        this.droleCode = droleCode;
        return this;
    }

    public UserRoleC setFroleCode(int froleCode) {
        this.froleCode = froleCode;
        return this;
    }

    public UserRoleC setStatCode(int statCode) {
        this.statCode = statCode;
        return this;
    }
    
    public UserRoleC setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }
    public UserRoleC setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, getClass());
    }

    @Override
    public Jsonable fromJson(String json) {
        return new Gson().fromJson(json, getClass());
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}
