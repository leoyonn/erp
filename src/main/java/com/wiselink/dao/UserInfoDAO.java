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
import com.wiselink.model.user.UserInfo;
import com.wiselink.model.user.UserPass;

/**
 * @author leo
 */
@DAO
public interface UserInfoDAO {
    String KEYS_NO_PASS = 
            " \"id\", \"account\", \"name\","
            + "\"avatar\", \"email\", \"phone\", \"tel\","
            + "\"desc\", \"province\", \"city\","
            + "\"createTime\", \"creatorId\", \"updateTime\", \"operId\"";

    String KEYS =  "\"password\", " + KEYS_NO_PASS;

    String VALUES_NO_PASS = " :id,:account,:name,"
            + ":avatar,:email,:phone,:tel,"
            + ":desc,:province,:city,"
            + "sysdate,:creatorId,sysdate,:operId";

    String VALUES = ":password," + VALUES_NO_PASS;

    String KVS_NO_PASS =
            " \"account\"=:account, \"name\"=:name, "
            + "\"avatar\"=:avatar, \"email\"=:email, \"phone\"=:phone, \"tel\"=:tel,"
            + "\"desc\"=:desc, \"province\"=:province, \"city\"=:city,"
            + "\"updateTime\"=sysdate, \"operId\"=:operId";

    String KVS = "\"password\"=:password, " + KVS_NO_PASS;

    /**
     * add an user into database
     * @param user
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.UserInfo + "(" + KEYS + ")" + " VALUES(" + VALUES + ")")
    public boolean add(@SQLParam("id") String id,
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
            @SQLParam("creatorId") String creatorId,
            @SQLParam("operId") String operId) throws SQLException, DataAccessException;

    /**
     * 更新一个用户的基本信息
     * @param id
     * @param account
     * @param name
     * @param avatar
     * @param email
     * @param phone
     * @param tel
     * @param desc
     * @param province
     * @param city
     * @param creatorId
     * @param operId
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("UPDATE " + TableName.UserInfo + " SET " + KVS_NO_PASS + " WHERE \"id\"=:id")
    public boolean update(@SQLParam("id") String id,
            @SQLParam("account") String account,
            @SQLParam("name") String name,
            @SQLParam("avatar") String avatar,
            @SQLParam("email") String email,
            @SQLParam("phone") String phone,
            @SQLParam("tel") String tel,
            @SQLParam("desc") String desc,
            @SQLParam("province") String province,
            @SQLParam("city") String city,
            @SQLParam("creatorId") String creatorId,
            @SQLParam("operId") String operId) throws SQLException, DataAccessException;

    /**
     * @param id
     * @param oldpass
     * @param newpass
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("UPDATE " + TableName.UserInfo + " SET " + "\"password\"=:newpass"
            + " WHERE \"id\"=:id and \"password\"=:oldpass")
    public boolean updatePasswordById(@SQLParam("id") String id,
            @SQLParam("oldpass") String oldpass, @SQLParam("newpass") String newpass) throws SQLException, DataAccessException;

    /**
     * @param account
     * @param password
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("UPDATE " + TableName.UserInfo + " SET " + "\"password\"=:newpass"
            + " WHERE \"account\"=:account and \"password\"=:oldpass")
    public boolean updatePasswordByAccount(@SQLParam("account") String account,
            @SQLParam("oldpass") String oldpass, @SQLParam("newpass") String newpass) throws SQLException, DataAccessException;

    /**
     * get password of an user (md5)
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT \"id\", \"account\", \"password\" FROM " + TableName.UserInfo + " WHERE \"id\" = :id")
    public UserPass getPasswordById(@SQLParam("id") String userId) throws SQLException, DataAccessException;

    /**
     * get password of an user (md5)
     * @param account
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT  \"id\", \"account\", \"password\" FROM " + TableName.UserInfo + " WHERE \"account\" = :account")
    public UserPass getPasswordByAccount(@SQLParam("account") String account) throws SQLException, DataAccessException;

    /**
     * get a user from account
     * @param account
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT " + KEYS_NO_PASS + " FROM " + TableName.UserInfo + " WHERE \"account\" = :account")
    public UserInfo getUserByAccount(@SQLParam("account") String account) throws SQLException, DataAccessException;

    /**
     * get a user from id
     *
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT " + KEYS_NO_PASS + " FROM " + TableName.UserInfo + " WHERE \"id\" = :id")
    public UserInfo getUserById(@SQLParam("id") String userId) throws SQLException, DataAccessException;

    /**
     * get users from id
     * @param userIds
     * @return
     */
    @SQL("SELECT " + KEYS_NO_PASS + " FROM " + TableName.UserInfo + " WHERE \"id\" IN (:ids) ORDER BY \"id\"")
    public List<UserInfo> getUsersById(@SQLParam("ids") List<String> userIds) throws SQLException, DataAccessException;

    /**
     * 获取所有的用户，调试使用
     * @param userIds
     * @return
     */
    @SQL("SELECT " + KEYS_NO_PASS + " FROM " + TableName.UserInfo + " ORDER BY \"id\"")
    public List<UserInfo> all() throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.UserInfo + " WHERE \"id\" = :id")
    public boolean delete(@SQLParam("id") String id) throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.UserInfo)
    public int clear() throws SQLException, DataAccessException;
}