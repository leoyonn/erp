/**
 * UserDao.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 8:49:10 PM
 */
package com.wiselink.dao;

import java.sql.SQLException;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.model.User;

/**
 * @author leo
 */
@DAO
public interface UserDAO {
    String TABLE_NAME_USER = "PICC.\"user\"";
    String TABLE_NAME_USER_ROLE = "PICC.\"user_role\"";
    String USER_KEYS =
            " (\"id\", \"account\", \"name\", \"password\","
            + "\"avatar\", \"email\", \"phone\", \"tel\","
            + "\"desc\", \"corpId\", \"deptId\","
            + "\"province\", \"city\","
            + "\"opUserId\", \"updateTime\", \"createTime\")";

    String USER_VALUES = " VALUES "
//            + "(':user.id', ':user.account', :user.name, 'testpass2',"
//            + "'http://picc.com/1.jpg','testuser@picc.com', '13811818888', '95518',"
//            + "'这是个测试用户，testuser', 'CORP01', 'DEPT01',"
//            + "'河北省', '石家庄市',"
//            + "'0', sysdate, sysdate)";

            + "(:id,:account,:name,:password,"
            + ":avatar,:email,:phone,:tel,"
            + ":desc,:corpId,:deptId,"
            + ":province,:city,"
            + ":opUserId,sysdate,sysdate)";

    /**
     * add an user into database
     * @param user
     * @return
     * @throws SQLException
     */
    @SQL("INSERT INTO " + TABLE_NAME_USER + USER_KEYS + USER_VALUES)
    public boolean addUser(@SQLParam("id") String id,
            @SQLParam("account") String account,
            @SQLParam("name") String name,
            @SQLParam("password") String password,
            @SQLParam("avatar") String avatar,
            @SQLParam("email") String email,
            @SQLParam("phone") String phone,
            @SQLParam("tel") String tel,
            @SQLParam("desc") String desc,
            @SQLParam("corpId") String corpId,
            @SQLParam("deptId") String deptId,
            @SQLParam("province") String province,
            @SQLParam("city") String city,
            @SQLParam("opUserId") String opUserId) throws SQLException;

    /**
     * get password of an user (md5)
     * @param userId
     * @return
     * @throws SQLException
     */
    @SQL("SELECT \"password\" FROM " + TABLE_NAME_USER + " WHERE \"id\" = :id")
    public String getPassword(@SQLParam("id") String userId) throws SQLException;

    /**
     * get a user from account
     * @param account
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TABLE_NAME_USER + " WHERE \"account\" = :account")
    public User getUserByAccount(@SQLParam("account") String account)throws SQLException;

    /**
     * get a user from id
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TABLE_NAME_USER + " WHERE \"id\" = :id")
    public User getUserById(@SQLParam("id") String userId)throws SQLException;
}