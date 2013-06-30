/**
 * UserRole.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-17 下午1:46:02
 */
package com.wiselink.model.user;

import net.sf.json.JSONObject;

import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * 用户角色属性，包括类别、状态、功能角色、数据角色
 * @author leo
 */
public class UserRole implements Jsonable {
    public String id;
    public UserCategory cat;
    public UserStatus stat;
    public Position pos;
    public FuncRoleInfo frole;
    public DataRoleInfo drole;
    public Corp corp;
    public Dept dept;

    public UserRole() {}

    public UserRole(String id) {
        this.id = id;
    }

    public UserRole setId(String id) {
        this.id = id;
        return this;
    }

    public UserRole setCat(UserCategory cat) {
        this.cat = cat;
        return this;
    }

    public UserRole setStat(UserStatus stat) {
        this.stat = stat;
        return this;
    }

    public UserRole setPos(Position pos) {
        this.pos = pos;
        return this;
    }

    public UserRole setFrole(FuncRoleInfo frole) {
        this.frole = frole;
        return this;
    }

    public UserRole setDrole(DataRoleInfo drole) {
        this.drole = drole;
        return this;
    }

    public UserRole setCorp(Corp corp) {
        this.corp = corp;
        return this;
    }

    public UserRole setDept(Dept dept) {
        this.dept = dept;
        return this;
    }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        if (cat != null) {
            json.put("cat", cat.json);
        }
        if (stat != null) {
            json.put("stat", stat.json);
        }
        if (pos != null) {
            json.put("pos", pos.toJson());
        }
        if (frole != null) {
            json.put("frole", frole.toJson());
        }
        if (drole != null) {
            json.put("drole", drole.toJson());
        }
        if (corp != null) {
            json.put("corp", corp.toJson());
        }
        if (dept != null) {
            json.put("dept", dept.toJson());
        }
        return json.toString();
    }

    @Override
    public Jsonable fromJson(String json) {
        JSONObject j = JSONObject.fromObject(json);
        this.id = j.getString("id");
        this.cat = (UserCategory) UserCategory.fromJsonStatic(j.getString("cat"));
        this.stat = (UserStatus) UserStatus.fromJsonStatic(j.getString("stat"));
        this.pos = (Position) new Position().fromJson(j.getString("pos"));
        this.frole = (FuncRoleInfo) new FuncRoleInfo().fromJson(j.getString("frole"));
        this.drole = (DataRoleInfo) new DataRoleInfo().fromJson(j.getString("drole"));
        this.corp = (Corp) new Corp().fromJson(j.getString("corp"));
        this.dept = (Dept) new Dept().fromJson(j.getString("dept"));
        return this;
    }
    
    @Override
    public String toString() {
        return toJson();
    }
}
