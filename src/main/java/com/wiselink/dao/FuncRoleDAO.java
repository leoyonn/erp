/**
 * FuncRoleDAO.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午5:03:58
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.model.FuncRole;

/**
 * @author leo
 */
@DAO
public interface FuncRoleDAO {
    String TABLE_NAME_FROLE = "PICC.\"func_role\"";

    /**
     * add an func-role into database
     * @param func-role
     * @return
     * @throws SQLException
     */
    @SQL("INSERT INTO " + TABLE_NAME_FROLE + "(\"name\", \"desc\")" + " VALUES (:name,:desc)")
    public boolean add(@SQLParam("name") String name, @SQLParam("desc") String desc);

    /**
     * find func-role using id
     * @param userId
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TABLE_NAME_FROLE + " WHERE \"code\" = ':code'")
    public FuncRole find(@SQLParam("code") int code) throws SQLException;
    
    @SQL("SELECT * FROM " + TABLE_NAME_FROLE + " WHERE \"name\" = ':name'")
    public FuncRole findByName(@SQLParam("name") String name) throws SQLException;

    /**
     * list all #num func-roles sorted by {@link Role#code} from #from 
     * 
     * @param from
     * @param num
     * @return
     */
    // config:mysql @SQL("SELECT * FROM " + TABLE_NAME_FROLE + " WHERE \"code\" >= ':from' LIMIT 0,:num")
    @SQL("SELECT * FROM " + TABLE_NAME_FROLE + " WHERE \"code\" >= ':from' AND ROWNUM <=:num")
    public List<FuncRole> list(@SQLParam("from") int from, @SQLParam("num") int num);
}