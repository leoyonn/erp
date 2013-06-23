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
import com.wiselink.dao.UserInfoDAO;
import com.wiselink.dao.UserRoleDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.user.User;
import com.wiselink.model.user.UserCard;

/**
 * @author leo
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserInfoDAO userInfoDao;
    @Autowired
    private UserRoleDAO userRoleDao;

    /**
     * @param userId
     * @param password
     * @return
     * @throws SQLException, DataAccessException 
     */
    public AuthResult checkPassword(String userId, String password) {
        String real = null;
        try {
            real = userInfoDao.getPassword(userId);
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

    /**
     * @param userId
     * @return
     * @throws ServiceException
     */
    public User getUser(String userId) throws ServiceException {
        try {
            return userInfoDao.getUserById(userId);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * @param userIds
     * @return
     * @throws ServiceException 
     */
    public List<UserCard> getUsers(List<String> userIds) throws ServiceException {
        try {
            return userInfoDao.getUserCardsById(userIds);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }
}
