/**
 * DataRoleUsersDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午1:46:07
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
 * <roleCode, userId>
 * @author leo
 */
@DAO
public interface DataRoleUsersDAO  {
    /**
     * get list of users' codes which are assigned to according data-role-code
     * @param roleCode
     * @throws SQLException, DataAccessException
     * @return
     */
    @SQL("SELECT \"userId\" from " + TableName.DataRoleUsers + " WHERE \"roleCode\" = :roleCode ORDER BY \"userId\"")
    public List<String> getUsers(@SQLParam("roleCode") int DataRoleCode) throws SQLException, DataAccessException;

    /**
     * 添加一个用户到指定的role
     * @param roleCode
     * @param userId
     * @throws SQLException, DataAccessException
     * @return
     */
    @SQL("INSERT INTO " + TableName.DataRoleUsers + "(\"roleCode\", \"userId\")" + " VALUES (:roleCode,:userId)")
    public boolean addUserToRole(@SQLParam("roleCode") int roleCode, @SQLParam("userId") String userId) throws SQLException, DataAccessException;

    /**
     * 删除角色中的一个用户
     * @param roleCode
     * @param userId
     * @return
     */
    @SQL("DELETE FROM " + TableName.DataRoleUsers + " WHERE \"userId\" = :userId AND \"roleCode\" = :roleCode")
    public boolean delete(@SQLParam("roleCode") int roleCode, @SQLParam("userId") String userId) throws SQLException, DataAccessException;

    /**
     * 删除角色对应的所有用户
     * @return
     */
    @SQL("DELETE FROM " + TableName.DataRoleUsers + " WHERE \"roleCode\" = :roleCode")
    public boolean deleteAll(@SQLParam("roleCode") int roleCode) throws SQLException, DataAccessException;

    /**
     * 清除表中数据，慎用
     * @return
     */
    @SQL("DELETE FROM " + TableName.DataRoleUsers)
    public boolean clear() throws SQLException, DataAccessException;
}
