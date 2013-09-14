/**
 * BudgetDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-11 下午5:51:13
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;
import com.wiselink.model.budget.Budget;

/**
 * @author leo
 */
@DAO
public interface BudgetDAO {
    String KEYS ="\"type\", \"progress\", \"status\", \"year\", \"org\", \"createTime\", \"creatorId\", \"amountApply\", \"amountApprove\", \"amountAlloc\"";
    String KEYS_WITH_CODE ="\"code\", " + KEYS;
    String VALUES = ":type, :progress, :status, :year, :org, :createTime, :creatorId, :amountApply, :amountApprove, :amountAlloc";
    String VALUES_WITH_CODE = ":code, " + VALUES;
    String VALUES_OBJ = ":b.type, :b.progress, :b.status, :b.year, :b.org, :b.createTime, :b.creatorId, :b.amountApply, :b.amountApprove, :b.amountAlloc";
    String VALUES_OBJ_WITH_CODE = ":b.code, " + VALUES_OBJ;
    String KVS = "\"type\" = :type, \"progress\" = :progress, \"status\" = :status," +
    		" \"year\" = :year, \"org\" = :org, \"createTime\" = :createTime, \"creatorId\" = :creatorId," +
    		" \"amountApply\" = :amountApply, \"amountApprove\" = :amountApprove, \"amountAlloc\" = :amountAlloc";
    String KVS_OBJ = "\"type\" = :b.type, \"progress\" = :b.progress, \"status\" = :b.status," +
    		" \"year\" = :b.year, \"org\" = :b.org, \"createTime\" = :b.createTime, \"creatorId\" = :b.creatorId," +
    		" \"amountApply\" = :b.amountApply, \"amountApprove\" = :b.amountApprove, \"amountAlloc\" = :b.amountAlloc";

    
    @SQL("SELECT PICC.\"budget_indexer\".NEXTVAL FROM DUAL")
    public int newCode();

    @SQL("INSERT INTO " + TableName.Budget + "(" + KEYS_WITH_CODE + ") VALUES (" + VALUES_OBJ_WITH_CODE + ")")
    public boolean add(@SQLParam("b") Budget budget);
    
    @SQL("UPDATE " + TableName.Budget + " SET " + KVS_OBJ + " WHERE \"code\"=:b.code")
    public boolean up(@SQLParam("b") Budget budget) throws SQLException, DataAccessException;

    @SQL("SELECT * FROM " + TableName.Budget + " WHERE \"code\" = :code")
    public Budget find(@SQLParam("code") int code) throws SQLException, DataAccessException;

    @SQL("SELECT * FROM " + TableName.Budget)
    public List<Budget> all() throws SQLException, DataAccessException;

    @SQL("DELETE FROM " + TableName.Budget + "WHERE \"code\"=:code")
    public boolean delete(@SQLParam("code") int code) throws SQLException, DataAccessException;

    @SQL("DELETE FROM " + TableName.Budget)
    public boolean clear() throws SQLException, DataAccessException;
}
