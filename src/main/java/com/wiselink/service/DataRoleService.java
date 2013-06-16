/**
 * DataRoleService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午10:02:44
 */
package com.wiselink.service;

import java.sql.SQLException;
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
import com.wiselink.model.UserCard;
import com.wiselink.model.org.Org;
import com.wiselink.model.role.DataLevel;
import com.wiselink.model.role.DataLevels;
import com.wiselink.model.role.DataRole;
import com.wiselink.model.role.DataRoleInfo;

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
    private OrgService orgService;
    
    public DataRoleService() {
        LOGGER.info("Data role service init...");
    }

    /**
     * 获取一个角色#roleCode 对应的所有功能信息
     * 
     * @param roleCode
     * @return
     * @throws ServiceException
     */
    public DataRole getDataRole(int roleCode) throws ServiceException {
        try {
            DataRoleInfo droleInfo = droleDao.find(roleCode);
            List<String> userIds = droleUsersDao.getUsers(roleCode);
            List<UserCard> users = userService.getUsers(userIds);
            List<String> scopeIds = droleScopesDao.getScopes(roleCode);
            List<Org> scopes = orgService.getOrgs(scopeIds);
            DataRole drole = new DataRole(droleInfo).setScopes(scopes).setUsers(users);
            LOGGER.debug("get Data role: {}", drole);
            return drole;
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 添加一个新的功能角色
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
    public DataRoleInfo newDataRole(String name, String desc, int levelCode, String corpId, String deptId,
            String creatorId) throws ServiceException {
        boolean ok = false;
        try {
            ok = droleDao.add(0, name, desc, levelCode, corpId, deptId, creatorId);
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
        if (!ok) {
            throw new ServiceException("new Data role failed.");
        }
        try {
            DataRoleInfo frole = droleDao.findByName(name);
            LOGGER.debug("add Data role success: {}.", frole);
            return frole;
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
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
     * 设置一个数据角色#code对应的权限范围和用户。
     * <p>TODO 性能：rose.dao框架似乎不能批量插入、删除
     * @param code
     * @param scopesToDel
     * @param scopesToAdd
     * @param usersToDel
     * @param usersToAdd
     * @throws ServiceException
     */
    public void updateDataRole(int roleCode, List<String> scopesToDel, List<String> scopesToAdd,
            List<String> usersToDel, List<String> usersToAdd) throws ServiceException {
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
                droleUsersDao.addDataToRole(roleCode, userId);
            }
        } catch (SQLException ex) {
            throw new ServiceException(ex);
        }
    }
}