/**
 * UserController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午5:36:55
 */
package com.wiselink.controllers;

import java.util.Arrays;

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
import com.wiselink.model.param.QueryListParam;
import com.wiselink.model.user.UserRaw;
import com.wiselink.result.ErrorCode;
import com.wiselink.service.UserService;
import com.wiselink.utils.CookieUtils;

/**
 * 
 * @author leo
 */
@Path("user")
@LoginRequired
public class UserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Get("positions")
    public String allPositions() {
        return apiResult(userService.allPositions());
    }

    /**
     * 新建一个用户
     * 
     * @param inv
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newUser(Invocation inv, @Param("param") String param) {
        String creatorId = getUserIdFromCookie(inv);
        UserRaw user = (UserRaw)new UserRaw().fromJson(param);
        user.setCreatorId(creatorId).setOperId(creatorId);
        String password = JSONObject.fromObject(param).optString("password", "");
        LOGGER.info("creating user: {} with password: {}", user, password);
        if (StringUtils.isBlank(password)) {
            return failResult(ErrorCode.BlankParam, "用户密码为空");
        }
        return apiResult(userService.newUser(user, password));
    }

    /**
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up")
    public String updateUser(Invocation inv, @Param("param") String param) {
        UserRaw user = (UserRaw)new UserRaw().fromJson(param);
        user.setOperId(getUserIdFromCookie(inv));
        LOGGER.info("update user info: {} ", user);
        return apiResult(userService.updateUser(user));
    }

    /**
     * 已登录用户修改自己的密码
     * 
     * @param inv
     * @param oldpass
     * @param newpass
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up/pass")
    public String updatePassword(Invocation inv, @Param("param") String param) {
        JSONObject json = JSONObject.fromObject(param);
        String oldpass =json.optString("oldpass", "");
        String newpass =json.optString("newpass", "");
        String userId = CookieUtils.getUserId(inv);
        LOGGER.info("update password of user himself: {} ", userId);
        if (StringUtils.isEmpty(userId)) {
            return failResult(ErrorCode.LoginRequired);
        }
        return apiResult(userService.updatePassword(userId, oldpass, newpass));
    }

    /**
     * 获取一个用户的信息
     * TODO put id into url
     * 
     * @param id
     * @return
     */
    @Get("{id:[0-9]+}")
    public String getUserById(@NotBlank @Param("id") String id) {
        LOGGER.info("get user info by id{} ", id);
        return apiResult(userService.getUserById(id));
    }

    @Get("{account:[a-zA-Z].*}")
    public String getUserByAccount(@NotBlank @Param("account") String account) {
        LOGGER.info("got user by account {}", account);
        return apiResult(userService.getUserByAccount(account));
    }

    /**
     * 批量获取用户
     * 
     * @param ids
     * @return
     */
    @Get("query")
    public String queryUsers(@NotBlank @Param("param") String param) {
        LOGGER.info("list users: {}", param);
        QueryListParam listParam = (QueryListParam) new QueryListParam().fromJson(param);
        LOGGER.info("list users with list param: {}", listParam);
        return apiResult(userService.queryUsers(listParam));
    }

    /**
     * 获取所有用户，仅用于调试
     * @return
     */
    @Get("all")
    public String allUsers() {
        return apiResult(userService.all());
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("del")
    public String deleteUser(Invocation inv, @NotBlank @Param("param") String param) {
        JSONObject json = JSONObject.fromObject(param);
        String ids =json.optString("ids", "");
        LOGGER.info("delete users: {} ", ids);
        if (StringUtils.isBlank(ids)) {
            return failResult(ErrorCode.BlankParam, "输入id列表为空");
        }
        return apiResult(userService.deleteUsers(Arrays.asList(ids.split(","))));
    }
}