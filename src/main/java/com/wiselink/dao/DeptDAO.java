/**
 * DeptDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:56:46
 */
package com.wiselink.dao;

import java.sql.SQLException;

import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.base.TableName;
import com.wiselink.model.org.Dept;

/**
 * 
 * @author leo
 */
public interface DeptDAO {
    String KEYS =" (\"id\", \"name\", \"desc\", \"tel\", \"contact\")";
    String VALUES = " VALUES (:id,:name,:password,:tel,:contact)";

    /**
     * add an user into database
     * @param user
     * @return
     * @throws SQLException
     */
    @SQL("INSERT INTO " + TableName.Dept + KEYS + VALUES)
    public boolean addCorp(@SQLParam("id") String id,
            @SQLParam("name") String name,
            @SQLParam("desc") String desc,
            @SQLParam("address") String address,
            @SQLParam("tel") String tel,
            @SQLParam("contact") String contact) throws SQLException;

    /**
     * get password of an user (md5)
     * @param userId
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TableName.Dept + " WHERE \"id\" = :id")
    public Dept find(@SQLParam("id") String id) throws SQLException;
}
