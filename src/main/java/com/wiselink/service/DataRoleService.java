/**
 * DataRoleService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午10:02:44
 */
package com.wiselink.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.dao.DataRoleInfoDAO;
import com.wiselink.dao.DataRoleScopesDAO;
import com.wiselink.dao.DataRoleUsersDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Org;
import com.wiselink.model.role.DataLevel;
import com.wiselink.model.role.DataLevels;
import com.wiselink.model.role.DataRole;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.model.user.UserCard;

/**
 * 
 * @author leo
 */
@Service
public class DataRoleService {
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
    
    public DataRoleService() {
        LOGGER.info("Data role service init...");
    }

    /**
     * 获取一个数据角色的完整信息
     * @param code
     * @return
     * @throws ServiceException
     */
    public DataRole getDataRole(int code) throws ServiceException {
        DataRole role = getDataRoleInfo(code);
        if (role == null) {
            return null;
        }
        return getDataRoleList(role);
    }

    /**
     * 获取一个数据角色的基本信息
     * @param code
     * @return
     * @throws ServiceException
     */
    public DataRole getDataRoleInfo(int code) throws ServiceException {
        try {
            DataRoleInfo droleInfo = droleDao.find(code);
            if (droleInfo == null) {
                return null;
            }
            LOGGER.debug("get data role info: {}", droleInfo);
            return new DataRole(droleInfo);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取一个角色#drole 对应的所有权限范围scope和用户信息
     * @param drole
     * @return
     * @throws ServiceException 
     */
    public DataRole getDataRoleList(DataRole drole) throws ServiceException {
        try {
            List<String> userIds = droleUsersDao.getUsers(drole.info.code);
            List<UserCard> users = userService.getUserCards(userIds);
            List<String> scopeIds = droleScopesDao.getScopes(drole.info.code);
            List<Org> scopes = corpService.getOrgs(scopeIds);
            drole.setScopes(scopes).setUsers(users);
            LOGGER.debug("get data role: {}", drole);
            return drole;
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取数据角色列表，从code为 #from 开始取 #num 个
     * @param from
     * @param num
     * @return
     * @throws ServiceException
     */
    public List<DataRoleInfo> getDataRoles(int from, int num) throws ServiceException {
        try {
            return droleDao.list(from, num);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取所有的数据权限级别
     * @return
     */
    public Collection<DataLevel> levels() {
        return levels.allLevels();
    }

    /**
     * 添加一个新的数据角色
     * 
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     * @throws ServiceException
     */
    public DataRole newDataRole(String name, String desc, int levelCode, String corpId, String deptId,
            String creatorId) throws ServiceException {
        boolean ok = false;
        try {
            ok = droleDao.add(name, desc, levelCode, corpId, deptId, creatorId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        if (!ok) {
            throw new ServiceException("new Data role failed.");
        }
        try {
            DataRoleInfo droleInfo = droleDao.findByName(name);
            LOGGER.debug("add Data role success: {}.", droleInfo);
            return new DataRole(droleInfo);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 修改一个数据角色对应的信息
     * @param code
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @return
     * @throws ServiceException
     */
    public DataRole updateDataRole(int code, String name, String desc, int levelCode, String corpId, String deptId) throws ServiceException {
        boolean ok = false;
        try {
            ok = droleDao.update(code, name, desc, levelCode, corpId, deptId);;
        } catch (Exception ex) { // SQLException, DataAccessException
            throw new ServiceException(ex);
        }
        if (!ok) {
            throw new ServiceException("update data role failed.");
        }
        return getDataRole(code);
    }

    /**
     * 设置一个数据角色#code对应的权限范围和用户。
     * <p>TODO 性能：rose.dao框架似乎不能批量插入、删除
     * @param code
     * @param scopesToDel
     * @param scopesToAdd
     * @param usersToDel
     * @param usersToAdd
     * @return 
     * @throws ServiceException
     */
    public DataRole updateDataRole(int roleCode, List<String> scopesToDel, List<String> scopesToAdd,
            List<String> usersToDel, List<String> usersToAdd) throws ServiceException {
        DataRole role = getDataRoleInfo(roleCode);
        if (role == null) {
            return null;
        }
        try {
            for (String orgId: scopesToDel) {
                droleScopesDao.delete(roleCode, orgId);
            }
            for (String orgId: scopesToDel) {
                droleScopesDao.addScopeToRole(roleCode, orgId);
            }
            for (String userId: usersToDel) {
                droleUsersDao.delete(roleCode, userId);
            }
            for (String userId: usersToDel) {
                droleUsersDao.addUserToRole(roleCode, userId);
            }
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return getDataRoleList(role);
    }

    /**
     * @param code
     * @throws ServiceException
     */
    public boolean delete(int code) throws ServiceException {
        try {
            boolean ok = droleDao.delete(code);
            LOGGER.debug("deleted data fole:{}: {}", code, ok);
            if (!ok) {
                return false;
            }
            ok = droleScopesDao.deleteAll(code);
            LOGGER.debug("deleted data fole funcs:{}: {}", code, ok);
            ok = droleUsersDao.deleteAll(code);
            LOGGER.debug("deleted data fole users:{}: {}", code, ok);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return true;
    }
}