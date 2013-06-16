/**
 * FuncRoleDAO.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午5:03:58
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import javax.management.relation.Role;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.base.TableName;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * @author leo
 */
@DAO
public interface FuncRoleInfoDAO {
    /**
     * 添加一条func-role-info到database
     * @param code
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     */
    @SQL("INSERT INTO " + TableName.FuncRoleInfo 
            + "(\"code\", \"name\", \"desc\", \"corpId\", \"deptId\", \"createTime\", \"creator\")"
            + " VALUES (:code,:name,:desc,:corpId,:deptId,systime,:creatorId)")
    public boolean add(@SQLParam("code") int code, @SQLParam("name") String name, @SQLParam("desc") String desc,
            @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId, @SQLParam("creatorId") String creatorId) throws SQLException;

    /**
     * 修改一条func-role-info
     * @param code
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @return
     */
    @SQL("UPDATE " + TableName.FuncRoleInfo
            + " SET (\"name\", \"desc\", \"corpId\", \"deptId\")"
            + " = (:name,:desc,:corpId,:deptId) WHERE \"code\" = :code")
    public boolean modify(@SQLParam("code") int code, @SQLParam("name") String name, @SQLParam("desc") String desc,
            @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId) throws SQLException;

    /**
     * find func-role using id
     * @param userId
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " WHERE \"code\" = ':code'")
    public FuncRoleInfo find(@SQLParam("code") int code) throws SQLException;
    
    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " WHERE \"name\" = ':name'")
    public FuncRoleInfo findByName(@SQLParam("name") String name) throws SQLException;

    /**
     * list all #num func-roles sorted by {@link Role#code} from #from 
     * 
     * @param from
     * @param num
     * @return
     */
    // config:mysql @SQL("SELECT * FROM " + TABLE_NAME_FROLE + " WHERE \"code\" >= ':from' LIMIT 0,:num")
    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " WHERE \"code\" >= ':from' AND ROWNUM <=:num")
    public List<FuncRoleInfo> list(@SQLParam("from") int from, @SQLParam("num") int num) throws SQLException;
}