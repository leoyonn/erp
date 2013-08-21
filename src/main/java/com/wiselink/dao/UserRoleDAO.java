/**
 * UserRoleDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-22 上午10:24:43
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;
import com.wiselink.model.user.UserRoleC;

/**
 * 
 * @author leo
 */
@DAO
public interface UserRoleDAO {

    String USER_ROLE_KEYS = "(\"id\",\"catCode\",\"posCode\"," +
            "\"froleCode\",\"droleCode\",\"statCode\",\"corpId\",\"deptId\")";

    String USER_ROLE_VALUES = " VALUES (:id,:catCode,:posCode,:froleCode,"
            + ":droleCode,:statCode,:corpId,:deptId)";

    String USER_ROLE_UP_KVS = "\"catCode\"=:catCode,\"posCode\"=:posCode," +
            "\"froleCode\"=:froleCode,\"droleCode\"=:droleCode,\"statCode\"=:statCode,\"corpId\"=:corpId,\"deptId\"=:deptId";

    /**
     * add an user into database
     * @param user
     * @return
     * @throws SQLException, DataAccessException
     */
    @Deprecated
    @SQL("INSERT INTO " + TableName.UserRole + USER_ROLE_KEYS + USER_ROLE_VALUES)
    public boolean addUserRole(@SQLParam("id") String userId,
            @SQLParam("catCode") int catCode,
            @SQLParam("posCode") int posCode,
            @SQLParam("froleCode") int froleCode,
            @SQLParam("droleCode") int droleCode,
            @SQLParam("statCode") int statCode,
            @SQLParam("corpId") String corpId,
            @SQLParam("deptId") String deptId) throws SQLException, DataAccessException;

    @SQL("UPDATE " + TableName.UserRole + " SET " + USER_ROLE_UP_KVS + " WHERE \"id\"=:id")
    public boolean updateUserRole(@SQLParam("id") String userId,
            @SQLParam("catCode") int catCode,
            @SQLParam("posCode") int posCode,
            @SQLParam("froleCode") int froleCode,
            @SQLParam("droleCode") int droleCode,
            @SQLParam("statCode") int statCode,
            @SQLParam("corpId") String corpId,
            @SQLParam("deptId") String deptId) throws SQLException, DataAccessException;

    /**
     * get password of an user (md5)
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @Deprecated
    @SQL("SELECT * FROM " + TableName.UserRole + " WHERE \"id\" = :id")
    public UserRoleC find(@SQLParam("id") String userId) throws SQLException, DataAccessException;

    /**
     * 获取指定id列表中的所有用户角色信息
     * @param userIds
     * @return
     */
    @Deprecated
    @SQL("SELECT * FROM " + TableName.UserRole + " WHERE \"id\" IN (:ids) ORDER BY \"id\"")
    public List<UserRoleC> getRoles(@SQLParam("ids") Collection<String> userIds) throws SQLException, DataAccessException;

    /**
     * 获取所有用户，近
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @Deprecated
    @SQL("SELECT * FROM " + TableName.UserRole + " ORDER BY \"id\"")
    public List<UserRoleC> all() throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @Deprecated
    @SQL("DELETE FROM " + TableName.UserRole + " WHERE \"id\" = :id")
    public boolean delete(@SQLParam("id") String id) throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @Deprecated
    @SQL("DELETE FROM " + TableName.UserRole)
    public int clear() throws SQLException, DataAccessException;
}
