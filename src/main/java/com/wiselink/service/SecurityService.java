/**
 * SecurityService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:55:46 PM
 */
package com.wiselink.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.base.AuthResult;
import com.wiselink.dao.UserDao;

/**
 * 
 * @author leo
 */
@Service
public class SecurityService {

    @Autowired
    private UserDao userDao;
    
    /**
     * @param userId
     * @param password
     * @return
     */
    public AuthResult checkPassword(long userId, String password) {
        String real = userDao.getPassword(userId);
        if (StringUtils.isEmpty(real)) {
            return AuthResult.INVALID_USER;
        } else if (!real.equalsIgnoreCase(password)) {
            return AuthResult.WRONG_PASSWORD;
        }
        return AuthResult.SUCCESS;
    }

}
