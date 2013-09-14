/**
 * FuncRoleUsersDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午1:59:08
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;

/**
 * func role users
 * <roleCode, userId>
 * @author leo
 */
@DAO
public interface FuncRoleUsersDAO  {
    /**
     * get list of users' codes which are assigned to according func-role-code
     * @param roleCode
     * @throws SQLException, DataAccessException
     * @return
     */
    @SQL("SELECT \"userId\" from " + TableName.FuncRoleUsers + " WHERE \"roleCode\" = :roleCode ORDER BY \"userId\"")
    public List<String> getUsers(@SQLParam("roleCode") int funcRoleCode) throws SQLException, DataAccessException;

    /**
     * 添加一个用户到指定的role
     * @param roleCode
     * @param useId
     * @throws SQLException, DataAccessException
     * @return
     */
    @SQL("INSERT INTO " + TableName.FuncRoleUsers + "(\"roleCode\", \"userId\")" + " VALUES (:roleCode,:userId)")
    public boolean addUserToRole(@SQLParam("roleCode") int roleCode, @SQLParam("userId") String userId) throws SQLException, DataAccessException;

    /**
     * 删除角色中的一个用户
     * @param roleCode
     * @param userId
     * @return
     */
    @SQL("DELETE FROM " + TableName.FuncRoleUsers + " WHERE \"userId\" = :userId AND \"roleCode\" = :roleCode")
    public boolean delete(@SQLParam("roleCode") int roleCode, @SQLParam("userId") String userId) throws SQLException, DataAccessException;

    /**
     * 删除所有角色对应的用户信息
     * @param userIds
     * @return
     */
    @SQL("DELETE FROM " + TableName.FuncRoleUsers + " WHERE \"userId\" IN (:userIds)")
    public boolean deleteUsers(@SQLParam("userIds") List<String> userIds) throws SQLException, DataAccessException;

    @SQL("DELETE FROM " + TableName.FuncRoleUsers + " WHERE \"userId\" = :userId")
    public boolean deleteUser(@SQLParam("userId") String userId) throws SQLException, DataAccessException;

    /**
     * 删除角色对应的所有用户
     * @return
     */
    @SQL("DELETE FROM " + TableName.FuncRoleUsers + " WHERE \"roleCode\" = :roleCode")
    public boolean deleteAll(@SQLParam("roleCode") int roleCode) throws SQLException, DataAccessException;

    /**
     * 清除表中数据，慎用
     * @return
     */
    @SQL("DELETE FROM " + TableName.FuncRoleUsers)
    public boolean clear() throws SQLException, DataAccessException;
}
