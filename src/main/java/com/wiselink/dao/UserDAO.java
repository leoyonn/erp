/**
 * UserDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-18 下午4:04:42
 */
package com.wiselink.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQLParam;

/**
 * 
 * @author leo
 */
@DAO
public interface UserDAO {
    public boolean add(@SQLParam("id") String userId,
            @SQLParam("account") String account,
            @SQLParam("name") String name,
            @SQLParam("password") String password,
            @SQLParam("avatar") String avatar,
            @SQLParam("email") String email,
            @SQLParam("phone") String phone,
            @SQLParam("tel") String tel,
            @SQLParam("desc") String desc,
            @SQLParam("province") String province,
            @SQLParam("city") String city,
            @SQLParam("creatorId") String creatorId,
            @SQLParam("operId") String operId,

            @SQLParam("catCode") int catCode,
            @SQLParam("posCode") int posCode,
            @SQLParam("froleCode") int froleCode,
            @SQLParam("droleCode") int droleCode,
            @SQLParam("statCode") int statCode,
            @SQLParam("corpId") String corpId,
            @SQLParam("deptId") String deptId) throws SQLException, DataAccessException;

}
