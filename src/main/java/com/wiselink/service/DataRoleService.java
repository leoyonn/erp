/**
 * DataRoleService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午10:02:44
 */
package com.wiselink.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.dao.DataRoleInfoDAO;
import com.wiselink.dao.DataRoleScopesDAO;
import com.wiselink.dao.DataRoleUsersDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.Org;
import com.wiselink.model.role.DataLevel;
import com.wiselink.model.role.DataLevels;
import com.wiselink.model.role.DataRole;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.user.UserCard;
import com.wiselink.result.Checked;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;

/**
 * 
 * @author leo
 */
@Service
public class DataRoleService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataRoleService.class);

    private static final DataLevels levels = DataLevels.getInstance();

    @Autowired
    private DataRoleInfoDAO droleDao;

    @Autowired
    private DataRoleScopesDAO droleScopesDao;

    @Autowired
    private DataRoleUsersDAO droleUsersDao;

    @Autowired
    private UserService userService;

    @Autowired
    private CorpService corpService;
    
    @Autowired
    private DeptService deptService;
    
    public DataRoleService() {
        LOGGER.info("Data role service init...");
    }

    /**
     * 获取一个数据角色的完整信息
     * @param code
     * @return
     * @throws ServiceException
     */
    public OperResult<DataRole> getDataRole(int code) {
        OperResult<DataRole> r = getDataRoleInfo(code);
        if (r.error != ErrorCode.Success) {
            return r;
        }
        return buildRoleScopesAndUsers(r);
    }

    /**
     * 获取一个数据角色的基本信息
     * @param code
     * @return
     */
    public OperResult<DataRole> getDataRoleInfo(int code) {
        DataRoleInfo droleInfo = null;
        try {
            droleInfo = droleDao.find(code);
        } catch (Exception ex) {
            LOGGER.error("get data role info of " + code + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "获取数据角色信息失败：" , ex);
        }
        if (droleInfo == null) {
            return r(ErrorCode.DbQueryFail, "获取数据角色信息失败，请检查参数");
        }
        return r(new DataRole(fillLevelName(droleInfo)));
    }

    private DataRoleInfo fillLevelName(DataRoleInfo info) {
        return info.setLevelName(levels.getLevel(info.levelCode).name);
    }

    /**
     * 获取一个角色#drole 对应的所有权限范围scope和用户信息
     * 
     * @param r
     * @return
     */
    public OperResult<DataRole> buildRoleScopesAndUsers(OperResult<DataRole> r) {
        // get all sub-corps/depts, and filter all that this role contains.
        DataRole drole = r.result;
        OperResult<List<Corp>> allCorps = corpService.allSubCorps(drole.info.corpId);
        if (allCorps.error != ErrorCode.Success) {
            return r.setError(allCorps.error).setReason(allCorps.reason).setResult(null);
        }
        OperResult<List<Dept>> allDepts = deptService.allDepts(drole.info.corpId);
        if (allDepts.error != ErrorCode.Success) {
            return r.setError(allDepts.error).setReason(allDepts.reason).setResult(null);
        }
        List<String> scopeIds = Collections.emptyList();
        try {
            scopeIds = droleScopesDao.getScopes(drole.info.code);
        } catch (Exception ex) {
            LOGGER.error("get data role scopse " + drole + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询数据角色权限范围失败：" , ex);
        } 
        List<Checked<Org>> checkedScopes = checkScopes(allCorps.result, allDepts.result, scopeIds);

        // get all users in this corp/user and check for frole
        OperResult<List<UserCard>> users = userService.getUsersInOrgWithDroleOrNoDrole(
                drole.info.corpId, drole.info.deptId, drole.info.code);
        if (users.error != ErrorCode.Success) {
            return r.setError(users.error).setReason(users.reason).setResult(null);
        }
        List<String> droleUserIds;
        try {
            droleUserIds = droleUsersDao.getUsers(drole.info.code);
        } catch (Exception ex) {
            LOGGER.error("get data role users " + drole + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询数据角色用户失败：" , ex);
        }
        List<Checked<UserCard>> checkedUsers = checkUsers(users.result, droleUserIds);
        return r.setResult(drole.setScopes(checkedScopes).setUsers(checkedUsers));
    }

    /**
     * 获取公司所属所有数据角色列表
     * @return
     */
    public OperResult<List<DataRoleInfo>> allDataRoles(String corpId) {
        List<DataRoleInfo> list = Collections.emptyList();
        try {
            list = droleDao.all(corpId);
        } catch (Exception ex) {
            LOGGER.error("get data roles got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询数据角色列表失败：" , ex);
        }
        for (DataRoleInfo info: list) {
            fillLevelName(info);
        }
        return r(list);
    }

    public OperResult<List<DataRoleInfo>> allDataRoles() {
        List<DataRoleInfo> list = Collections.emptyList();
        try {
            list = droleDao.all();
        } catch (Exception ex) {
            LOGGER.error("get data roles got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询数据角色列表失败：" , ex);
        }
        for (DataRoleInfo info: list) {
            fillLevelName(info);
        }
        return r(list);
    }

    /**
     * 获取所有的数据权限级别
     * @return
     */
    public OperResult<Collection<DataLevel>> levels() {
        return r(levels.allLevels());
    }

    /**
     * 添加一个新的数据角色
     * 
     * @param info
     * @return
     */
    public OperResult<DataRole> newDataRole(DataRoleInfo info) {
        try {
            if (!droleDao.add(info)) {
                return r(ErrorCode.DbInsertFail, "插入数据角色失败，请检查参数");
            }
        } catch (Exception ex) {
            LOGGER.error("new data role " + info + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "插入数据角色失败：" , ex);
        }
        DataRoleInfo droleInfo = null;
        try {
            droleInfo = droleDao.findByName(info.name);
        } catch (Exception ex) {
            LOGGER.error("get data role by name " + info + " got exception", ex);
            return r(ErrorCode.DbUpdateFail, "读取数据角色失败：" , ex);
        }
        if (droleInfo == null) {
            return r(ErrorCode.DbUpdateFail, "读取数据角色失败，请检查参数");
        }
        return r(new DataRole(fillLevelName(droleInfo)));
    }

    /**
     * 修改一个数据角色对应的信息
     * @param info
     * @return
     */
    public OperResult<DataRole> updateDataRole(DataRoleInfo info) {
        try {
            if (!droleDao.update(info)) {
                return r(ErrorCode.DbInsertFail, "更新数据角色失败，请检查参数");
            }
        } catch (Exception ex) {
            LOGGER.error("update data role " + info + " got exception", ex);
            return r(ErrorCode.DbUpdateFail, "更新数据角色失败：" , ex);
        }
        return getDataRole(info.code);
    }

    public OperResult<DataRole> updateDataRole(int roleCode, List<String> scopesToDel,
            List<String> scopesToAdd, List<String> usersToDel, List<String> usersToAdd) {
        OperResult<DataRole> r = getDataRoleInfo(roleCode);
        if (r.error != ErrorCode.Success) {
            return r;
        }
        try {
            for (String orgId: scopesToDel) {
                droleScopesDao.delete(roleCode, orgId);
            }
            for (String orgId: scopesToAdd) {
                droleScopesDao.addScopeToRole(roleCode, orgId);
            }
            for (String userId: usersToDel) {
                droleUsersDao.delete(roleCode, userId);
            }
            for (String userId: usersToAdd) {
                droleUsersDao.addUserToRole(roleCode, userId);
            }
        } catch (Exception ex) {
            LOGGER.error("modify data role scopse and users: " + r.result + " got exception", ex);
            return r(ErrorCode.DbUpdateFail, "更新功能角色用户/功能列表失败：" , ex);
        }
        userService.updateUsersFuncRole(usersToDel, usersToAdd, roleCode);
        return buildRoleScopesAndUsers(r);
    }

    /**
     * @param code
     */
    public OperResult<Boolean> delete(int code) {
        try {
            if (droleUsersDao.getUsers(code).size() > 0) {
                return r(ErrorCode.DbDeleteFail, "尚有用户在此功能角色中，不可删除");
            }
            boolean ok = droleDao.delete(code);
            LOGGER.debug("deleted data fole:{}: {}", code, ok);
            if (!ok) {
                LOGGER.error("delete data role " + code + " failed");
                return r(ErrorCode.DbInsertFail, "删除数据角色失败，请检查参数");
            }
            ok = droleScopesDao.deleteAll(code);
            LOGGER.debug("deleted data fole funcs:{}: {}", code, ok);
        } catch (Exception ex) {
            LOGGER.error("delete data role " + code + " got exception", ex);
            return r(ErrorCode.DbDeleteFail, "删除数据角色失败：" , ex);
        }
        return r(true);
    }

    private List<Checked<Org>> checkScopes(List<Corp> allCorps, List<Dept> allDepts, List<String> toCheck) {
        Map<String, Checked<Org>> checkedScopes = new TreeMap<String, Checked<Org>>();
        for (Corp c: allCorps) {
            checkedScopes.put(c.getId(), new Checked<Org>(c, false));
        }
        for (Dept d: allDepts) {
            checkedScopes.put(d.getId(), new Checked<Org>(d, false));
        }
        for (String id: toCheck) {
            Checked<Org> cs = checkedScopes.get(id);
            if (cs == null) {
                LOGGER.warn("no such org: {}", cs);
            } else {
                cs.setChecked(true);
            }
        }
        return new ArrayList<Checked<Org>>(checkedScopes.values());
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
        return new ArrayList<Checked<UserCard>>(checked.values());
    }

}