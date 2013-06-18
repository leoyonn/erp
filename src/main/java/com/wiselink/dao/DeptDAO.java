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
 * @author leo
 */
public interface DeptDAO {
    String KEYS =" (\"id\", \"name\", \"deptType\", \"corpId\")";
    String VALUES = " VALUES (:id, :name, :tel, :contact)";

    /**
     * 添加一个新的部门
     * @param id
     * @param name
     * @param corpId
     * @param deptType
     * @return
     * @throws SQLException
     */
    @SQL("INSERT INTO " + TableName.Dept + KEYS + VALUES)
    public boolean addDept(@SQLParam("id") String id,
            @SQLParam("name") String name,
            @SQLParam("corpId") String corpId,
            @SQLParam("deptType") String deptType) throws SQLException;

    /**
     * 获取一个部门信息
     * @param id
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TableName.Dept + " WHERE \"id\" = :id")
    public Dept find(@SQLParam("id") String id) throws SQLException;
}
