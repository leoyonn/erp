/**
 * UserDao.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 8:49:10 PM
 */
package com.wiselink.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

/**
 * @author leo
 */
@DAO
public interface UserDao {
    @SQL("SELECT password FROM user WHERE id = :userId")
    public String getPassword(@SQLParam("userId") long userId);

}
