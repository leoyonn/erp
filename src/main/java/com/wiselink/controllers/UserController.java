/**
 * UserController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午5:36:55
 */
package com.wiselink.controllers;

import java.util.Arrays;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.controllers.annotations.Trimmed;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.user.User;
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

    /**
     * @param inv
     * @param id
     * @param account
     * @param name
     * @param password
     * @param avatar
     * @param email
     * @param phone
     * @param tel
     * @param desc
     * @param province
     * @param city
     * @param creatorId
     * @param operId
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newUser(Invocation inv, @Trimmed @Param("id") String id,
            @Trimmed @Param("account") String account,
            @Trimmed @Param("name") String name,
            @Trimmed @Param("password") String password,
            @Param("avatar") String avatar,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("tel") String tel,
            @Param("desc") String desc,
            @Param("province") String province,
            @Param("city") String city,
            @Param("creatorId") String creatorId,
            @Param("operId") String operId) {
        try {
            boolean ok = userService.addUser(id, account, name, password, avatar, email, phone, tel,
                    desc, province, city, creatorId, operId);
            if (ok) {
                return successResult("添加用户成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("add user failed:" + id, ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * @param id
     * @param account
     * @param name
     * @param avatar
     * @param email
     * @param phone
     * @param tel
     * @param desc
     * @param province
     * @param city
     * @param creatorId
     * @param operId
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up/info")
    public String updateUserInfo(@Param("id") String id,
            @Param("account") String account,
            @Param("name") String name,
            @Param("avatar") String avatar,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("tel") String tel,
            @Param("desc") String desc,
            @Param("province") String province,
            @Param("city") String city,
            @Param("creatorId") String creatorId,
            @Param("operId") String operId) {
        try {
            boolean ok = userService.updateUserInfo(id, account, name, avatar, email, phone, tel,
                    desc, province, city, creatorId, operId);
            if (ok) {
                return successResult("修改用户信息成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("update user info failed:" + id, ex);
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
    public String updatePassword(Invocation inv, @Param("oldpass") String oldpass, @Param("newpass") String newpass) {
        String userId = CookieUtils.getUserId(inv);
        if (StringUtils.isEmpty(userId)) {
            return failResult(ApiStatus.AUTH_INVALID_USER);
        }
        try {
            boolean ok = userService.updateUserPassword(userId, oldpass, newpass);
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
     * @param userId
     * @param catCode
     * @param posCode
     * @param froleCode
     * @param droleCode
     * @param statCode
     * @param corpId
     * @param deptId
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new/role")
    public String newUserRole(@Param("id") String userId,
            @Param("catCode") int catCode,
            @Param("posCode") int posCode,
            @Param("froleCode") int froleCode,
            @Param("droleCode") int droleCode,
            @Param("statCode") int statCode,
            @Param("corpId") String corpId,
            @Param("deptId") String deptId) {
        try {
            boolean ok = userService.addUserRole(userId, catCode, posCode, froleCode, droleCode,
                    statCode, corpId, deptId);
            if (ok) {
                return successResult("添加用户角色属性成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("new user role failed:" + userId, ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    @SuppressWarnings("@Post")
    @Get("up/role")
    public String updateUserRole(@Param("id") String userId,
            @Param("catCode") int catCode,
            @Param("posCode") int posCode,
            @Param("froleCode") int froleCode,
            @Param("droleCode") int droleCode,
            @Param("statCode") int statCode,
            @Param("corpId") String corpId,
            @Param("deptId") String deptId) {
        try {
            boolean ok = userService.updateUserRole(userId, catCode, posCode, froleCode, droleCode,
                    statCode, corpId, deptId);
            if (ok) {
                return successResult("修改用户角色属性成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("update user role failed:" + userId, ex);
            return failResult(ApiStatus.DATA_UPDATE_FAILED);
        }
        return failResult(ApiStatus.DATA_UPDATE_FAILED);
    }

    /**
     * 获取一个用户，包括info和role
     * @param id
     * @return
     */
    @Get("1")
    public String getUser(@Param("id") String id, @Param("account") String account) {
        User user = null;
        try {
            if (StringUtils.isEmpty(id)) {
                user = userService.getUserById(id);
            } else {
                user = userService.getUserByAccount(account);
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
     * @param ids
     * @return
     */
    @Get("list")
    public String getUsers(@Trimmed @Param("ids") String ids) {
        // TODO @NotBlank
        if (StringUtils.isEmpty(ids)) {
            return failResult(ApiStatus.INVALID_PARAMETER);
        }
        List<String> idlist = Arrays.asList(ids.split(","));
        LOGGER.debug("getting users:{}", idlist);
        try {
            List<User> users = userService.getUsers(idlist);
            LOGGER.debug("got users:{}", users);
            if (users != null && users.size() > 0) {
                return successResult(users);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get users failed: " + idlist, ex);
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
    public String deleteUser(@Param("id") String userId) {
        // TODO permission check
        try {
            boolean ok = userService.deleteUser(userId);
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