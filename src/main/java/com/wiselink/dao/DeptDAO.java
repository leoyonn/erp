/**
 * DeptDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:56:46
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
import com.wiselink.model.org.Dept;

/**
 * @author leo
 */
@DAO
public interface DeptDAO {
    String KEYS =" (\"id\", \"name\", \"desc\", \"deptType\", \"corpId\")";
    String VALUES = " VALUES (:id, :name, :desc, :deptType, :corpId)";
    String KVS =" \"name\"=:name, \"desc\"=:desc, \"deptType\"=:deptType, \"corpId\"=:corpId";

    /**
     * 添加一个新的部门
     * @param id
     * @param name
     * @param desc
     * @param corpId
     * @param deptType
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.Dept + KEYS + VALUES)
    public boolean addDept(@SQLParam("id") String id,
            @SQLParam("name") String name,
            @SQLParam("desc") String desc,
            @SQLParam("deptType") String deptType,
            @SQLParam("corpId") String corpId) throws SQLException, DataAccessException;

    /**
     * 更新一个部门的信息
     * @param id
     * @param name
     * @param desc
     * @param deptType
     * @param corpId
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("UPDATE " + TableName.Dept + " SET " + KVS + " WHERE \"id\" = :id")
    public boolean updateDept(@SQLParam("id") String id,
            @SQLParam("name") String name,
            @SQLParam("desc") String desc,
            @SQLParam("deptType") String deptType,
            @SQLParam("corpId") String corpId) throws SQLException, DataAccessException;

    /**
     * 获取一个部门信息
     * @param id
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.Dept + " WHERE \"id\" = :id")
    public Dept find(@SQLParam("id") String id) throws SQLException, DataAccessException;

    @SQL("SELECT COUNT(\"id\") FROM " + TableName.Dept)
    public int count() throws SQLException, DataAccessException;

    @SQL("SELECT \"id\", \"name\", \"desc\", \"deptType\", \"corpId\" "
            + "FROM (SELECT A.*, ROWNUM N FROM (SELECT * FROM PICC.\"dept\" WHERE \"name\" LIKE :nameLike) A "
            + "WHERE ROWNUM <= :to) WHERE N >= :from")
    public List<Dept> queryByName(@SQLParam("nameLike") String nameLike, 
            @SQLParam("from") int from, @SQLParam("to") int to) throws SQLException, DataAccessException;

    @SQL("SELECT COUNT(\"id\") FROM " + TableName.Dept + " WHERE \"name\" LIKE :nameLike ORDER BY \"id\"")
    public int countByName(@SQLParam("nameLike") String nameLike) throws SQLException, DataAccessException;

    @SQL("SELECT MAX(\"id\") FROM " + TableName.Dept + " WHERE \"corpId\"=:corpId ORDER BY \"id\"")
    public String maxDeptId(@SQLParam("corpId") String corpId) throws SQLException, DataAccessException;
    /**
     * list all depts in :ids  
     * 
     * @param from
     * @param num
     * @return
     */
    @SQL("SELECT * FROM " + TableName.Dept + " WHERE \"id\" IN (:ids) ORDER BY \"id\"")
    public List<Dept> list(@SQLParam("ids") Collection<String> ids) throws SQLException, DataAccessException;

    /**
     * 获取一个公司所有的部门
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.Dept + " WHERE \"corpId\"=:corpId ORDER BY \"id\"")
    public List<Dept> all(@SQLParam("corpId") String corpId) throws SQLException, DataAccessException;

    /**
     * 获取所有部门列表
     * @param ids
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.Dept + " ORDER BY \"id\"")
    public List<Dept> all() throws SQLException, DataAccessException;

    /**
     * 删除
     * @param id
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("DELETE FROM " + TableName.Dept + "WHERE \"id\"=:id")
    public boolean delete(@SQLParam("id") String id) throws SQLException, DataAccessException;
    
    @SQL("DELETE FROM " + TableName.Dept)
    public int clear() throws SQLException, DataAccessException;
}
