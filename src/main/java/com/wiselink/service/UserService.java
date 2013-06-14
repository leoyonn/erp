/**
 * SecurityService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:55:46 PM
 */
package com.wiselink.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.base.AuthResult;
import com.wiselink.dao.UserDAO;
import com.wiselink.model.User;
import com.wiselink.model.UserCard;

/**
 * @author leo
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
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
        LOGGER.error("check pass: userId:{}, real:{}, para:{}", new Object[]{userId, real, password});
        
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

    /**
     * @param userIds
     * @return
     */
    public List<UserCard> getUsers(List<String> userIds) {
        // TODO Auto-generated method stub
        return null;
    }

}
