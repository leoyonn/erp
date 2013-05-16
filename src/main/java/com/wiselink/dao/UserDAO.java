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
    @SQL("INSERT INTO user "
            + "(id,account,name,password,"
            + "avatar,email,phone,tel,desc,corpId,deptId,province,city,"
            + "updateTime,createTime,opUserId,"
            + "cat,type,drole,frole,stat)"
            + " VALUES "
            + "(:u.id,:u.account,:u.name,:u.password,"
            + ":u.avatar,:u.email,:u.phone,:u.tel,:u.desc,:u.corpId,:u.deptId,:u.province,:u.city,"
            + ":u.updateTime,:u.createTime,:u.opUserId,"
            + ":u.cat,:u.type,:u.drole,:u.frole,:u.stat)")
    public boolean addUser(@SQLParam("u") User user) throws SQLException;

    @SQL("SELECT password FROM user WHERE id = :userId")
    public String getPassword(@SQLParam("userId") long userId) throws SQLException;

    @SQL("SELECT * FROM user WHERE account=:account")
    public User getUser(@SQLParam("account") String account)throws SQLException;

    @SQL("SELECT * FROM user WHERE id=:id")
    public User getUser(@SQLParam("id") long userId)throws SQLException;
}