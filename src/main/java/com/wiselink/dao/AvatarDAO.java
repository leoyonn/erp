/**
 * AvatarDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-11 上午10:06:18
 */
package com.wiselink.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.wiselink.base.TableName;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

/**
 * 存储用户头像<url, data>
 * url为：avatar/u/:userId, data为图像二进制数据
 * 
 * @author leo
 */
@DAO
public interface AvatarDAO {
    /**
     * add an corp into database
     * @param corp
     * @return
     * @throws SQLException, DataAccessException
     */
    @SQL("INSERT INTO " + TableName.Avatar + "(\"url\", \"data\")" + " VALUES (:url, :data)")
    public boolean add(@SQLParam("url") String url, @SQLParam("data") byte[] data) throws SQLException, DataAccessException;

    @SQL("SELECT \"data\" FROM " + TableName.Avatar + " WHERE \"url\"=:url")
    public byte[] get(@SQLParam("url") String url) throws SQLException, DataAccessException;

}
