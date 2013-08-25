/**
 * FuncRoleDAO.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午5:03:58
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.management.relation.Role;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * @see resources/sql/func_role_info.sql
 * @author leo
 */
@DAO
public interface FuncRoleInfoDAO {
    String KEYS = "\"name\", \"desc\", \"corpId\", \"deptId\", \"creatorId\", \"createTime\", \"updateTime\"";

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
    @SQL("INSERT INTO " + TableName.FuncRoleInfo + "(" + KEYS + ")"
            + " VALUES (:name,:desc,:corpId,:deptId,:creatorId,sysdate,sysdate)")
    public boolean add(@SQLParam("name") String name, @SQLParam("desc") String desc, @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId,
            @SQLParam("creatorId") String creatorId) throws SQLException, DataAccessException;

    @SQL("INSERT INTO " + TableName.FuncRoleInfo + "(" + KEYS + ")"
            + " VALUES (:r.name,:r.desc,:r.corpId,:r.deptId,:r.creatorId,sysdate,sysdate)")
    public boolean add(@SQLParam("r") FuncRoleInfo role) throws SQLException, DataAccessException;

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

    @SQL("UPDATE " + TableName.FuncRoleInfo
            + " SET \"name\"=:r.name, \"desc\"=:r.desc, \"corpId\"=:r.corpId, \"deptId\"=:r.deptId, \"updateTime\"=sysdate"
            + " WHERE \"code\" = :r.code") // TODO creator验证：AND \"creatorId\" = :r.creatorId"
    public boolean update(@SQLParam("r") FuncRoleInfo role) throws SQLException, DataAccessException;

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
    @SQL("SELECT " + KEYS + " FROM " + TableName.FuncRoleInfo + "WHERE \"corpId\" = :corpId ORDER BY \"code\"")
    public List<FuncRoleInfo> all(@SQLParam("corpId") String corpId) throws SQLException, DataAccessException;

    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " ORDER BY \"code\"")
    public List<FuncRoleInfo> all() throws SQLException, DataAccessException;

    @SQL("SELECT COUNT(\"code\") FROM " + TableName.FuncRoleInfo)
    public int count() throws SQLException, DataAccessException;

    /**
     * list all func-roles in :codes sorted by {@link Role#code}  
     * 
     * @param from
     * @param num
     * @return
     */
    @SQL("SELECT * FROM " + TableName.FuncRoleInfo + " WHERE \"code\" IN (:codes) ORDER BY \"code\"")
    public List<FuncRoleInfo> list(@SQLParam("codes") Collection<Integer>codes) throws SQLException, DataAccessException;

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