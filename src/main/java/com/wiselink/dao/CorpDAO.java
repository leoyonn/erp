/**
 * CorpDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-11 下午5:51:13
 */
package com.wiselink.dao;

import java.sql.SQLException;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.model.Corp;

/**
 * @author leo
 */
@DAO
public interface CorpDAO {
    String TABLE_NAME_CORP = "PICC.\"corp\"";
    String KEYS =" (\"id\", \"name\", \"desc\", \"tel\", \"contact\")";
    String VALUES = " VALUES (:id,:name,:password,:tel,:contact)";

    /**
     * add an user into database
     * @param user
     * @return
     * @throws SQLException
     */
    @SQL("INSERT INTO " + TABLE_NAME_CORP + KEYS + VALUES)
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
    @SQL("SELECT * FROM " + TABLE_NAME_CORP + " WHERE \"id\" = :id")
    public Corp find(@SQLParam("id") String id) throws SQLException;
}
