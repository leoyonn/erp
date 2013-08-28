/**
 * FuncService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午2:46:06
 */
package com.wiselink.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wiselink.dao.FuncRoleFuncsDAO;
import com.wiselink.dao.FuncRoleInfoDAO;
import com.wiselink.dao.FuncRoleUsersDAO;
import com.wiselink.model.role.Func;
import com.wiselink.model.role.FuncModule;
import com.wiselink.model.role.FuncModules;
import com.wiselink.model.role.FuncRole;
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.model.user.UserCard;
import com.wiselink.result.Checked;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
import com.wiselink.utils.UserSorter;

/**
 * 功能角色相关的服务
 * 
 * @author leo
 */
@Service
public class FuncRoleService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncRoleService.class);

    private static final FuncModules modules = FuncModules.getInstance();

    @Autowired
    private FuncRoleInfoDAO froleDao;

    @Autowired
    private FuncRoleFuncsDAO froleFuncsDao;

    @Autowired
    private FuncRoleUsersDAO froleUsersDao;

    @Autowired
    private UserService userService;

    public FuncRoleService() {
        LOGGER.info("func service init...");
    }

    /**
     * 获取一个功能角色的完整信息
     * 
     * @param code
     * @return
     * @
     */
    public OperResult<FuncRole> getFuncRole(int code)  {
        OperResult<FuncRole> r = getFuncRoleInfo(code);
        if (r.error != ErrorCode.Success) {
            return r;
        }
        return buildRoleFuncsAndUsers(r);
    }

    /**
     * 获取一个功能角色的基本信息
     * 
     * @param code
     * @return
     */
    public OperResult<FuncRole> getFuncRoleInfo(int code) {
        FuncRoleInfo froleInfo = null;
        try {
            froleInfo = froleDao.find(code);
        } catch (Exception ex) {
            LOGGER.error("get func role " + code + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询功能角色信息失败：" , ex);
        }
        if (froleInfo == null) {
            return r(ErrorCode.DbQueryFail, "查询功能角色信息失败，请检查参数");
        }
        return r(new FuncRole(froleInfo));
    }

    /**
     * 获取一个角色#frole对应的所有功能和用户信息
     * 
     * @param frole
     * @return
     */
    public OperResult<FuncRole> buildRoleFuncsAndUsers(OperResult<FuncRole> r) {
        // get all module/funcs, and filter all that this role contains.
        FuncRole frole = r.result;
        List<Integer> funcCodes = Collections.emptyList();
        try {
            funcCodes = froleFuncsDao.getFuncs(frole.info.code);
        } catch (Exception ex) {
            LOGGER.error("get func role funcs " + frole + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询功能角色信息失败：" , ex);
        } 
        List<Checked<Func>> checkedFuncs = checkFuncs(funcCodes);

        // get all users in this corp/user and check for frole
        OperResult<List<UserCard>> users = userService.getUsersInOrgWithFroleOrNoFrole(
                frole.info.corpId, frole.info.deptId, frole.info.code);
        if (users.error != ErrorCode.Success) {
            return r.setError(users.error).setReason(users.reason).setResult(null);
        }
        List<String> froleUserIds;
        try {
            froleUserIds = froleUsersDao.getUsers(frole.info.code);
        } catch (Exception ex) {
            LOGGER.error("get func role users " + frole + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询功能角色信息失败：" , ex);
        }
        List<Checked<UserCard>> checkedUsers = checkUsers(users.result, froleUserIds);
        return r.setResult(frole.setFuncs(checkedFuncs).setUsers(checkedUsers));
    }

    public OperResult<List<FuncRoleInfo>> allFuncRoles() {
        List<FuncRoleInfo> list = Collections.emptyList();
        try {
            list = froleDao.all();
        } catch (Exception ex) {
            LOGGER.error("get all func roles got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询功能角色列表失败：" , ex);
        }
        return r(list);
    }
    
    public OperResult<List<FuncRoleInfo>> allFuncRoles(String corpId) {
        List<FuncRoleInfo> list = Collections.emptyList();
        try {
            list = froleDao.all(corpId);
        } catch (Exception ex) {
            LOGGER.error("get all func roles of " + corpId + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询功能角色列表失败：" , ex);
        }
        return r(list);
    }
    
    public OperResult<Integer> allFuncRolesCount() {
        try {
            int n = froleDao.count();
            return r(n);
        } catch (Exception ex) {
            LOGGER.error("count all func roles got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询功能角色个数失败：" , ex);
        }
    }

    /**
     * 获取所有的功能模块
     * 
     * @return
     */
    public OperResult<List<FuncModule>> allModules() {
        return r(new ArrayList<FuncModule>(modules.allModules()));
    }

    /**
     * 获取code指定的功能模块
     * 
     * @param code
     * @return
     */
    public OperResult<FuncModule> getModule(int code) {
        FuncModule m = modules.getModule(code);
        if (m == null) {
            return r(ErrorCode.InvalidParam, "无此code的module");
        }
        return r(m);
    }

    /**
     * 添加一个新的功能角色
     * 
     * @param info
     * @return
     * @
     */
    public OperResult<FuncRole> newFuncRole(FuncRoleInfo info) {
        try {
            if (!froleDao.add(info)) {
                return r(ErrorCode.DbInsertFail, "添加功能角色失败，请检查参数");
            }
            info = froleDao.findByName(info.name);
            return r(new FuncRole(info));
        } catch (Exception ex) { // SQLException, DataAccessException
            LOGGER.error("new func role" + info + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "添加功能角色失败：" , ex);
        }
    }

    /**
     * 修改一个功能角色对应的信息
     * @param info
     * @return
     * @
     */
    public OperResult<FuncRole> updateFuncRole(FuncRoleInfo info)  {
        try {
            if (!froleDao.update(info)) {
                return r(ErrorCode.DbInsertFail, "添加功能角色失败，请检查参数");
            }
        } catch (Exception ex) { // SQLException, DataAccessException
            LOGGER.error("new func role" + info + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "添加功能角色失败：" , ex);
        }
        return getFuncRole(info.code);
    }

    /**
     * 设置一个功能能角色#code对应的功能和用户。
     * <p>
     * TODO 性能：rose.dao框架似乎不能批量插入、删除
     * 
     * @param roleCode
     * @param funcsToDel
     * @param funcsToAdd
     * @param usersToDel
     * @param usersToAdd
     * @return @
     */
    public OperResult<FuncRole> updateFuncRole(int roleCode, List<Integer> funcsToDel,
            List<Integer> funcsToAdd, List<String> usersToDel, List<String> usersToAdd) {
        OperResult<FuncRole> r = getFuncRoleInfo(roleCode);
        if (r.error != ErrorCode.Success) {
            return r;
        }
        try {
            for (int funcCode: funcsToDel) {
                froleFuncsDao.delete(roleCode, funcCode);
            }
            for (int funcCode: funcsToAdd) {
                froleFuncsDao.addFuncToRole(roleCode, funcCode);
            }
            for (String userId: usersToDel) {
                froleUsersDao.delete(roleCode, userId);
            }
            for (String userId: usersToAdd) {
                froleUsersDao.addUserToRole(roleCode, userId);
            }
        } catch (Exception ex) {
            LOGGER.error("modify func role funcs and users: " + r.result + " got exception", ex);
            return r(ErrorCode.DbUpdateFail, "更新功能角色用户/功能列表失败：" , ex);
        }
        userService.updateUsersFuncRole(usersToDel, usersToAdd, roleCode);
        return buildRoleFuncsAndUsers(r);
    }

    /**
     * @param code
     */
    public OperResult<Boolean> deleteFuncRole(int code)  {
        try {
            if (froleUsersDao.getUsers(code).size() > 0) {
                return r(ErrorCode.DbDeleteFail, "尚有用户在此功能角色中，不可删除");
            }
            boolean ok = froleDao.delete(code);
            LOGGER.debug("deleted func fole:{}: {}", code, ok);
            if (!ok) {
                return r(ErrorCode.DbDeleteFail, "删除功能角色失败，请检查参数");
            }
            ok = froleFuncsDao.deleteAll(code);
            LOGGER.debug("deleted func fole funcs:{}: {}", code, ok);
        } catch (Exception ex) {
            LOGGER.error("delete func role " + code + " got exception", ex);
            return r(ErrorCode.DbDeleteFail, "删除功能角色失败：" , ex);
        }
        return r(true);
    }

    /**
     * WARNING: only for unittest or debug.
     * @throws SQLException 
     * @throws DataAccessException 
     */
    public void clearAll() throws DataAccessException, SQLException {
        froleDao.clear();
        froleFuncsDao.clear();
        froleUsersDao.clear();
    }


    /**
     * 批量查询功能代码对应的功能
     * @param funcCodes
     * @return
     */
    private List<Checked<Func>> checkFuncs(List<Integer> toCheck) {
        Map<Integer, Func> allFuncs = modules.allFuncsMap();
        Map<Integer, Checked<Func>> checkedFuncs = new TreeMap<Integer, Checked<Func>>();
        for (Map.Entry<Integer, Func> e: allFuncs.entrySet()) {
            checkedFuncs.put(e.getKey(), new Checked<Func>(e.getValue(), false));
        }
        for (int code: toCheck) {
            Checked<Func> cf = checkedFuncs.get(code);
            if (cf == null) {
                LOGGER.warn("no such func: {}", code);
            } else {
                cf.setChecked(true);
            }
        }
        return new ArrayList<Checked<Func>>(checkedFuncs.values());
    }

    private List<Checked<UserCard>> checkUsers(List<UserCard> all, List<String> toCheck) {
        Map<String, Checked<UserCard>> checked = new TreeMap<String, Checked<UserCard>>();
        for (UserCard u: all) {
            checked.put(u.id, new Checked<UserCard>(u, false));
        }
        for (String id: toCheck) {
            Checked<UserCard> cu = checked.get(id);
            if (cu == null) {
                LOGGER.warn("no such user: {}", id);
            } else {
                cu.setChecked(true);
            }
        }
        return UserSorter.sort(new ArrayList<Checked<UserCard>>(checked.values()), UserSorter.ByDeptId);
    }
}
