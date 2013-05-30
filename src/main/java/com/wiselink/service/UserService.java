/**
 * SecurityService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:55:46 PM
 */
package com.wiselink.service;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.base.AuthResult;
import com.wiselink.dao.UserDAO;
import com.wiselink.model.User;

/**
 * @author leo
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDao;

    /**
     * @param userId
     * @param password
     * @return
     * @throws SQLException 
     */
    public AuthResult checkPassword(String userId, String password) {
        String real = null;
        try {
            real = userDao.getPassword(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(real)) {
            return AuthResult.INVALID_USER;
        } else if (!real.equalsIgnoreCase(password)) {
            return AuthResult.WRONG_PASSWORD;
        }
        return AuthResult.SUCCESS;
    }
    
    public User getUser(String userId) {
        try {
            return userDao.getUserById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO exception
        return null;
    }

}