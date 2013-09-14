/**
 * UserService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 14, 2013 7:55:46 PM
 */
package com.wiselink.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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

import com.wiselink.dao.CorpDAO;
import com.wiselink.dao.DataRoleInfoDAO;
import com.wiselink.dao.DataRoleUsersDAO;
import com.wiselink.dao.DeptDAO;
import com.wiselink.dao.FuncRoleInfoDAO;
import com.wiselink.dao.FuncRoleUsersDAO;
import com.wiselink.dao.SupplierDAO;
import com.wiselink.dao.UserDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.param.QueryListParam;
import com.wiselink.model.param.QueryListParam.QueryType;
import com.wiselink.model.param.UserQueryParam;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.model.user.Position;
import com.wiselink.model.user.Positions;
import com.wiselink.model.user.User;
import com.wiselink.model.user.UserCard;
import com.wiselink.model.user.UserCardRaw;
import com.wiselink.model.user.UserCategory;
import com.wiselink.model.user.UserRaw;
import com.wiselink.model.user.UserStatus;
import com.wiselink.result.Auth;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
import com.wiselink.utils.AuthUtils;
import com.wiselink.utils.IdUtils;
import com.wiselink.utils.Utils;

/**
 * TODO: too much DAO, optimize on perf.
 * @author leo
 */
@Service
public class UserService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDao;

    @Autowired
    private FuncRoleInfoDAO funcRoleDao;

    @Autowired
    private FuncRoleUsersDAO froleUsersDao;
    
    @Autowired
    private DataRoleInfoDAO dataRoleDao;

    @Autowired
    private DataRoleUsersDAO droleUsersDao;
    @Autowired
    private CorpDAO corpDao;

    @Autowired
    private DeptDAO deptDao;
    
    @Autowired
    private SupplierDAO supplierDao;

    /**
     * @return
     */
    public OperResult<Collection<Position>> allPositions() {
        return r(Positions.getInstance().allPositions());
    }

    /**
     * 添加一个用户
     * 
     * @param raw
     * @param password
     * @return
     */
    public OperResult<User> newUser(UserRaw raw, String password) {
        OperResult<User> r = buildUser(raw);
        if (r.error != ErrorCode.Success) {
            return r;
        }
        if (StringUtils.isBlank(raw.id)) {
            String id = IdUtils.isUserIdLegal(raw.getAccount()) ? raw.getAccount() : IdUtils.genUserId(r.result.corp.id);
            raw.setId(id);
            r.result.setId(id);
        }
        try {
            if (!userDao.add(raw, password)) {
                return r(ErrorCode.DbInsertFail, "插入用户数据失败，请检查参数");
            }
            if (r.result.frole != null) {
                froleUsersDao.addUserToRole(raw.froleCode, raw.id);
            }
            if (r.result.drole != null) {
                droleUsersDao.addUserToRole(raw.droleCode, raw.id);
            }
        } catch (Exception ex) {
            LOGGER.error("new user " + raw + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "插入用户数据失败：" , ex);
        }
        return r;
    }

    /**
     * 更新一个用户的信息
     * 
     * @param raw
     * @return
     */
    public OperResult<User> updateUser(UserRaw raw) {
        OperResult<User> r = buildUser(raw);
        if (r.error != ErrorCode.Success) {
            return r;
        }
        try {
            if (!userDao.update(raw)) {
                return r(ErrorCode.DbUpdateFail, "更新用户数据失败，请检查参数");
            }
            froleUsersDao.deleteUser(raw.id);
            if (r.result.frole != null) {
                froleUsersDao.addUserToRole(raw.froleCode, raw.id);
            }
            droleUsersDao.deleteUser(raw.id);
            if (r.result.drole != null) {
                droleUsersDao.addUserToRole(raw.droleCode, raw.id);
            }
        } catch (Exception ex) {
            LOGGER.error("update user " + raw + " got exception", ex);
            return r(ErrorCode.DbUpdateFail, "更新用户数据失败：" , ex);
        }
        return r;
    }

    /**
     * 修改一个用户的密码
     * 
     * @param userId
     * @param oldpass
     * @param newpass
     * @return
     */
    public OperResult<String> updatePassword(String userId, String oldpass, String newpass) {
        try {
            if (!userDao.updatePasswordById(userId, oldpass, newpass)) {
                return r(ErrorCode.DbUpdateFail, "更新密码失败，请检查参数");
            }
        } catch (Exception ex) {
            LOGGER.error("update user password " + userId + " got exception", ex);
            return r(ErrorCode.DbUpdateFail, "更新密码失败：" , ex);
        }
        return r("更新密码成功");
    }

    /**
     * 删除用户信息
     * 
     * @param code
     * @param creatorId
     * @return
     */
    public OperResult<String> deleteUsers(List<String> ids) {
        int count = 0;
        try {
            if ((count = userDao.delete(ids)) <= 0) {
                return r(ErrorCode.DbDeleteFail, "删除用户失败，请检查参数");
            }
            froleUsersDao.deleteUsers(ids);
            droleUsersDao.deleteUsers(ids);
        } catch (Exception ex) {
            LOGGER.error("delete user " + ids + " got exception", ex);
            return r(ErrorCode.DbDeleteFail, "删除用户失败：" , ex);
        }
        return r("成功删除" + count + "个用户");
    }

    /**
     * 获取一个用户的密码
     * 
     * @param userId
     * @return
     */
    public OperResult<Auth> getAuthInfoById(String userId) {
        Auth auth = null;
        try {
            auth = userDao.authById(userId);
        } catch (Exception ex) {
            LOGGER.error("get auth info of [" + userId + "] got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询用户权限信息失败：" , ex);
        }
        if (auth == null || StringUtils.isBlank(auth.password)) {
            return r(ErrorCode.DbQueryFail, "查询用户权限信息失败，请检查参数");
        }
        return r(auth);
    }

    /**
     * user可以是account或id，都支持。account中必须包含字幕，id中必须全为数字。
     * @param user
     * @param password
     * @param userIp
     * @return
     */
    public OperResult<Auth> auth(String user, String password, String userIp) {
        Auth auth = null;
        try {
            if (IdUtils.isUserAccountLegal(user)) {
                auth = userDao.authByAccount(user);
            } else if (IdUtils.isUserIdLegal(user)) {
                auth = userDao.authById(user);
            } else {
                return r(ErrorCode.InvalidParam, "不是合法的用户Id或Account：" + user);
            }
        } catch (Exception ex) {
            LOGGER.error("auth uesr [" + user + "|" + password + "] got exception:", ex);
            return r(ErrorCode.DbQueryFail, "验证用户权限信息失败：" , ex);
        }
        if (auth == null || StringUtils.isEmpty(auth.password)) {
            return r(ErrorCode.DbQueryFail, "查询用户权限信息失败，或用户密码设置不合法，请检查参数");
        }
        if (!auth.password.equalsIgnoreCase(password)) {
            return r(ErrorCode.WrongPassword, "用户密码错误");
        }
        auth = AuthUtils.genPassToken(auth, userIp);
        return r(auth);
    }

    /**
     * 获取一个用户的信息
     * 
     * @param id
     * @return
     */
    public OperResult<User> getUserById(String id) {
        UserRaw raw = null;
        try {
            raw = userDao.getUserById(id);
        } catch (Exception ex) {
            LOGGER.error("get user info by id " + id + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找用户信息失败：" , ex);
        }
        if (raw == null) {
            return r(ErrorCode.DbQueryFail, "未查找到此用户，请检查参数");
        }
        return buildUser(raw);
    }

    /**
     * 获取一个用户的信息
     * 
     * @param account
     * @return
     */
    public OperResult<User> getUserByAccount(String account) {
        UserRaw raw = null;
        try {
            raw = userDao.getUserByAccount(account);
        } catch (Exception ex) {
            LOGGER.error("get user info by account " + account + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找用户信息失败：" , ex);
        }
        if (raw == null) {
            return r(ErrorCode.DbQueryFail, "未查找到此用户，请检查参数");
        }
        return buildUser(raw);
    }

    /**
     * 通过raw构建role，多次读表
     * 
     * @param raw
     * @return
     */
    private OperResult<User> buildUser(UserRaw raw) {
        LOGGER.debug("building user data: {}...", raw);
        User user = new User(raw);
        if (raw.catCode >= 0) {
            UserCategory cat = UserCategory.fromCode(raw.catCode);
            if (cat == null) {
                return r(ErrorCode.InvalidParam, "用户的catCode无效：" + raw.catCode);
            }
            user.setCat(cat);
        }
        if (raw.posCode >= 0) {
            Position pos = Positions.getInstance().getPosition(raw.posCode);
            if (pos == null) {
                return r(ErrorCode.InvalidParam, "用户的posCode无效：" + raw.posCode);
            }
            user.setPos(pos);
        }
        if (raw.statCode >= 0) {
            UserStatus stat = UserStatus.fromCode(raw.statCode);
            if (stat == null) {
                return r(ErrorCode.InvalidParam, "用户的statCode无效：" + raw.statCode);
            }
            user.setStat(stat);
        }
        try {
            if (raw.froleCode >= 0) {
                FuncRoleInfo frole = funcRoleDao.find(raw.froleCode);
                if (frole == null) {
                    return r(ErrorCode.InvalidParam, "用户的froleCode无效：" + raw.froleCode);
                }
                user.setFrole(frole);
            }
            if (raw.droleCode >= 0) {
                DataRoleInfo drole = dataRoleDao.find(raw.droleCode);
                if (drole == null) {
                    return r(ErrorCode.InvalidParam, "用户的dorleCode无效：" + raw.droleCode);
                }
                user.setDrole(drole);
            }
            if (!StringUtils.isBlank(raw.corpId)) {
                Corp corp = corpDao.find(raw.corpId);
                if (corp == null) {
                    return r(ErrorCode.InvalidParam, "用户的corpId无效：" + raw.corpId);
                }
                user.setCorp(corp);
            }
            if (!StringUtils.isBlank(raw.corpId)) {
                Dept dept = deptDao.find(raw.deptId);
                if (dept == null) {
                    return r(ErrorCode.InvalidParam, "用户的deptId无效：" + raw.deptId);
                }
                user.setDept(dept);
            }
        } catch (Exception ex) {
            LOGGER.error("build raw " + raw + " to user got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询用户信息失败：", ex);
        }
        return r(user);
    }

    /**
     * 非常重量级的函数
     * 
     * @param userIds
     * @return
     */
    private OperResult<List<User>> buildUsers(List<UserRaw> raws, int total) {
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
            LOGGER.error("query user related info got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找用户相关信息失败：" , ex);
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
        return r(res, total);
    }

    /**
     * @param listParam
     * @return
     */
    public OperResult<List<User>> queryUsers(QueryListParam param, String myCorpId) {
        List<UserRaw> raws = Collections.emptyList();
        UserQueryParam query = new UserQueryParam()
                .setName(param.getLikeField("name", ""))
                .setCorpId(param.getStringField("corpId", ""))
                .setDeptId(param.getStringField("deptId", ""))
                .setPosCode(param.getIntField("posCode", -1))
                .setFroleCode(param.getIntField("froleCode", -1))
                .setDroleCode(param.getIntField("droleCode", -1))
                .setMyCorpId(myCorpId)
                .setFrom((param.page - 1) * param.size);
        query.setTo(query.from + param.size);
        List<String> suppliers = Collections.emptyList();
        List<String> subcorps = Collections.emptyList();
        try {
            suppliers = supplierDao.getSuppliers(myCorpId);
            subcorps = corpDao.subCorpIds(myCorpId);
        } catch (Exception ex) {
            LOGGER.error("query suppliers/subcorps of " + myCorpId + " got exception:", ex);
        }
        query.setSuppliers(suppliers).setSubcorps(subcorps);
        LOGGER.debug("query user by param: {} {}", query, myCorpId);
        try {
            raws = param.q == QueryType.or ? userDao.queryAllUsersByOr(query)
                    : userDao.queryAllUsersByAnd(query);
        } catch (Exception ex) {
            LOGGER.error("query users of " + param + " got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询用户列表失败：" , ex);
        }
        int total = raws.size();
        raws = Utils.sublist(query.from, query.to, raws);
        return buildUsers(raws, total);
    }

    /**
     * @param userIds
     * @return
     * @throws ServiceException
     */
    public OperResult<List<UserCard>> getUserCards(List<String> userIds) throws ServiceException {
        OperResult<List<UserCardRaw>> r = getUserCardRaws(userIds);
        if (r.error != ErrorCode.Success) {
            return new OperResult<List<UserCard>>(r.error, r.reason);
        }
        return buildUserCards(r.result);
    }

    private OperResult<List<UserCard>> buildUserCards(List<UserCardRaw> raws) {
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
            LOGGER.error("query user related info got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找用户相关信息失败：" , ex);
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
        return r(res);
    }


    /**
     * 获取用户名片原始信息
     * 
     * @param userIds
     * @return
     */
    private OperResult<List<UserCardRaw>> getUserCardRaws(List<String> userIds) {
        List<UserCardRaw> cards = null;
        try {
            cards = userDao.getUserCardsById(userIds);
        } catch (SQLException ex) {
            LOGGER.error("query user card raws of " + userIds + " got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询用户信息失败");
        }
        if (cards == null || cards.size() == 0) {
            return r(ErrorCode.DbQueryFail, "未查询到相关用户信息，请检查参数");
        }
        return r(cards);
    }

    /**
     * 获取所有的用户，仅用于调试
     * 
     * @return
     */
    public OperResult<List<User>> all() {
        List<UserRaw> raws = Collections.emptyList();
        try {
            raws = userDao.all();
        } catch (Exception ex) {
            LOGGER.error("query all users got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询所有用户信息失败");
        }
        if (raws == null || raws.size() == 0) {
            return r(ErrorCode.DbQueryFail, "未查到任何用户");
        }
        return buildUsers(raws, raws.size());
    }

    public OperResult<Integer> countUsersInDepts(List<String> deptIds) {
        try {
            return r(userDao.countUserOfDepts(deptIds));
        } catch (Exception ex) {
            LOGGER.error("count users in depts " + deptIds + " got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询部门列表中的用户个数信息失败");
        }
    }

    public OperResult<Integer> countUsersInCorps(List<String> corpIds) {
        try {
            return r(userDao.countUserOfCorps(corpIds));
        } catch (Exception ex) {
            LOGGER.error("count users in depts " + corpIds + " got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询公司列表中的用户个数信息失败：" , ex);
        }
    }

    /**
     * 更新一组用中的功能角色
     * 
     * @param usersToDel
     * @param usersToAdd
     * @param roleCode
     */
    public OperResult<Boolean> updateUsersFuncRole(List<String> usersToDel, List<String> usersToAdd, int roleCode) {
        try {
            for (String id: usersToDel) {
                userDao.updateFuncRole(id, -1);
            }
            for (String id: usersToAdd) {
                userDao.updateFuncRole(id, roleCode);
            }
        } catch (Exception ex) {
            LOGGER.error("update users func role got exception:", ex);
            return r(ErrorCode.DbUpdateFail, "更新用户功能角色属性失败：" , ex);
        }
        return r(false);
    }


    /**
     * 更新一组用中的数据角色
     * 
     * @param usersToDel
     * @param usersToAdd
     * @param roleCode
     */
    public OperResult<Boolean> updateUsersDataRole(List<String> usersToDel, List<String> usersToAdd, int roleCode) {
        try {
            for (String id: usersToDel) {
                userDao.updateDataRole(id, -1);
            }
            for (String id: usersToAdd) {
                userDao.updateDataRole(id, roleCode);
            }
        } catch (Exception ex) {
            LOGGER.error("update users func role got exception:", ex);
            return r(ErrorCode.DbUpdateFail, "更新用户功能角色属性失败：" , ex);
        }
        return r(false);
    }

    public OperResult<List<UserCard>> getUsersInOrgWithFroleOrNoFrole(String corpId, String deptId, int froleCode) {
        List<UserCardRaw> raws = Collections.emptyList();
        try {
            if (StringUtils.isBlank(deptId)) {
                raws = userDao.getUserCardsOfCorpWithFroleOrNoFrole(corpId, froleCode);
            } else {
                raws = userDao.getUserCardsOfDeptWithFroleOrNoFrole(corpId, deptId, froleCode);
            }
        } catch (Exception ex) {
            LOGGER.error("get usres in org " + corpId + ", " + deptId + " got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询公司/部门中的用户列表失败：" , ex);
        }
        return buildUserCards(raws);
    }

    public OperResult<List<UserCard>> getUsersInOrgWithDroleOrNoDrole(String corpId, String deptId, int droleCode) {
        List<UserCardRaw> raws = Collections.emptyList();
        try {
            if (StringUtils.isBlank(deptId)) {
                raws = userDao.getUserCardsOfCorpWithDroleOrNoDrole(corpId, droleCode);
            } else {
                raws = userDao.getUserCardsOfDeptWithDroleOrNoDrole(corpId, deptId, droleCode);
            }
        } catch (Exception ex) {
            LOGGER.error("get users in org " + corpId + ", " + deptId + " got exception:", ex);
            return r(ErrorCode.DbQueryFail, "查询公司/部门中的用户列表失败：" , ex);
        }
        return buildUserCards(raws);
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