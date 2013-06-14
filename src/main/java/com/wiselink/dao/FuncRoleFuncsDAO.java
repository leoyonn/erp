/**
 * FuncRoleFuncsDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午1:59:24
 */
package com.wiselink.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.base.TableName;

/**
 * <funcRoleCode, funcCode>
 * @author leo
 */
@DAO
public interface FuncRoleFuncsDAO {
    /**
     * get list of func codes which are assigned to according func-role-code
     * @param funcRoleCode
     * @return
     */
    @SQL("SELECT \"funcCode\" from " + TableName.FuncRoleFuncs + " WHERE \"funcRoleCode\" = :funcRoleCode")
    public List<Integer> getFuncs(@SQLParam("funcRoleCode") int funcRoleCode);

    /**
     * 添加一个功能到指定的role
     * @param funcRoleCode
     * @param funcCode
     * @return
     */
    @SQL("INSERT INTO " + TableName.FuncRoleFuncs
            + "(\"funcRoleCode\", \"funcCode\")" + " VALUES (:funcRoleCode,:funcCode)")
    public boolean addFuncToRole(@SQLParam("funcRoleCode") int funcRoleCode, @SQLParam("funcCode") int funcCode);
}
