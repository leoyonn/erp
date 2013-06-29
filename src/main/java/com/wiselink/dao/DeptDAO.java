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
    String KEYS =" (\"id\", \"name\", \"deptType\", \"corpId\")";
    String VALUES = " VALUES (:id, :name, :deptType, :corpId)";

    /**
     * 添加一个新的部门
     * @param id
     * @param name
     * @param corpId
     * @param deptType
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.Dept + KEYS + VALUES)
    public boolean addDept(@SQLParam("id") String id,
            @SQLParam("name") String name,
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

    /**
     * list all depts in :ids  
     * 
     * @param from
     * @param num
     * @return
     */
    @SQL("SELECT * FROM " + TableName.Dept + " WHERE \"id\" IN (:ids) ORDER BY \"id\"")
    public List<Dept> list(@SQLParam("ids") Collection<String> ids) throws SQLException, DataAccessException;
}
