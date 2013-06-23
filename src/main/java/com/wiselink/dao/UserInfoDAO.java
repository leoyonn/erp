/**
 * UserDao.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 8:49:10 PM
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;
import com.wiselink.model.user.User;
import com.wiselink.model.user.UserCard;

/**
 * @author leo
 */
@DAO
public interface UserInfoDAO {
    String USER_INFO_KEYS =
            " (\"id\", \"account\", \"name\", \"password\","
            + "\"avatar\", \"email\", \"phone\", \"tel\","
            + "\"desc\", \"province\", \"city\","
            + "\"opUserId\", \"updateTime\", \"createTime\")";

    String USER_INFO_VALUES = " VALUES (:id,:account,:name,:password,"
            + ":avatar,:email,:phone,:tel,"
            + ":desc,:province,:city,"
            + ":opUserId,sysdate,sysdate)";

    /**
     * add an user into database
     * @param user
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.UserInfo + USER_INFO_KEYS + USER_INFO_VALUES)
    public boolean addUser(@SQLParam("id") String id,
            @SQLParam("account") String account,
            @SQLParam("name") String name,
            @SQLParam("password") String password,
            @SQLParam("avatar") String avatar,
            @SQLParam("email") String email,
            @SQLParam("phone") String phone,
            @SQLParam("tel") String tel,
            @SQLParam("desc") String desc,
            @SQLParam("province") String province,
            @SQLParam("city") String city,
            @SQLParam("opUserId") String opUserId) throws SQLException, DataAccessException;

    /**
     * get password of an user (md5)
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT \"password\" FROM " + TableName.UserInfo + " WHERE \"id\" = :id")
    public String getPassword(@SQLParam("id") String userId) throws SQLException, DataAccessException;

    /**
     * get a user from account
     * @param account
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.UserInfo + " WHERE \"account\" = :account")
    public User getUserByAccount(@SQLParam("account") String account) throws SQLException, DataAccessException;

    /**
     * get a user from id
     *
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.UserInfo + " WHERE \"id\" = :id")
    public User getUserById(@SQLParam("id") String userId) throws SQLException, DataAccessException;

    /**
     * get users from id
     * @param userIds
     * @return
     */
    @SQL("SELECT * FROM " + TableName.UserInfo + " WHERE \"id\" IN (:ids)")
    public List<UserCard> getUserCardsById(@SQLParam("ids") List<String> userIds) throws SQLException, DataAccessException;
}