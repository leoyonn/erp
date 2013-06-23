/**
 * DataRoleController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-15 上午11:47:00
 */
package com.wiselink.controllers;

import java.util.Collection;
import java.util.List;

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
import com.wiselink.exception.ServiceException;
import com.wiselink.model.role.DataLevel;
import com.wiselink.model.role.DataRole;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.service.DataRoleService;
import com.wiselink.service.UserService;
import com.wiselink.utils.Utils;

/**
 * 
 * @author leo
 */
@Path("drole")
public class DataRoleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncRoleController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private DataRoleService droleService;

    /**
     * 获取#code指定的数据角色的数据，包括功能清单和人员清单。<p>
     * 
     * @param code
     * @return
     */
    @Get("1")
    public String getDrole(@Param("code") int code) {
        try {
            DataRole drole = droleService.getDataRole(code);
            return successResult(drole.toJson());
        } catch (ServiceException ex) {
            LOGGER.error("add func role " + code + " got exception!", ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
    }

    @SuppressWarnings("@Post")
    @Get("new")
    public String newDrole(@Param("name") String name, @Param("desc") String desc, @Param("levelCode") int levelCode,
        @Param("corpId") String corpId, @Param("deptId") String deptId, @Param("creatorId") String creatorId) {
        LOGGER.debug("add data role of name: {}, desc: {}.", name, desc);
        // TODO full file this: get creator from cookie
        try {
            DataRoleInfo drole = droleService.newDataRole(name, desc, levelCode, corpId, deptId, creatorId);
            LOGGER.debug("add data role success: {}.", drole);
            return successResult(drole.toJson());
        } catch (ServiceException ex) {
            LOGGER.error("add func role " + name + "|" + desc + " got exception!", ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED, "角色添加失败，请确认角色名不重复，或联系系统管理员");
        }
    }

    /**
     * list all #num data-roles sorted by {@link DataRole#code} from #from 
     * 如果需要listall，设置from和num都为负值
     * @param from
     * @param num
     * @return
     */
    @Get("list")
    public String listDroles(@Param("from") int from, @Param("num") int num) {
        if (num < 0) {
            num = Constants.MAX_ROLE_NUM;
        }
        List<DataRoleInfo> roles = null;
        try {
            roles = droleService.getDataRoles(from, num);
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
        for (DataRoleInfo role: roles) {
            array.add(role.toJson());
        }
        LOGGER.debug("list role returning{}.", array);
        return successResult(array.toString());
    }

    /**
     * 获取所有的数据角色列表，相当于调用 <code>list(-1, -1)</code>
     * @return
     */
    @Get("all")
    public String allDroles() {
        return listDroles(-1, -1);
    }

    /**
     * 获取系统所有的功能模块
     * @return
     */
    @Get("levels")
    public String levels() {
        Collection<DataLevel> allLevels = droleService.levels();
        LOGGER.debug("query for all-levels got {}.", allLevels);
        return successResult(allLevels);
    }

    /**
     * 更新数据角色#code对应的权限范围和用户
     * <p>
     * 注意参数说明
     * TODO 参数支持list？
     * 
     * @param code
     * @param scopesToDel
     *            要从角色中删除的权限范围列表，列表为用逗号隔开的org-id，如"0012,0123"
     * @param scopesToAdd
     *            要添加到角色中的权限范围列表，列表为用逗号隔开的org-id，如"0012,0123"
     * @param usersToDel
     *            要从角色中删除的用户列表，列表为用逗号隔开的userId，如“111,222,333"
     * @param usersToAdd
     *            要添加到角色中的用户列表，列表为用逗号隔开的userId，如“111,222,333"
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("update")
    public String updateFrole(@Param("code") int code,
            @Param("scopesToDel") String scopesToDel, @Param("scopesToAdd") String scopesToAdd, 
            @Param("usersToDel") String usersToDel, @Param("usersToAdd") String usersToAdd) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updating data role: {}|sd:{}|sa:{}; ud:{}|ua:{}", 
                    new Object[]{code, scopesToDel, scopesToAdd, usersToDel, usersToAdd});
        }
        List<String> sdel = Utils.split(scopesToDel, ",");
        List<String> sadd = Utils.split(scopesToAdd, ",");
        List<String> udel = Utils.split(usersToDel, ",");
        List<String> uadd = Utils.split(usersToAdd, ",");
        try {
            droleService.updateDataRole(code, sdel, sadd, udel, uadd);
        } catch (ServiceException ex) {
            LOGGER.error("update data role " + code + " scopes " + scopesToDel + "|" + scopesToAdd
                    + " users " + usersToDel + "|" + usersToAdd + " got exception!", ex);
            return failResult(ApiStatus.SERVICE_ERROR);
        }
        return successResult("设置角色成功");
    }
}
