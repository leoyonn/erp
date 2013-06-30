/**
 * RoleController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午3:29:21
 */
package com.wiselink.controllers;

import java.util.Collection;
import java.util.List;

import javax.management.relation.Role;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.base.Constants;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.role.FuncModule;
import com.wiselink.model.role.FuncRole;
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.service.FuncRoleService;
import com.wiselink.service.UserService;
import com.wiselink.utils.CookieUtils;
import com.wiselink.utils.Utils;

/**
 * 用户角色相关的操作，例如创建角色、修改添加角色人物等
 * @author leo
 */
@Path("frole")
public class FuncRoleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncRoleController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private FuncRoleService froleService;

    /**
     * 获取#code指定的功能角色的数据，包括功能清单和人员清单。<p>
     * 如果#code < 0，则返回所有的module列表
     * 
     * @param code
     * @return
     */
    @Get("1")
    public String getFrole(@Param("code") int code) {
        if (code < 0) {
            return allFroles();
        }
        try {
            FuncRole frole = froleService.getFuncRole(code);
            if (frole == null) {
                return failResult(ApiStatus.INVALID_PARAMETER, "未检索导数据，请检查参数或联系管理员");
            }
            return successResult(frole.toJson());
        } catch (ServiceException ex) {
            LOGGER.error("add func role " + code + " got exception!", ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
    }
    /**
     * list all #num func-roles sorted by {@link Role#code} from #from 
     * 如果需要listall，设置from和num都为负值
     * @param from
     * @param num
     * @return
     */
    @Get("list")
    public String listFroles(@Param("from") int from, @Param("num") int num) {
        if (num < 0) {
            num = Constants.MAX_ROLE_NUM;
        }
        List<FuncRoleInfo> roles = null;
        try {
            roles = froleService.getFuncRoles(from, num);
        } catch (ServiceException ex) {
            LOGGER.error("list froles from " + from + " num " + num + " got exception!", ex);
            return failResult(ApiStatus.SERVICE_ERROR);
        }
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
    public String allFroles() {
        return listFroles(-1, -1);
    }

    /**
     * 获取#code指定的功能模块信息及其所有的功能
     * @param code  如果code<0则返回所有的modules
     * @return
     */
    @Get("module")
    public String module(@Param("code") int code) {
        LOGGER.debug("query for module {}.", code);
        if (code < 0) {
            return allModules();
        }
        FuncModule module = froleService.getModule(code);
        LOGGER.debug("query for module {} got {}.", code, module);
        if (module == null) {
            return failResult(ApiStatus.INVALID_PARAMETER);
        } else {
            return successResult(module.toJson());
        }
    }

    /**
     * 获取系统所有的功能模块
     * @return
     */
    @Get("modules")
    public String allModules() {
        Collection<FuncModule> allModules = froleService.allModules();
        LOGGER.debug("query for all-module got {}.", allModules);
        return successResult(allModules);
    }


    /**
     * 新建一个功能角色
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     */
    @SuppressWarnings("not completed|post")
    @Get("new")
    public String newFrole(Invocation inv, @Param("name") String name, @Param("desc") String desc,
            @Param("corpId") String corpId, @Param("deptId") String deptId, @Param("creatorId") String creatorId) {
        LOGGER.debug("add func role of name: {}, desc: {}.", name, desc);
        // TODO full file this: get creator from cookie
        CookieUtils.getCookie(inv, "user");
        try {
            FuncRole frole = froleService.newFuncRole(name, desc, corpId, deptId, creatorId);
            LOGGER.debug("add func role result: {}.", frole);
            if (frole == null) {
                return failResult(ApiStatus.INVALID_PARAMETER, "未检索导数据，请检查参数或联系管理员");
            }
            return successResult(frole.toJson());
        } catch (ServiceException ex) {
            LOGGER.error("add func role " + name + "|" + desc + " got exception!", ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED, "角色添加失败，请检查参数或联系管理员");
        }
    }

    /**
     * 修改一个功能角色的信息
     * @param inv
     * @param code
     * @param name
     * @param desc
     * @param corpId
     * @param deptId
     * @return
     */
    @SuppressWarnings("not completed|post")
    @Get("up/info")
    public String updateFroleInfo(Invocation inv, @Param("code") int code, @Param("name") String name, 
            @Param("desc") String desc, @Param("corpId") String corpId, @Param("deptId") String deptId) {
        LOGGER.debug("update func role info of name: {}, desc: {}.", name, desc);
        // TODO full file this: get creator from cookie
        CookieUtils.getCookie(inv, "user");
        try {
            FuncRole frole = froleService.updateFuncRole(code, name, desc, corpId, deptId);
            LOGGER.debug("update func role result: {}.", frole);
            if (frole == null) {
                return failResult(ApiStatus.INVALID_PARAMETER, "未检索导数据，请检查参数或联系管理员");
            }
            return successResult(frole.toJson());
        } catch (ServiceException ex) {
            LOGGER.error("add func role " + name + "|" + desc + " got exception!", ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED, "角色添加失败，请检查参数或联系管理员");
        }
    }
    
    /**
     * 更新功能角色#code对应的功能和用户
     * <p>
     * 注意参数说明 TODO 参数支持list？
     * 
     * @param code
     * @param funcsToDel
     *            要从角色中删除的功能列表，列表为用逗号隔开的func-code，如"0012,0123"
     * @param funcsToAdd
     *            要添加到角色中的功能列表，列表为用逗号隔开的func-code，如"0012,0123"
     * @param usersToDel
     *            要从角色中删除的用户列表，列表为用逗号隔开的userId，如“111,222,333"
     * @param usersToAdd
     *            要添加到角色中的用户列表，列表为用逗号隔开的userId，如“111,222,333"
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up/list")
    public String updateFroleList(@Param("code") int code,
            @Param("funcsToDel") String funcsToDel, @Param("funcsToAdd") String funcsToAdd, 
            @Param("usersToDel") String usersToDel, @Param("usersToAdd") String usersToAdd) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updating func role: {}|fd:{}|fa:{}; ud:{}|ua:{}", 
                    new Object[]{code, funcsToDel, funcsToAdd, usersToDel, usersToAdd});
        }
        List<Integer> fdel = Utils.parseArrayFromStrings(funcsToDel, ",");
        List<Integer> fadd = Utils.parseArrayFromStrings(funcsToAdd, ",");
        List<String> udel = Utils.split(usersToDel, ",");
        List<String> uadd = Utils.split(usersToAdd, ",");
        try {
            FuncRole role = froleService.updateFuncRole(code, fdel, fadd, udel, uadd);
            if (role == null) {
                return failResult(ApiStatus.INVALID_PARAMETER, "未检索导数据，请检查参数或联系管理员");
            }
            return successResult(role.toJson());
        } catch (ServiceException ex) {
            LOGGER.error("update func role " + code + " funcs " + funcsToDel + "|" + funcsToAdd
                    + " users " + usersToDel + "|" + usersToAdd + " got exception!", ex);
            return failResult(ApiStatus.SERVICE_ERROR);
        }
    }

    /**
     * 删除指定功能角色
     * @param code
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("del")
    public String delete(@Param("code") int code) {
        LOGGER.debug("deleting func role: {}", code);
        try {
            boolean ok = froleService.delete(code);
            if (!ok) {
                return failResult(ApiStatus.DATA_DELETE_FAILED);
            }
            return successResult();
        } catch (ServiceException ex) {
            LOGGER.error("delete func role " + code + " got exception!", ex);
            return failResult(ApiStatus.SERVICE_ERROR);
        }
    }
}