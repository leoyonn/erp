/**
 * SupplierDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午6:58:50
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;
import com.wiselink.model.supplier.Supplier;

/**
 * @author leo
 */
@DAO
public interface SupplierDAO {
    String KEYS_SUPPLIER = "\"id\", \"name\", \"desc\", \"type\", \"mode\", \"stype\", \"contact\", "
            + "\"tel\", \"status\", \"startTime\", \"endTime\", \"createTime\", \"creatorId\"";

    String VALUES_SUPPLIER = ":id, :name, :desc, :type, :mode, :stype, :contact, :tel, :status, " +
            "#if(:startTime != null && :startTime.getTime() > 0) {:startTime} #else{sysdate}, " +
            "#if(:endTime != null && :endTime.getTime() > 0) {:endTime} #else{sysdate}, " +
            "sysdate, :creatorId";

    String VALUES_OBJ_SUPPLIER = ":supplier.id, :supplier.name, :supplier.desc, :supplier.type," +
    		" :supplier.mode, :supplier.stype, :supplier.contact, :supplier.tel, :supplier.status," +
            "#if(:supplier.startTime != null && :supplier.startTime.getTime() > 0) {:supplier.startTime} #else{sysdate}, " +
            "#if(:supplier.endTime != null && :supplier.endTime.getTime() > 0) {:supplier.endTime} #else{sysdate}, " +
    		"sysdate, :supplier.creatorId";

    /**
     * add an supplier
     * 
     * @param supplier
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.Supplier + " (" + KEYS_SUPPLIER + ")" + " VALUES(" + VALUES_SUPPLIER + ")")
    boolean add(@SQLParam("id") String id,
            @SQLParam("name") String name, @SQLParam("desc") String desc, @SQLParam("type") String type,
            @SQLParam("mode") String mode, @SQLParam("stype") String stype, @SQLParam("contact") String contact, 
            @SQLParam("tel") String tel, @SQLParam("status") String status,
            @SQLParam("startTime") Timestamp startTime, @SQLParam("endTime") Timestamp endTime,
            @SQLParam("creatorId") String creatorId) throws SQLException, DataAccessException;

    /**
     * add an supplier
     * @param supplier
     * @return
     */
    @SQL("INSERT INTO " + TableName.Supplier + "(" + KEYS_SUPPLIER + ")" + " VALUES(" + VALUES_OBJ_SUPPLIER + ")")
    boolean add(@SQLParam("supplier") Supplier supplier);

    /**
     * 更新一个供货商的信息，不需要更新的域设为null或非法值
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("UPDATE " + TableName.Supplier + " SET "
            + "#if(:name != null && :name.length() > 0) {\"name\"=:name, }"
            + "#if(:desc != null && :desc.length() > 0) {\"desc\"=:desc, }"
            + "#if(:type != null && :type.length() > 0) {\"type\"=:type, }"
            + "#if(:mode != null && :mode.length() > 0) {\"mode\"=:mode, }"
            + "#if(:stype != null && :stype.length() > 0) {\"stype\"=:stype, }"
            + "#if(:contact != null && :contact.length() > 0) {\"contact\"=:contact, }"
            + "#if(:tel != null && :tel.length() > 0) {\"tel\"=:tel, }"
            + "#if(:status != null && :status.length() > 0) {\"status\"=:status, }"
            + "#if(:startTime != null && :startTime.getTime() > 0) {\"startTime\"=:startTime, }"
            + "#if(:endTime != null && :endTime.getTime() > 0) {\"endTime\"=:endTime, }"
            + "\"id\"=:id"
            + " WHERE \"id\"=:id")
    boolean update(@SQLParam("id") String id,
            @SQLParam("name") String name, @SQLParam("desc") String desc, @SQLParam("type") String type,
            @SQLParam("mode") String mode, @SQLParam("stype") String stype, @SQLParam("contact") String contact, 
            @SQLParam("tel") String tel, @SQLParam("status") String status,
            @SQLParam("startTime") Timestamp startTime, @SQLParam("endTime") Timestamp endTime)
                    throws SQLException, DataAccessException;

    /**
     * @param supplier
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("UPDATE " + TableName.Supplier + " SET "
            + "#if(:supplier.name != null && :supplier.name.length() > 0) {\"name\"=:supplier.name, }"
            + "#if(:supplier.desc != null && :supplier.desc.length() > 0) {\"desc\"=:supplier.desc, }"
            + "#if(:supplier.type != null && :supplier.type.length() > 0) {\"type\"=:supplier.type, }"
            + "#if(:supplier.mode != null && :supplier.mode.length() > 0) {\"mode\"=:supplier.mode, }"
            + "#if(:supplier.stype != null && :supplier.stype.length() > 0) {\"stype\"=:supplier.stype, }"
            + "#if(:supplier.contact != null && :supplier.contact.length() > 0) {\"contact\"=:supplier.contact, }"
            + "#if(:supplier.tel != null && :supplier.tel.length() > 0) {\"tel\"=:supplier.tel, }"
            + "#if(:supplier.status != null && :supplier.status.length() > 0) {\"status\"=:supplier.status, }"
            + "#if(:supplier.startTime != null && :supplier.startTime.getTime() > 0) {\"startTime\"=:supplier.startTime, }"
            + "#if(:supplier.endTime != null && :supplier.endTime.getTime() > 0) {\"endTime\"=:supplier.endTime, }"
            + "\"id\"=:supplier.id"
            + " WHERE \"id\"=:supplier.id")
    boolean update(@SQLParam("supplier") Supplier supplier) throws SQLException, DataAccessException;

    /**
     * 根据id查找供货商
     * @param id
     * @return
     */
    @SQL("SELECT " + KEYS_SUPPLIER + " FROM " + TableName.Supplier + " WHERE \"id\" = :id")
    Supplier findById(@SQLParam("id") String id) throws SQLException, DataAccessException;

    /**
     * 根据名称查找供货商
     * @param name
     * @return
     */
    @SQL("SELECT " + KEYS_SUPPLIER + " FROM " + TableName.Supplier + " WHERE \"name\" = :name")
    List<Supplier> findByName(@SQLParam("name") String name) throws SQLException, DataAccessException;

    /**
     * 根据类型或供销形式或状态查询
     * @param type
     * @param mode
     * @param status
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("SELECT " + KEYS_SUPPLIER + " FROM " + TableName.Supplier + " WHERE \"type\" = '供货商' AND (1=0 "
            + "OR \"name\" LIKE :name"
            + "#if(:mode != null && :mode.length() > 0) {OR \"mode\"=:mode }"
            + "#if(:stype != null && :stype.length() > 0) {OR \"stype\"=:stype }"
            + "#if(:status != null && :status.length() > 0) {OR \"status\"=:status }"
            + " ) ORDER BY \"id\"")
    List<Supplier> queryByOr(@SQLParam("name") String name, @SQLParam("mode") String mode, @SQLParam("stype") String stype, @SQLParam("status") String status)
            throws SQLException, DataAccessException;

    /**
     * 根据类型或供销形式或状态查询
     * @param type
     * @param mode
     * @param status
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("SELECT " + KEYS_SUPPLIER + " FROM " + TableName.Supplier + " WHERE \"type\" = '供货商' "
            + "AND \"name\" LIKE :name"
            + "#if(:mode != null && :mode.length() > 0) {AND \"mode\"=:mode }"
            + "#if(:stype != null && :stype.length() > 0) {AND \"stype\"=:stype }"
            + "#if(:status != null && :status.length() > 0) {AND \"status\"=:status }"
            + " ORDER BY \"id\"")
    List<Supplier> queryByAnd(@SQLParam("name") String name, @SQLParam("mode") String mode, @SQLParam("stype") String stype, @SQLParam("status") String status)
            throws SQLException, DataAccessException;

    /**
     * 获取所有供货商
     * 
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    @SQL("SELECT " + KEYS_SUPPLIER + " FROM " + TableName.Supplier + " WHERE \"type\" = '供货商' ORDER BY \"id\"")
    List<Supplier> all() throws SQLException, DataAccessException;

    /**
     * 删除指定供货商
     * 
     * @return
     * @throws SQLException
     *             , DataAccessException
     */
    @SQL("DELETE FROM " + TableName.Supplier + " WHERE \"id\" = :id")
    boolean delete(@SQLParam("id") String id) throws SQLException, DataAccessException;

    /**
     * 通过名称删除指定供货商
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.Supplier + " WHERE \"name\" = :name")
    boolean deleteByName(@SQLParam("name") String name) throws SQLException, DataAccessException;

    /**
     * WARNING: only for unittest and debug!
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("DELETE FROM " + TableName.Supplier)
    int clear() throws SQLException, DataAccessException;

}