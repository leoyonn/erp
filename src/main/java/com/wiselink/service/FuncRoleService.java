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
     * 获取功能角色列表，从code为 #from 开始取 #num 个
     * @param from
     * @param num
     * @return
     * @throws ServiceException
     */
    public List<FuncRoleInfo> getFuncRoles(int from, int num) throws ServiceException {
        try {
            return froleDao.list(from, num);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 设置一个功能能角色#code对应的功能和用户。
     * <p>TODO 性能：rose.dao框架似乎不能批量插入、删除
     * @param code
     * @param funcsToDel
     * @param funcsToAdd
     * @param usersToDel
     * @param usersToAdd
     * @throws ServiceException
     */
    public void updateFuncRole(int roleCode, List<Integer> funcsToDel, List<Integer> funcsToAdd,
            List<String> usersToDel, List<String> usersToAdd) throws ServiceException {
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
            for (String userId: usersToDel) {
                froleUsersDao.addFuncToRole(roleCode, userId);
            }
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取一个角色#roleCode 对应的所有功能信息
     * @param roleCode
     * @return
     * @throws ServiceException 
     */
    public FuncRole getFuncRole(int roleCode) throws ServiceException {
        try {
            FuncRoleInfo froleInfo = froleDao.find(roleCode);
            List<String> userIds = froleUsersDao.getUsers(roleCode);
            List<UserCard> users = userService.getUsers(userIds);
            List<Integer> funcCodes = froleFuncsDao.getFuncs(roleCode);
            List<Func> funcs = getFuncs(funcCodes);
            FuncRole frole = new FuncRole(froleInfo).setFuncs(funcs).setUsers(users);
            LOGGER.debug("get func role: {}", frole);
            return frole;
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 添加一个新的功能角色
     * 
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     * @throws ServiceException
     */
    public FuncRoleInfo newFuncRole(String name, String desc, String corpId, String deptId, String creatorId) throws ServiceException {
        boolean ok = false;
        try {
            ok = froleDao.add(0, name, desc, corpId, deptId, creatorId);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
        if (!ok) {
            throw new ServiceException("new func role failed.");
        }
        try {
            FuncRoleInfo frole = froleDao.findByName(name);
            LOGGER.debug("add func role success: {}.", frole);
            return frole;
        } catch (SQLException ex) {
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

}
