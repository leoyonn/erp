/**
 * DataRoleController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-15 上午11:47:00
 */
package com.wiselink.controllers;

import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.model.role.DataRole;
import com.wiselink.model.role.DataRoleInfo;
import com.wiselink.result.ErrorCode;
import com.wiselink.service.DataRoleService;
import com.wiselink.service.UserService;
import com.wiselink.utils.Utils;

/**
 * 
 * @author leo
 */
@Path("drole")
@LoginRequired
public class DataRoleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataRoleController.class);

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
    @Get("{code:[0-9]+}")
    public String getDrole(@Param("code") int code) {
        if (code < 0) {
            return allDroles();
        }
        return apiResult(droleService.getDataRole(code));
    }

    /**
     * @param param
     * @return
     */
    @Get("all/1corp")
    public String listDroles(@NotBlank @Param("param") String param) {
        String corpId = JSONObject.fromObject(param).optString("corpId");
        if (StringUtils.isBlank(corpId)) {
            return allDroles();
        }
        return apiResult(droleService.allDataRoles(corpId));
    }

    /**
     * 获取所有的数据角色列表，相当于调用 <code>list(-1, -1)</code>
     * @return
     */
    @Get("all")
    public String allDroles() {
        return apiResult(droleService.allDataRoles());
    }

    /**
     * 获取系统所有的数据level
     * @return
     */
    @Get("levels")
    public String levels() {
        return apiResult(droleService.levels());
    }

    /**
     * 新建一个数据角色
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @param creatorId
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newDrole(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("add data role from param: {}.", param);
        DataRoleInfo info = (DataRoleInfo) new DataRoleInfo().fromJson(param);
        info.setCreatorId(getUserIdFromCookie(inv));
        return apiResult(droleService.newDataRole(info));
    }

    /**
     * 修改一个数据角色的信息
     * @param inv
     * @param code
     * @param name
     * @param desc
     * @param levelCode
     * @param corpId
     * @param deptId
     * @return
     */
    @Get("up")
    public String updateDroleInfo(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("update data role from param: {}.", param);
        DataRoleInfo info = (DataRoleInfo) new DataRoleInfo().fromJson(param);
        info.setCreatorId(getUserIdFromCookie(inv));
        return apiResult(droleService.updateDataRole(info));
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
    @Get("up/nodes")
    public String updateDroleList(@NotBlank @Param("param") String param) {
        JSONObject jparam = JSONObject.fromObject(param);
        LOGGER.info("update data role list {}", param);
        int code = jparam.optInt("code", -1);
        if (code < 0) {
            return failResult(ErrorCode.InvalidParam, "code参数不存在");
        }
        List<String> sdel = Utils.split(jparam.optString("scopesToDel"), ",");
        List<String> sadd = Utils.split(jparam.optString("scopesToAdd"), ",");
        List<String> udel = Utils.split(jparam.optString("usersToDel"), ",");
        List<String> uadd = Utils.split(jparam.optString("usersToAdd"), ",");
        return apiResult(droleService.updateDataRole(code, sdel, sadd, udel, uadd));
    }

    /**
     * 删除指定的数据角色
     * @param code
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("del")
    public String delete(@NotBlank @Param("param") String param) {
        LOGGER.info("deleting data role: {}", param);
        int code = JSONObject.fromObject(param).optInt("code", -1);
        if (code < 0) {
            return failResult(ErrorCode.InvalidParam, "code参数不存在");
        }
        return apiResult(droleService.delete(code));
    }
}
