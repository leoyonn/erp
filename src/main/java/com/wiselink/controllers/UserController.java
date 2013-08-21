/**
 * UserController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午5:36:55
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

import com.google.gson.Gson;
import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.param.QueryListParam;
import com.wiselink.model.user.User;
import com.wiselink.model.user.UserRaw;
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

    @SuppressWarnings("@Post")
    @Get("new")
    public String newUser(Invocation inv, @Param("param") String param) {
        UserRaw user = (UserRaw)new UserRaw().fromJson(param);
        String password = JSONObject.fromObject(param).optString("password", "");
        if (StringUtils.isBlank(password)) {
            return failResult(ApiStatus.INVALID_PARAMETER, "用户密码为空");
        }
        new Gson().fromJson(param, UserRaw.class);
        try {
            boolean ok = userService.newUser(user, password);
            if (ok) {
                return successResult("添加用户成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("add user failed:" + user, ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up")
    public String updateUser(@Param("param") String param) {
        UserRaw user = (UserRaw)new UserRaw().fromJson(param);
        try {
            boolean ok = userService.updateUser(user);
            if (ok) {
                return successResult("修改用户信息成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("update user info failed:" + user, ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
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
        if (StringUtils.isEmpty(userId)) {
            return failResult(ApiStatus.AUTH_INVALID_USER);
        }
        try {
            boolean ok = userService.updatePassword(userId, oldpass, newpass);
            if (ok) {
                return successResult("修改密码成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("update password failed:" + userId, ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * 获取一个用户，包括info和role
     * @param id
     * @return
     */
    @Get("1")
    public String getUser(@NotBlank @Param("param") String param) {
        JSONObject j = JSONObject.fromObject(param);
        String id = j.optString("id", "");
        String account = j.optString("account", "");
        if (StringUtils.isBlank(id) && StringUtils.isBlank(account)) {
            return failResult(ApiStatus.INVALID_PARAMETER, "id或account不能都为空");
        }
        User user = null;
        try {
            if (!StringUtils.isEmpty(id)) {
                user = userService.getUserById(id);
                LOGGER.debug("got user by id:" + id, user);
            } else {
                user = userService.getUserByAccount(account);
                LOGGER.debug("got user by account:" + account, user);
            }
            if (user != null) {
                return successResult(user);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get user failed:" + id, ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    /**
     * 批量获取用户
     * 
     * @param ids
     * @return
     */
    @Get("list")
    public String getUsers(@NotBlank @Param("param") String param) {
        LOGGER.info("list users: {}", param);
        QueryListParam listParam = (QueryListParam) new QueryListParam().fromJson(param);
        LOGGER.info("list users with list param: {}", listParam);
        try {
            List<User> users = userService.queryUsers(listParam);
            LOGGER.debug("got users:{}", users);
            if (users != null && users.size() > 0) {
                return successResult(users);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get users failed: " + listParam, ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
        return failResult(ApiStatus.DATA_EMPTY);
    }

    /**
     * 获取所有用户，仅用于调试
     * @return
     */
    @Get("all")
    public String allUsers() {
        try {
            List<User> users = userService.all();
            LOGGER.debug("got all users:{}", users);
            if (users != null && users.size() > 0) {
                return successResult(users);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get all users failed!", ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
        return failResult(ApiStatus.DATA_EMPTY);
    }

    /**
     * @param userId
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("del")
    public String deleteUser(Invocation inv, @NotBlank @Param("param") String param) {
        JSONObject json = JSONObject.fromObject(param);
        String id =json.optString("id", "");
        String creatorId =json.optString("creatorId", "");
        String userId = CookieUtils.getUserId(inv);
        if (StringUtils.isEmpty(creatorId) || !creatorId.equals(userId)) {
            return failResult(ApiStatus.AUTH_DENIED, "创建者ID参数不存在或与当前登录用户不符，或与要删除的用户创建者不符");
        }
        try {
            boolean ok = userService.deleteUser(id, creatorId);
            if (ok) {
                return successResult("删除用户成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("delete user failed:" + userId, ex);
            return failResult(ApiStatus.DATA_DELETE_FAILED);
        }
        return failResult(ApiStatus.DATA_DELETE_FAILED);
    }
}