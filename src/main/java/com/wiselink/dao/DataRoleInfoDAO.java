/**
 * DataRoleInfoDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午1:45:07
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.management.relation.Role;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;
import com.wiselink.model.role.DataRole;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * 
 * @author leo
 */
@DAO
@SuppressWarnings("not completed|post|验证sequence！")
public interface DataRoleInfoDAO {

    /**
     * 添加一条data-role-info到database
     * <li>code为自增PK字段；
     * <li>name为unique字段；
     * <li>createTime/updateTime为database系统时间戳
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("INSERT INTO " + TableName.DataRoleInfo 
            + "(\"name\", \"desc\", \"levelCode\", \"corpId\", \"deptId\", \"creatorId\", \"createTime\", \"updateTime\")"
            + " VALUES (:name,:desc,:levelCode,:corpId,:deptId,:creatorId,sysdate,sysdate)")
    boolean add(@SQLParam("name") String name, @SQLParam("desc") String desc,
            @SQLParam("levelCode") int levelCode, @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId,
            @SQLParam("creatorId") String creatorId) throws SQLException, DataAccessException;

    /**
     * 修改一条level-role-info
     * @param code
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("UPDATE " + TableName.DataRoleInfo
            + " SET \"name\"=:name, \"desc\"=:desc, \"levelCode\"=:levelCode, \"corpId\"=:corpId, \"deptId\"=:deptId"
            + " WHERE \"code\" = :code")
    boolean update(@SQLParam("code") int code, @SQLParam("name") String name, @SQLParam("desc") String desc,
            @SQLParam("levelCode") int levelCode, @SQLParam("corpId") String corpId, @SQLParam("deptId") String deptId) throws SQLException, DataAccessException;

    /**
     * find data-role-info using code
     * @param code
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.DataRoleInfo + " WHERE \"code\" = :code")
    DataRoleInfo find(@SQLParam("code") int code) throws SQLException, DataAccessException;

    /**
     * find data-role-info using name
     * @param name
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.DataRoleInfo + " WHERE \"name\" = :name")
    DataRoleInfo findByName(@SQLParam("name") String name) throws SQLException, DataAccessException;

    /**
     * list all #num data-roles sorted by {@link DataRole#code} from #from 
     * 
     * @param from
     * @param num
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("SELECT * FROM " + TableName.DataRoleInfo + " WHERE \"code\" >= :from AND ROWNUM <=:num ORDER BY \"code\"")
    public List<DataRoleInfo> list(@SQLParam("from") int from, @SQLParam("num") int num) throws SQLException, DataAccessException;

    /**
     * list all func-roles in :codes sorted by {@link Role#code}  
     * 
     * @param from
     * @param num
     * @return
     */
    @SQL("SELECT * FROM " + TableName.DataRoleInfo + " WHERE \"code\" IN (:codes) ORDER BY \"code\"")
    public List<DataRoleInfo> list(@SQLParam("codes") Collection<Integer>codes) throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.DataRoleInfo + " WHERE \"code\" = :code")
    public boolean delete(@SQLParam("code") int code) throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.DataRoleInfo)
    public int clear() throws SQLException, DataAccessException;
}