/**
 * FuncRoleFuncsDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午1:59:24
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;

/**
 * <roleCode, funcCode>
 * @author leo
 */
@DAO
public interface FuncRoleFuncsDAO {
    /**
     * get list of func codes which are assigned to according func-role-code
     * @param roleCode
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT \"funcCode\" from " + TableName.FuncRoleFuncs + " WHERE \"roleCode\" = :roleCode ORDER BY \"funcCode\"")
    public List<Integer> getFuncs(@SQLParam("roleCode") int roleCode) throws SQLException, DataAccessException;;

    /**
     * 添加一个功能到指定的role
     * 
     * @param roleCode
     * @param funcCode
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.FuncRoleFuncs
            + "(\"roleCode\", \"funcCode\")" + " VALUES (:roleCode,:funcCode)")
    public boolean addFuncToRole(@SQLParam("roleCode") int roleCode, @SQLParam("funcCode") int funcCode) throws SQLException, DataAccessException;;

    /**
     * 删除角色中的一个功能
     * @return
     */
    @SQL("DELETE FROM " + TableName.FuncRoleFuncs + " WHERE \"funcCode\" = :funcCode AND \"roleCode\" = :roleCode")
    public boolean delete(@SQLParam("roleCode") int roleCode, @SQLParam("funcCode") int funcCode) throws SQLException, DataAccessException;

    /**
     * 删除角色对应的所有功能
     * @return
     */
    @SQL("DELETE FROM " + TableName.FuncRoleFuncs + " WHERE \"roleCode\" = :roleCode")
    public boolean deleteAll(@SQLParam("roleCode") int roleCode) throws SQLException, DataAccessException;

    /**
     * 清除表中数据，慎用
     * @return
     */
    @SQL("DELETE FROM " + TableName.FuncRoleFuncs)
    public boolean clear() throws SQLException, DataAccessException;
}
