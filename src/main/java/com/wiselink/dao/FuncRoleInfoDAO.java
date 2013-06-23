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

import org.springframework.dao.DataAccessException;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.base.TableName;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * @see resources/sql/func_role_info.sql
 * @author leo
 */
@DAO
public interface FuncRoleInfoDAO {
    /**
     * 添加一条func-role-info到database
     * <li>code为自增PK字段；
     * <li>name为unique字段；
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     */
    @SQL("INSERT INTO " + TableName.FuncRoleInfo 
            + "(\"name\", \"desc\", \"corpId\", \"deptId\", \"creatorId\", \"createTime\", \"updateTime\")"
            + " VALUES (:name,:desc,:corpId,:deptId,:creatorId,sysdate,sysdate)")
    public boolean add(@SQLParam("name") String name, @SQLParam("desc") String desc, @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId,
            @SQLParam("creatorId") String creatorId) throws SQLException, DataAccessException, DataAccessException;

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
            + " SET \"name\"=:name, \"desc\"=:desc, \"corpId\"=:corpId, \"deptId\"=:deptId, \"updateTime\"=sysdate"
            + " WHERE \"code\" = :code")
    public boolean update(@SQLParam("code") int code, @SQLParam("name") String name, @SQLParam("desc") String desc,
            @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId) throws SQLException, DataAccessException;

    /**
     * find func-role using id
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " WHERE \"code\" = :code")
    public FuncRoleInfo find(@SQLParam("code") int code) throws SQLException, DataAccessException;
    
    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " WHERE \"name\" = :name")
    public FuncRoleInfo findByName(@SQLParam("name") String name) throws SQLException, DataAccessException;

    /**
     * list all #num func-roles sorted by {@link Role#code} from #from 
     * 
     * @param from
     * @param num
     * @return
     */
    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " WHERE \"code\" >= :from AND ROWNUM <=:num ORDER BY \"code\"")
    public List<FuncRoleInfo> list(@SQLParam("from") int from, @SQLParam("num") int num) throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.FuncRoleInfo + " WHERE \"code\" = :code")
    public boolean delete(@SQLParam("code") int code) throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.FuncRoleInfo)
    public int clear() throws SQLException, DataAccessException;
}