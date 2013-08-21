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
import java.util.Comparator;
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
import com.wiselink.dao.UserDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.param.QueryListParam;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.model.user.Positions;
import com.wiselink.model.user.User;
import com.wiselink.model.user.UserCard;
import com.wiselink.model.user.UserCardRaw;
import com.wiselink.model.user.UserCategory;
import com.wiselink.model.user.UserPass;
import com.wiselink.model.user.UserRaw;
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
    private UserDAO userDao;

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
     * 
     * @param uesr
     * @param password
     * @return
     * @throws ServiceException
     */
    public boolean newUser(UserRaw user, String password) throws ServiceException {
        try {
            return userDao.add(user, password);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 更新一个用户的信息
     * 
     * @param user
     * @return
     * @throws ServiceException
     */
    public boolean updateUser(UserRaw user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 修改一个用户的密码
     * 
     * @param userId
     * @param oldpass
     * @param newpass
     * @return
     * @throws ServiceException
     */
    public boolean updatePassword(String userId, String oldpass, String newpass) throws ServiceException {
        try {
            return userDao.updatePasswordById(userId, oldpass, newpass);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 删除用户信息
     * 
     * @param id
     * @param creatorId
     * @return
     * @throws ServiceException
     */
    public boolean deleteUser(String id, String creatorId) throws ServiceException {
        try {
            return userDao.delete(id, creatorId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取一个用户的密码
     * 
     * @param userId
     * @return
     */
    public UserPass getPasswordById(String userId) {
        try {
            return userDao.getPasswordById(userId);
        } catch (Exception ex) {
            LOGGER.error("get pass [" + userId + "] got exception:", ex);
            return null;
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
                pass = userDao.getPasswordById(user);
            } else if (IdUtils.isUserAccountLegal(user)) {
                pass = userDao.getPasswordByAccount(user);
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
        return new AuthResult(AuthStatus.SUCCESS, pass.id, passToken);
    }

    /**
     * 获取一个用户的信息
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    public User getUserById(String id) throws ServiceException {
        try {
            UserRaw raw = userDao.getUserById(id);
            if (raw == null) {
                return null;
            }
            return buildUser(raw);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取一个用户的信息
     * 
     * @param account
     * @return
     * @throws ServiceException
     */
    public User getUserByAccount(String account) throws ServiceException {
        try {
            UserRaw raw = userDao.getUserByAccount(account);
            if (raw == null) {
                return null;
            }
            return buildUser(raw);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 通过raw构建role，多次读表
     * 
     * @param raw
     * @return
     * @throws ServiceException
     */
    public User buildUser(UserRaw raw) throws ServiceException {
        LOGGER.debug("building user data: {}...", raw);
        User user = new User(raw);
        user.setCat(UserCategory.fromCode(raw.catCode));
        user.setPos(Positions.getInstance().getPosition(raw.posCode));
        user.setStat(UserStatus.fromCode(raw.statCode));
        try {
            user.setFrole(funcRoleDao.find(raw.froleCode));
            user.setDrole(dataRoleDao.find(raw.droleCode));
            user.setCorp(corpDao.find(raw.corpId));
            user.setDept(deptDao.find(raw.deptId));
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return user;
    }

    /**
     * 非常重量级的函数
     * 
     * @param userIds
     * @return
     * @throws ServiceException 
     */
    public List<User> buildUsers(List<UserRaw> raws) throws ServiceException {
        // 1. read all user roleCs
        // 2. init roles and code/ids of attributes
        Map<String, User> users = new HashMap<String, User>();
        Map<Integer, FuncRoleInfo> froles = new HashMap<Integer, FuncRoleInfo>();
        Map<Integer, DataRoleInfo> droles = new HashMap<Integer, DataRoleInfo>();
        Map<String, Corp> corps = new HashMap<String, Corp>();
        Map<String, Dept> depts = new HashMap<String, Dept>();
        for (int i = 0; i < raws.size(); i++) {
            UserRaw raw = raws.get(i);
            LOGGER.debug("building user data: {}...", raw);
            User user = new User(raw);
            users.put(user.id, user);
            user.setCat(UserCategory.fromCode(raw.catCode));
            user.setPos(Positions.getInstance().getPosition(raw.posCode));
            user.setStat(UserStatus.fromCode(raw.statCode));
            froles.put(raw.froleCode, null);
            droles.put(raw.droleCode, null);
            corps.put(raw.corpId, null);
            depts.put(raw.deptId, null);
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
        for (int i = 0; i < raws.size(); i++) {
            UserRaw raw = raws.get(i);
            User user = users.get(raw.id);
            LOGGER.debug("filling user role: {}...", raw);
            user.setFrole(froles.get(raw.froleCode));
            user.setDrole(droles.get(raw.droleCode));
            user.setCorp(corps.get(raw.corpId));
            user.setDept(depts.get(raw.deptId));
        }
        List<User> res = new ArrayList<User>(users.values());
        Collections.sort(res, COMPARATOR_USER_BY_ID);
        return res;
    }

    /**
     * @param listParam
     * @return
     * @throws ServiceException 
     */
    public List<User> queryUsers(QueryListParam param) throws ServiceException {
        List<UserRaw> raws = Collections.emptyList();
        String name = param.getLikeField("name", "");
        String corpId = param.getStringField("corpId", "");
        String deptId = param.getStringField("deptId", "");
        int posCode = param.getIntField("posCode", -1);
        int froleCode = param.getIntField("froleCode", -1);
        int droleCode = param.getIntField("droleCode", -1);
        int from = (param.page - 1) * param.size + 1;
        int to = from + param.size - 1;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("list depts of name: {} from {} to {}", new Object[]{name, from, to});
        }
        try {
            raws = userDao.queryUsers(name, corpId, deptId, posCode, froleCode, droleCode, from, to);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return buildUsers(raws);
    }

    /**
     * @param userIds
     * @return
     * @throws ServiceException
     */
    public List<UserCard> getUserCards(List<String> userIds) throws ServiceException {
        List<UserCardRaw> raws = getUserCardRaws(userIds);
        return buildUserCards(raws);
    }

    public List<UserCard> buildUserCards(List<UserCardRaw> raws) throws ServiceException {
        // 1. read all user roleCs
        // 2. init roles and code/ids of attributes
        Map<String, UserCard> cards = new HashMap<String, UserCard>();
        Map<String, Corp> corps = new HashMap<String, Corp>();
        Map<String, Dept> depts = new HashMap<String, Dept>();
        for (int i = 0; i < raws.size(); i++) {
            UserCardRaw raw = raws.get(i);
            LOGGER.debug("building user card: {}...", raw);
            UserCard card = new UserCard(raw);
            cards.put(card.id, card);
            card.setPos(Positions.getInstance().getPosition(raw.posCode).name);
            corps.put(raw.corpId, null);
            depts.put(raw.deptId, null);
        }
        // 3. batch get all attributes
        List<Corp> corpsl = Collections.emptyList();
        List<Dept> deptsl = Collections.emptyList();
        try {
            corpsl = corpDao.list(corps.keySet());
            deptsl = deptDao.list(depts.keySet());
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        // 4. batch set into map for retrieve: O(n^2)->O(n)
        for (Corp corp: corpsl) corps.put(corp.id, corp);
        for (Dept dept: deptsl) depts.put(dept.id, dept);
        for (int i = 0; i < raws.size(); i++) {
            UserCardRaw raw = raws.get(i);
            UserCard card = cards.get(raw.id);
            LOGGER.debug("filling user role: {}...", raw);
            card.setCorp(corps.get(raw.corpId).name);
            card.setDept(depts.get(raw.deptId).name);
        }
        List<UserCard> res = new ArrayList<UserCard>(cards.values());
        Collections.sort(res, COMPARATOR_CARD_BY_ID);
        return res;
    }


    /**
     * 获取用户名片原始信息
     * 
     * @param userIds
     * @return
     * @throws ServiceException 
     */
    private List<UserCardRaw> getUserCardRaws(List<String> userIds) throws ServiceException {
        try {
            return userDao.getUserCardsById(userIds);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取所有的用户，仅用于调试
     * 
     * @return
     * @throws ServiceException
     */
    public List<User> all() throws ServiceException {
        List<UserRaw> raws = Collections.emptyList();
        try {
            raws = userDao.all();
            LOGGER.debug("all user raws:{}", raws);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return buildUsers(raws);
    }

    public static final Comparator<User> COMPARATOR_USER_BY_ID = new Comparator<User>() {
        @Override
        public int compare(User u1, User u2) {
            return u1.id.compareTo(u2.id);
        }
    };

    public static final Comparator<UserCard> COMPARATOR_CARD_BY_ID = new Comparator<UserCard>() {
        @Override
        public int compare(UserCard u1, UserCard u2) {
            return u1.id.compareTo(u2.id);
        }
    };

}