/**
 * DataRoleScopesDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午1:45:44
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import com.wiselink.base.TableName;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

/**
 * <roleCode, orgId>
 * @author leo
 */
@DAO
public interface DataRoleScopesDAO {
    /**
     * 获取角色代码对应的所有权限范围（组织,orgId）
     * @param roleCode
     * @return
     * @throws SQLException
     */
    @SQL("SELECT \"orgId\" from " + TableName.DataRoleFuncs + " WHERE \"roleCode\" = :roleCode")
    public List<String> getScopes(@SQLParam("roleCode") int roleCode) throws SQLException;;

    /**
     * 添加一个权限范围（组织,orgId）到指定的role
     * 
     * @param roleCode
     * @param orgId
     * @return
     * @throws SQLException
     */
    @SQL("INSERT INTO " + TableName.DataRoleFuncs
            + "(\"roleCode\", \"orgId\")" + " VALUES (:roleCode,:orgId)")
    public boolean addScopeToRole(@SQLParam("roleCode") int roleCode, @SQLParam("orgId") String orgId) throws SQLException;;

    /**
     * 删除角色中的一个权限范围（组织,orgId）
     * @return
     */
    @SQL("DELETE FROM " + TableName.DataRoleFuncs + " WHERE \"orgId\" = :orgId AND \"roleCode\" = :roleCode")
    public boolean delete(@SQLParam("roleCode") int roleCode, @SQLParam("orgId") String orgId) throws SQLException;

    /**
     * 清除表中数据，慎用
     * @return
     */
    @SQL("DELETE * FROM " + TableName.DataRoleFuncs)
    public boolean clear() throws SQLException;
}
