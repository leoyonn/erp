/**
 * UserService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:55:46 PM
 */
package com.wiselink.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.base.AuthResult;
import com.wiselink.base.AuthStatus;
import com.wiselink.dao.CorpDAO;
import com.wiselink.dao.DataRoleInfoDAO;
import com.wiselink.dao.DeptDAO;
import com.wiselink.dao.FuncRoleInfoDAO;
import com.wiselink.dao.UserInfoDAO;
import com.wiselink.dao.UserRoleDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.model.user.Positions;
import com.wiselink.model.user.User;
import com.wiselink.model.user.UserCard;
import com.wiselink.model.user.UserCategory;
import com.wiselink.model.user.UserInfo;
import com.wiselink.model.user.UserPass;
import com.wiselink.model.user.UserRole;
import com.wiselink.model.user.UserRoleC;
import com.wiselink.model.user.UserStatus;
import com.wiselink.utils.AuthUtils;
import com.wiselink.utils.IdUtils;

/**
 * TODO: too much DAO, optimize on perf.
 * @author leo
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserInfoDAO userInfoDao;

    @Autowired
    private UserRoleDAO userRoleDao;

    @Autowired
    private FuncRoleInfoDAO funcRoleDao;

    @Autowired
    private DataRoleInfoDAO dataRoleDao;

    @Autowired
    private CorpDAO corpDao;

    @Autowired
    private DeptDAO deptDao;

    /**
     * 添加一个用户
     * @param id
     * @param account
     * @param name
     * @param password
     * @param avatar
     * @param email
     * @param phone
     * @param tel
     * @param desc
     * @param province
     * @param city
     * @param creatorId
     * @param operId
     * @return
     * @throws ServiceException
     */
    public boolean addUser(String id, String account, String name, String password, String avatar,
            String email, String phone, String tel, String desc, String province, String city,
            String creatorId, String operId) throws ServiceException {
        try {
            return userInfoDao.add(id, account, name, password, avatar, email, phone, tel,
                    desc, province, city, creatorId, operId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * @param id
     * @param account
     * @param name
     * @param avatar
     * @param email
     * @param phone
     * @param tel
     * @param desc
     * @param province
     * @param city
     * @param creatorId
     * @param operId
     * @return
     * @throws ServiceException
     */
    public boolean updateUserInfo(String id, String account, String name, String avatar, String email, String phone,
            String tel, String desc, String province, String city, String creatorId, String operId)
            throws ServiceException {
        try {
            return userInfoDao.update(id, account, name, avatar, email, phone, tel,
                    desc, province, city, creatorId, operId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * @param userId
     * @param oldpass
     * @param newpass
     * @return
     * @throws ServiceException
     */
    public boolean updateUserPassword(String userId, String oldpass, String newpass) throws ServiceException {
        try {
            return userInfoDao.updatePasswordById(userId, oldpass, newpass);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 添加一个用户的角色信息
     * @param userId
     * @param catCode
     * @param posCode
     * @param froleCode
     * @param droleCode
     * @param statCode
     * @param corpId
     * @param deptId
     * @return
     * @throws ServiceException
     */
    public boolean addUserRole(String userId, int catCode, int posCode, int froleCode, int droleCode,
            int statCode, String corpId, String deptId) throws ServiceException {
        try {
            return userRoleDao.addUserRole(userId, catCode, posCode, froleCode, droleCode, statCode, corpId, deptId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 更新用户的所有角色信息
     * @param userId
     * @param catCode
     * @param posCode
     * @param froleCode
     * @param droleCode
     * @param statCode
     * @param corpId
     * @param deptId
     * @return
     * @throws ServiceException
     */
    public boolean updateUserRole(String userId, int catCode, int posCode, int froleCode, int droleCode,
            int statCode, String corpId, String deptId) throws ServiceException {
        try {
            return userRoleDao.updateUserRole(userId, catCode, posCode, froleCode, droleCode, statCode, corpId, deptId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 删除用户，包括user-info和user-role
     * @param id
     * @return
     * @throws ServiceException
     */
    public boolean deleteUser(String id) throws ServiceException {
        try {
            return userInfoDao.delete(id)
                    && userRoleDao.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * user可以是account或id，都支持。account中必须包含字幕，id中必须全为数字。
     * @param user
     * @param password
     * @param userIp
     * @return
     * @throws SQLException, DataAccessException 
     */
    public AuthResult checkPassword(String user, String password, String userIp) {
        UserPass pass = null;
        try {
            if (IdUtils.isUserIdLegal(user)) {
                pass = userInfoDao.getPasswordById(user);
            } else if (IdUtils.isUserAccountLegal(user)) {
                pass = userInfoDao.getPasswordByAccount(user);
            } else {
                return AuthResult.INVALID_USER;
            }
            LOGGER.debug("check pass: user:{}, real:{}, para:{}", new Object[]{user, pass, password});
        } catch (Exception ex) {
            LOGGER.error("check pass [" + user + "|" + password + "] got exception:", ex);
        }
        if (pass == null || StringUtils.isEmpty(pass.password)) {
            return AuthResult.INVALID_USER;
        } else if (!pass.password.equalsIgnoreCase(password)) {
            return AuthResult.WRONG_PASSWORD;
        }
        String sessionCode = AuthUtils.generateRandomAESKey();
        String passToken = AuthUtils.genPassToken(pass, sessionCode, userIp);
        return new AuthResult(AuthStatus.SUCCESS, user, passToken);
    }

    /**
     * 获取一个用户，包括info和role
     * @param id
     * @return
     * @throws ServiceException
     */
    public User getUser(String id) throws ServiceException {
        UserInfo info = getUserInfo(id);
        if (info == null) {
            LOGGER.warn("no such user: {}", id);
            return null;
        }
        UserRole role = getUserRole(id);
        LOGGER.debug("got user: info:{}, role:{}, ", info, role);
        return new User(id, info, role);
    }

    /**
     * @param userId
     * @return
     * @throws ServiceException
     */
    public UserInfo getUserInfo(String userId) throws ServiceException {
        try {
            return userInfoDao.getUserById(userId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取用户角色，涉及多次读表操作
     * 
     * @param userId
     * @return
     * @throws ServiceException
     */
    public UserRole getUserRole(String userId) throws ServiceException {
        UserRoleC rolec = null;
        try {
            rolec = userRoleDao.find(userId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        if (rolec == null) {
            return null;
        }
        return buildUserRole(rolec);
    }

    /**
     * 通过rolec构建role，多次读表
     * TODO to be optimized
     * @param rolec
     * @return
     * @throws ServiceException
     */
    public UserRole buildUserRole(UserRoleC rolec) throws ServiceException {
        LOGGER.debug("building user role: {}...", rolec);
        UserRole role = new UserRole(rolec.id);
        role.setCat(UserCategory.fromCode(rolec.catCode));
        role.setPos(Positions.getInstance().getPosition(rolec.posCode));
        role.setStat(UserStatus.fromCode(rolec.statCode));
        try {
            role.setFrole(funcRoleDao.find(rolec.froleCode));
            role.setDrole(dataRoleDao.find(rolec.droleCode));
            role.setCorp(corpDao.find(rolec.corpId));
            role.setDept(deptDao.find(rolec.deptId));
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return role;
    }

    /**
     * @param userIds
     * @return
     * @throws ServiceException 
     */
    public List<UserInfo> getUserInfos(List<String> userIds) throws ServiceException {
        try {
            return userInfoDao.getUsersById(userIds);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<UserRoleC> getUserRoleCs(List<String> userIds) throws ServiceException {
        List<UserRoleC> rolecs = null;
        try {
            rolecs = userRoleDao.getRoles(userIds);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        LOGGER.debug("userids:{} got rolecs:{}", userIds, rolecs);
        return rolecs;
    }

    /**
     * 非常重量级的函数
     * @param userIds
     * @return
     * @throws ServiceException 
     */
    public Map<String, UserRole> getUserRoles(List<UserRoleC> rolecs) throws ServiceException {
        // 1. read all user roleCs
        // 2. init roles and code/ids of attributes
        Map<String, UserRole> roles = new HashMap<String, UserRole>();
        Map<Integer, FuncRoleInfo> froles = new HashMap<Integer, FuncRoleInfo>();
        Map<Integer, DataRoleInfo> droles = new HashMap<Integer, DataRoleInfo>();
        Map<String, Corp> corps = new HashMap<String, Corp>();
        Map<String, Dept> depts = new HashMap<String, Dept>();
        for (int i = 0; i < rolecs.size(); i++) {
            UserRoleC rolec = rolecs.get(i);
            LOGGER.debug("building user role: {}...", rolec);
            UserRole role = new UserRole(rolec.id);
            roles.put(rolec.id, role);
            role.setCat(UserCategory.fromCode(rolec.catCode));
            role.setPos(Positions.getInstance().getPosition(rolec.posCode));
            role.setStat(UserStatus.fromCode(rolec.statCode));
            froles.put(rolec.froleCode, null);
            droles.put(rolec.droleCode, null);
            corps.put(rolec.corpId, null);
            depts.put(rolec.deptId, null);
        }
        // 3. batch get all attributes
        List<FuncRoleInfo> frolesl = Collections.emptyList();
        List<DataRoleInfo> drolesl = Collections.emptyList();
        List<Corp> corpsl = Collections.emptyList();
        List<Dept> deptsl = Collections.emptyList();
        try {
            frolesl = funcRoleDao.list(froles.keySet());
            drolesl = dataRoleDao.list(droles.keySet());
            corpsl = corpDao.list(corps.keySet());
            deptsl = deptDao.list(depts.keySet());
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        // 4. batch set into map for retrieve: O(n^2)->O(n)
        for (FuncRoleInfo frole: frolesl) froles.put(frole.code, frole);
        for (DataRoleInfo drole: drolesl) droles.put(drole.code, drole);
        for (Corp corp: corpsl) corps.put(corp.id, corp);
        for (Dept dept: deptsl) depts.put(dept.id, dept);
        for (int i = 0; i < rolecs.size(); i++) {
            UserRoleC rolec = rolecs.get(i);
            UserRole role = roles.get(rolec.id);
            LOGGER.debug("filling user role: {}...", role);
            role.setFrole(froles.get(rolec.froleCode));
            role.setDrole(droles.get(rolec.droleCode));
            role.setCorp(corps.get(rolec.corpId));
            role.setDept(depts.get(rolec.deptId));
        }
        return roles;
    }
    
    /**
     * @param userIds
     * @return
     * @throws ServiceException 
     */
    public List<UserCard> getUserCards(List<String> userIds) throws ServiceException {
        List<UserInfo> infos = getUserInfos(userIds);
        List<UserRoleC> rolecs = getUserRoleCs(userIds);
        Map<String, UserRole> roles = getUserRoles(rolecs);
        List<UserCard> cards = new ArrayList<UserCard>(infos.size()); 
        for (UserInfo info: infos) {
            UserRole role = roles.get(info.id);
            LOGGER.debug("filling user card: from info:{}, role:{}...", info, role);
            if (role == null) {
                cards.add(new UserCard(info.id, info.name, null, null, null));
            } else {
                cards.add(new UserCard(info.id, info.name, role.corp.name, role.dept.name, role.pos.name));
                
            }
        }
        return null;
    }

    /**
     * @param userIds
     * @return
     * @throws ServiceException
     */
    public List<User> getUsers(List<String> userIds) throws ServiceException {
        List<UserInfo> infos = getUserInfos(userIds);
        LOGGER.debug("got user infos:{}", infos);
        List<UserRoleC> rolecs = getUserRoleCs(userIds);
        Map<String, UserRole> roles = getUserRoles(rolecs);
        LOGGER.debug("got user roles:{}", roles);
        List<User> users = new ArrayList<User>(infos.size()); 
        for (UserInfo info: infos) {
            UserRole role = roles.get(info.id);
            LOGGER.debug("filling user: from info:{}, role:{}...", info, role);
            users.add(new User(info.id, info, role));
        }
        LOGGER.debug("got users:{}, ", users);
        return users;
    }
    
    /**
     * 获取所有的用户，仅用于调试
     * @return
     * @throws ServiceException 
     */
    public List<User> all() throws ServiceException {
        List<UserInfo> infos = Collections.emptyList();
        List<UserRoleC> rolecs = Collections.emptyList();
        try {
            infos = userInfoDao.all();
            LOGGER.debug("all user infos:{}", infos);
            rolecs = userRoleDao.all();
            LOGGER.debug("all user rolecs:{}", rolecs);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        Map<String, UserRole> roles = getUserRoles(rolecs);
        LOGGER.debug("all user roles:{}", roles);
        List<User> users = new ArrayList<User>(infos.size()); 
        for (UserInfo info: infos) {
            UserRole role = roles.get(info.id);
            LOGGER.debug("filling user: from info:{}, role:{}...", info, role);
            users.add(new User(info.id, info, role));
        }
        LOGGER.debug("all users:{}, ", users);
        return users;
    }
}