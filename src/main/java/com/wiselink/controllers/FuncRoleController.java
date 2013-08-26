/**
 * RoleController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午3:29:21
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
import com.wiselink.model.role.FuncRoleInfo;
import com.wiselink.result.ErrorCode;
import com.wiselink.service.FuncRoleService;
import com.wiselink.service.UserService;
import com.wiselink.utils.Utils;

/**
 * 用户角色相关的操作，例如创建角色、修改添加角色人物等
 * @author leo
 */
@Path("frole")
@LoginRequired
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
    @Get("{code:[0-9]+}")
    public String getFrole(@Param("code") int code) {
        return apiResult(froleService.getFuncRole(code));
    }

    /**
     * all func roles in corpId
     * 
     * @param param
     * @return
     */
    @Get("all/1corp")
    public String listFroles(@NotBlank @Param("param") String param) {
        String corpId = JSONObject.fromObject(param).optString("corpId");
        if (StringUtils.isBlank(corpId)) {
            return allFroles();
        }
        return apiResult(froleService.allFuncRoles(corpId));
    }

    /**
     * 获取所有的功能角色列表，相当于调用 <code>list(-1, -1)</code>
     * @return
     */
    @Get("all")
    public String allFroles() {
        LOGGER.info("all func roles...");
        return apiResult(froleService.allFuncRoles());
    }

    /**
     * 获取#code指定的功能模块信息及其所有的功能
     * @param code  如果code<0则返回所有的modules
     * @return
     */
    @Get("module/{code:[0-9]+}")
    public String module(@Param("code") int code) {
        if (code < 0) {
            return allModules();
        }
        return apiResult(froleService.getModule(code));
    }

    /**
     * 获取系统所有的功能模块
     * @return
     */
    @Get("modules")
    public String allModules() {
        LOGGER.info("all modules...");
        return apiResult(froleService.allModules());
    }

    /**
     * 新建一个功能角色
     * @param param
     * @return
     */
    @SuppressWarnings("post")
    @Get("new")
    public String newFrole(Invocation inv, @Param("param") String param) {
        LOGGER.info("add func role from param: {}.", param);
        FuncRoleInfo info = (FuncRoleInfo) new FuncRoleInfo().fromJson(param);
        info.setCreatorId(getUserIdFromCookie(inv));
        return apiResult(froleService.newFuncRole(info));
    }

    /**
     * 修改一个功能角色的信息
     * @param inv
     * @param param
     * @return
     */
    @SuppressWarnings("not completed|post")
    @Get("up/info")
    public String updateFroleInfo(Invocation inv, @Param("param") String param) {
        LOGGER.info("add func role from param: {}.", param);
        FuncRoleInfo info = (FuncRoleInfo) new FuncRoleInfo().fromJson(param);
        info.setCreatorId(getUserIdFromCookie(inv));
        return apiResult(froleService.updateFuncRole(info));
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
    public String updateFroleList(@Param("param") String param) {
        JSONObject jparam = JSONObject.fromObject(param);
        int code = jparam.optInt("code", -1);
        if (code <= 0) {
            return failResult(ErrorCode.InvalidParam, "参数code无效");
        }
        String funcsToDel = jparam.optString("funcsToDel", ""),
               funcsToAdd = jparam.optString("funcsToAdd", ""), 
               usersToDel = jparam.optString("usersToDel", ""),
               usersToAdd = jparam.optString("usersToAdd", "");
        LOGGER.info("updating func role: {}|fd:{}|fa:{}; ud:{}|ua:{}", 
                new Object[]{code, funcsToDel, funcsToAdd, usersToDel, usersToAdd});
        List<Integer> fdel = Utils.parseArrayFromStrings(funcsToDel, ",");
        List<Integer> fadd = Utils.parseArrayFromStrings(funcsToAdd, ",");
        List<String> udel = Utils.split(usersToDel, ",");
        List<String> uadd = Utils.split(usersToAdd, ",");
        return apiResult(froleService.updateFuncRole(code, fdel, fadd, udel, uadd));
    }

    /**
     * 删除指定功能角色
     * @param code
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("del")
    public String delete(@NotBlank @Param("param") String param) {
        int code = JSONObject.fromObject(param).optInt("code", -1);
        LOGGER.debug("deleting func role: {}", code);
        if (code <= 0) {
            return failResult(ErrorCode.InvalidParam, "参数code无效");
        }
        return apiResult(froleService.deleteFuncRole(code));
    }
}