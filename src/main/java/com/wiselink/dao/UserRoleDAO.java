/**
 * UserRoleDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-22 上午10:24:43
 */
package com.wiselink.dao;

import java.sql.SQLException;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;
import com.wiselink.model.user.UserRole;

/**
 * 
 * @author leo
 */
@DAO
public interface UserRoleDAO {

    String USER_ROLE_KEYS ="(\"id\",\"catCode\",\"posCode\"," +
    		"\"froleCode\",\"droleCode\",\"statCode\",\"corpId\",\"deptId\")";
    String USER_ROLE_VALUES = " VALUES (:id,:catCode,:posCode,:froleCode,"
            + ":droleCode,:statCode,:corpId,:deptId)";

    /**
     * add an user into database
     * @param user
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.UserRole + USER_ROLE_KEYS + USER_ROLE_VALUES)
    public boolean addUser(@SQLParam("id") String userId,
            @SQLParam("catCode") String catCode,
            @SQLParam("posCode") String posCode,
            @SQLParam("froleCode") String froleCode,
            @SQLParam("droleCode") String droleCode,
            @SQLParam("statCode") String statCode,
            @SQLParam("corpId") String corpId,
            @SQLParam("deptId") String deptId) throws SQLException, DataAccessException;

    /**
     * get password of an user (md5)
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.UserRole + " WHERE \"id\" = :id")
    public UserRole find(@SQLParam("id") String userId) throws SQLException, DataAccessException;

}
