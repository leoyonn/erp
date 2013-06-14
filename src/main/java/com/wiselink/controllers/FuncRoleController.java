/**
 * RoleController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午3:29:21
 */
package com.wiselink.controllers;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.management.relation.Role;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.base.Constants;
import com.wiselink.dao.FuncRoleFuncsDAO;
import com.wiselink.dao.FuncRoleInfoDAO;
import com.wiselink.dao.FuncRoleUsersDAO;
import com.wiselink.model.UserCard;
import com.wiselink.model.role.Func;
import com.wiselink.model.role.FuncModule;
import com.wiselink.model.role.FuncModules;
import com.wiselink.model.role.FuncRole;
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.service.FuncService;
import com.wiselink.service.UserService;

/**
 * 用户角色相关的操作，例如创建角色、修改添加角色人物等
 * @author leo
 */
@Path("frole")
public class FuncRoleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncRoleController.class);

    @Autowired
    private FuncRoleInfoDAO froleDao;

    @Autowired
    private FuncRoleFuncsDAO froleFuncsDao;

    @Autowired
    private FuncRoleUsersDAO froleUsersDao;

    @Autowired
    private UserService userService;
    
    @Autowired
    private FuncService funcService;
    
    /**
     * 获取#code指定的功能角色的数据，包括功能清单和人员清单。<p>
     * 如果#code < 0，则返回所有的module列表
     * 
     * @param code
     * @return
     */
    @Get("1")
    public String getFrole(@Param("code")int code) {
        if (code < 0) {
            return module(code);
        }
        try {
            FuncRoleInfo froleInfo = froleDao.find(code);
            List<String> userIds = froleUsersDao.getUsers(code);
            List<UserCard> users = userService.getUsers(userIds);
            List<Integer> funcCodes = froleFuncsDao.getFuncs(code);
            List<Func> funcs = funcService.getFuncs(funcCodes);
            FuncRole frole = new FuncRole(froleInfo).setFuncs(funcs).setUsers(users);
            LOGGER.debug("get func role: {}", frole);
            return successResult(frole.toJson());
        } catch (SQLException ex) {
            LOGGER.error("add func role " + code + " got exception!", ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
    }

    @Post("new")
    public String newFrole(@Param("name") String name, @Param("desc") String desc) {
        LOGGER.debug("add func role of name: {}, desc: {}.", name, desc);
        // TODO full file this: get creator from cookie
        if (froleDao.add(0, name, desc, "", "", null)) {
            FuncRoleInfo frole = null;
            try {
                frole = froleDao.findByName(name);
            } catch (SQLException ex) {
                LOGGER.error("add func role " + name + "|" + desc + " got exception!", ex);
            }
            if (frole != null) {
                LOGGER.debug("add func role success: {}.", frole);
                return successResult(frole.toJson());
            }
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED, "角色添加失败，请确认角色名不重复，或联系系统管理员");
    }
    
    /**
     * list all #num func-roles sorted by {@link Role#code} from #from 
     * 如果需要listall，设置from和num都为负值
     * @param from
     * @param num
     * @return
     */
    @Get("list")
    public String list(@Param("from") int from, @Param("num") int num) {
        if (num < 0) {
            num = Constants.MAX_ROLE_NUM;
        }
        List<FuncRoleInfo> roles = froleDao.list(from, num);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("list role from: {}, num: {}, got {}.", new Object[]{from, num, roles});
        }
        if (roles == null || roles.size() == 0) {
            return failResult(ApiStatus.DATA_EMPTY);
        }
        JSONArray array = new JSONArray();
        for (FuncRoleInfo role: roles) {
            array.add(role.toJson());
        }
        LOGGER.debug("list role returning{}.", array);
        return successResult(array.toString());
    }

    /**
     * 获取所有的功能角色列表，相当于调用 <code>list(-1, -1)</code>
     * @return
     */
    @Get("all")
    public String all() {
        return list(-1, -1);
    }

    /**
     * 获取#code指定的功能模块信息及其所有的功能
     * @param code  如果code<0则返回所有的modules
     * @return
     */
    @Get("module")
    public String module(@Param("code") int code) {
        LOGGER.debug("query for module {}.", code);
        if (code >= 0) {
            FuncModule module = FuncModules.getInstance().getModule(code);
            LOGGER.debug("query for module {} got {}.", code, module);
            if (module == null) {
                return failResult(ApiStatus.INVALID_PARAMETER);
            } else {
                return successResult(module.toJson());
            }
        }
        Collection<FuncModule> allModules = funcService.allModules();
        LOGGER.debug("query for all-module {} got {}.", code, allModules);
        return successResult(allModules);
    }
}