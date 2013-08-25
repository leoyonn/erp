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
    String KEYS =" (\"id\", \"type\", \"name\", \"desc\", \"address\", \"tel\", \"contact\", \"superCorpId\")";
    String VALUES = " VALUES (:id, :type, :name, :desc, :address, :tel, :contact, :superCorpId)";
    String VALUES_OBJ = " VALUES (:c.id, :c.type, :c.name, :c.desc, :c.address, :c.tel, :c.contact, :c.superCorpId)";
    String KVS = " \"type\"=:type, \"name\"=:name, \"desc\"=:desc, \"address\"=:address, "
            + "\"tel\"=:tel, \"contact\"=:contact, \"superCorpId\"=:superCorpId";
    String KVS_OBJ = " \"type\"=:c.type, \"name\"=:c.name, \"desc\"=:c.desc, \"address\"=:c.address, "
            + "\"tel\"=:c.tel, \"contact\"=:c.contact, \"superCorpId\"=:c.superCorpId";

    /**
     * add an corp into database
     * @param corp
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.Corp + KEYS + VALUES)
    public boolean addCorp(@SQLParam("id") String id,
            @SQLParam("type") String type,
            @SQLParam("name") String name,
            @SQLParam("desc") String desc,
            @SQLParam("address") String address,
            @SQLParam("tel") String tel,
            @SQLParam("contact") String contact,
            @SQLParam("superCorpId") String superCorpId) throws SQLException, DataAccessException;

    @SQL("INSERT INTO " + TableName.Corp + KEYS + VALUES_OBJ)
    public boolean addCorp(@SQLParam("c") Corp corp) throws SQLException, DataAccessException;

    /**
     * update an corp into database
     * @param corp
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("UPDATE " + TableName.Corp + " SET " + KVS + " WHERE \"id\"=:id")
    public boolean updateCorp(@SQLParam("id") String id,
            @SQLParam("type") String type,
            @SQLParam("name") String name,
            @SQLParam("desc") String desc,
            @SQLParam("address") String address,
            @SQLParam("tel") String tel,
            @SQLParam("contact") String contact,
            @SQLParam("superCorpId") String superCorpId) throws SQLException, DataAccessException;

    @SQL("UPDATE " + TableName.Corp + " SET " + KVS_OBJ + " WHERE \"id\"=:c.id")
    public boolean updateCorp(@SQLParam("c") Corp corp) throws SQLException, DataAccessException;

    @SQL("SELECT * FROM " + TableName.Corp + " WHERE \"id\" = :id")
    public Corp find(@SQLParam("id") String id) throws SQLException, DataAccessException;

    @SQL("SELECT * FROM " + TableName.Corp + " WHERE \"name\" = :name")
    public Corp findByName(@SQLParam("name") String name) throws SQLException, DataAccessException;

    /**
     * list all corps in :ids  
     * 
     * @param from
     * @param num
     * @return
     */
    @SQL("SELECT * FROM " + TableName.Corp + " WHERE \"id\" IN (:ids) ORDER BY \"id\"")
    public List<Corp> list(@SQLParam("ids") Collection<String> ids) throws SQLException, DataAccessException;

    /**
     * 获取所有的公司列表
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.Corp + " ORDER BY \"id\"")
    public List<Corp> all() throws SQLException, DataAccessException;

    @SQL("SELECT * FROM " + TableName.Corp + " WHERE \"superCorpId\" = :superCorpId ORDER BY \"id\"")
    public List<Corp> subCorps(@SQLParam("superCorpId") String superCorpId) throws SQLException, DataAccessException;


    /**
     * 删除一个公司
     * 
     * @param id
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("DELETE FROM " + TableName.Corp + "WHERE \"id\"=:id")
    public boolean delete(@SQLParam("id") String id) throws SQLException, DataAccessException;

    /**
     * 删除一组公司
     * 
     * @param ids
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("DELETE FROM " + TableName.Corp + "WHERE \"id\" IN :ids")
    public int delete(@SQLParam("ids") List<String> ids) throws SQLException, DataAccessException;
}
