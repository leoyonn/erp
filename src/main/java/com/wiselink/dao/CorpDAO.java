/**
 * CorpDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-11 下午5:51:13
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
import com.wiselink.model.org.Corp;

/**
 * @author leo
 */
@DAO
public interface CorpDAO {
    String KEYS =" (\"id\", \"name\", \"desc\", \"address\", \"tel\", \"contact\")";
    String VALUES = " VALUES (:id, :name, :desc, :address, :tel, :contact)";

    /**
     * add an corp into database
     * @param corp
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.Corp + KEYS + VALUES)
    public boolean addCorp(@SQLParam("id") String id,
            @SQLParam("name") String name,
            @SQLParam("desc") String desc,
            @SQLParam("address") String address,
            @SQLParam("tel") String tel,
            @SQLParam("contact") String contact) throws SQLException, DataAccessException;

    /**
     * get password of an user (md5)
     * @param userId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.Corp + " WHERE \"id\" = :id")
    public Corp find(@SQLParam("id") String id) throws SQLException, DataAccessException;

    /**
     * list all corps in :ids  
     * 
     * @param from
     * @param num
     * @return
     */
    @SQL("SELECT * FROM " + TableName.Corp + " WHERE \"id\" IN (:ids) ORDER BY \"id\"")
    public List<Corp> list(@SQLParam("ids") Collection<String> ids) throws SQLException, DataAccessException;
}
