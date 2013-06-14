/**
 * FuncRoleUsersDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午1:59:08
 */
package com.wiselink.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.base.TableName;

/**
 * func role users
 * <funcRoleCode, userId>
 * @author leo
 */
public interface FuncRoleUsersDAO  {
    /**
     * get list of users' codes which are assigned to according func-role-code
     * @param funcRoleCode
     * @return
     */
    @SQL("SELECT \"userId\" from " + TableName.FuncRoleUsers + " WHERE \"funcRoleCode\" = :funcRoleCode")
    public List<String> getUsers(@SQLParam("funcRoleCode") int funcRoleCode);

    /**
     * 添加一个用户到指定的role
     * @param funcRoleCode
     * @param funcCode
     * @return
     */
    @SQL("INSERT INTO " + TableName.FuncRoleUsers
            + "(\"funcRoleCode\", \"userId\")" + " VALUES (:funcRoleCode,:userId)")
    public boolean addFuncToRole(@SQLParam("funcRoleCode") int funcRoleCode, @SQLParam("userId") String userId);
}
