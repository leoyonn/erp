/**
 * FuncService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午2:46:06
 */
package com.wiselink.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wiselink.dao.FuncRoleFuncsDAO;
import com.wiselink.dao.FuncRoleInfoDAO;
import com.wiselink.dao.FuncRoleUsersDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.role.Func;
import com.wiselink.model.role.FuncModule;
import com.wiselink.model.role.FuncModules;
import com.wiselink.model.role.FuncRole;
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.model.user.UserCard;

/**
 * 功能角色相关的服务
 * 
 * @author leo
 */
@Service
public class FuncRoleService {
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
     * @param code
     * @return
     * @throws ServiceException
     */
    public FuncRole getFuncRole(int code) throws ServiceException {
        FuncRole role = getFuncRoleInfo(code);
        if (role == null) {
            return null;
        }
        return getFuncRoleList(role);
    }

    /**
     * 获取一个功能角色的基本信息
     * @param code
     * @return
     * @throws ServiceException
     */
    public FuncRole getFuncRoleInfo(int code) throws ServiceException {
        try {
            FuncRoleInfo froleInfo = froleDao.find(code);
            if (froleInfo == null) {
                return null;
            }
            LOGGER.debug("get func role info: {}", froleInfo);
            return new FuncRole(froleInfo);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取一个角色#frole对应的所有功能和用户信息
     * @param frole
     * @return
     * @throws ServiceException 
     */
    public FuncRole getFuncRoleList(FuncRole frole) throws ServiceException {
        try {
            List<String> userIds = froleUsersDao.getUsers(frole.info.code);
            List<UserCard> users = userService.getUserCards(userIds);
            List<Integer> funcCodes = froleFuncsDao.getFuncs(frole.info.code);
            List<Func> funcs = getFuncs(funcCodes);
            frole.setFuncs(funcs).setUsers(users);
            LOGGER.debug("get func role: {}", frole);
            return frole;
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取功能角色列表，从code为 #from 开始取 #num 个
     * @param from
     * @param num
     * @return
     * @throws ServiceException
     */
    public List<FuncRoleInfo> getFuncRoles(int from, int num) throws ServiceException {
        try {
            return froleDao.list(from, num);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取所有的功能模块
     * @return
     */
    public Collection<FuncModule> allModules() {
        return modules.allModules();
    }

    /**
     * 获取code指定的功能模块
     * 
     * @param code
     * @return
     */
    public FuncModule getModule(int code) {
        return modules.getModule(code);
    }

    /**
     * 批量查询功能代码对应的功能
     * @param funcCodes
     * @return
     */
    public List<Func> getFuncs(List<Integer> funcCodes) {
        List<Func> funcs = new ArrayList<Func>();
        for (int code: funcCodes) {
            funcs.add(modules.getFunc(code));
        }
        return funcs;
    }

    /**
     * 添加一个新的功能角色
     * 
     * @param code
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     * @throws ServiceException
     */
    public FuncRole newFuncRole(String name, String desc, String corpId, String deptId, String creatorId) throws ServiceException {
        boolean ok = false;
        try {
            ok = froleDao.add(name, desc, corpId, deptId, creatorId);
        } catch (Exception ex) { // SQLException, DataAccessException
            throw new ServiceException(ex);
        }
        if (!ok) {
            throw new ServiceException("new func role failed.");
        }
        try {
            FuncRoleInfo frole = froleDao.findByName(name);
            LOGGER.debug("add func role success: {}.", frole);
            return new FuncRole(frole);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 修改一个功能角色对应的信息
     * @param code
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @return
     * @throws ServiceException
     */
    public FuncRole updateFuncRole(int code, String name, String desc, String corpId, String deptId) throws ServiceException {
        boolean ok = false;
        try {
            ok = froleDao.update(code, name, desc, corpId, deptId);;
        } catch (Exception ex) { // SQLException, DataAccessException
            throw new ServiceException(ex);
        }
        if (!ok) {
            throw new ServiceException("update func role failed.");
        }
        return getFuncRole(code);
    }

    /**
     * 设置一个功能能角色#code对应的功能和用户。
     * <p>TODO 性能：rose.dao框架似乎不能批量插入、删除
     * @param roleCode
     * @param funcsToDel
     * @param funcsToAdd
     * @param usersToDel
     * @param usersToAdd
     * @return 
     * @throws ServiceException
     */
    public FuncRole updateFuncRole(int roleCode, List<Integer> funcsToDel, List<Integer> funcsToAdd,
            List<String> usersToDel, List<String> usersToAdd) throws ServiceException {
        FuncRole role = getFuncRoleInfo(roleCode);
        if (role == null) {
            return null;
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
            throw new ServiceException(ex);
        }
        return getFuncRoleList(role);
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
}
