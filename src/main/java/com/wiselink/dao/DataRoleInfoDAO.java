/**
 * DataRoleInfoDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午1:45:07
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.base.TableName;
import com.wiselink.model.role.DataRole;
import com.wiselink.model.role.DataRoleInfo;

/**
 * 
 * @author leo
 */
@DAO
public interface DataRoleInfoDAO {
    /**
     * 添加一条data-role-info到database
     * @param code
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     * @throws SQLException
     */
    @SQL("INSERT INTO " + TableName.DataRoleInfo 
            + "(\"code\", \"name\", \"desc\", \"levelCode\", \"corpId\", \"deptId\", \"createTime\", \"creator\")"
            + " VALUES (:code,:name,:desc,:levelCode,:corpId,:deptId,systime,:creatorId)")
    public boolean add(@SQLParam("code") int code, @SQLParam("name") String name, @SQLParam("desc") String desc,
            @SQLParam("levelCode") int levelCode, @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId,
            @SQLParam("creatorId") String creatorId) throws SQLException;

    /**
     * 修改一条level-role-info
     * @param code
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @return
     * @throws SQLException
     */
    @SQL("UPDATE " + TableName.DataRoleInfo
            + " SET (\"name\", \"desc\", \"levelCode\", \"corpId\", \"deptId\")"
            + " = (:name,:desc,:corpId,:deptId) WHERE \"code\" = :code")
    public boolean modify(@SQLParam("code") int code, @SQLParam("name") String name, @SQLParam("desc") String desc,
            @SQLParam("levelCode") int levelCode, @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId) throws SQLException;

    /**
     * find data-role-info using code
     * @param code
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TableName.DataRoleInfo + " WHERE \"code\" = ':code'")
    public DataRoleInfo find(@SQLParam("code") int code) throws SQLException;

    /**
     * find data-role-info using name
     * @param name
     * @return
     * @throws SQLException
     */
    @SQL("SELECT * FROM " + TableName.DataRoleInfo + " WHERE \"name\" = ':name'")
    public DataRoleInfo findByName(@SQLParam("name") String name) throws SQLException;

    /**
     * list all #num data-roles sorted by {@link DataRole#code} from #from 
     * 
     * @param from
     * @param num
     * @return
     */
    // config:mysql @SQL("SELECT * FROM " + TABLE_NAME_FROLE + " WHERE \"code\" >= ':from' LIMIT 0,:num")
    @SQL("SELECT * FROM " + TableName.DataRoleInfo + " WHERE \"code\" >= ':from' AND ROWNUM <=:num")
    public List<DataRoleInfo> list(@SQLParam("from") int from, @SQLParam("num") int num) throws SQLException;
}