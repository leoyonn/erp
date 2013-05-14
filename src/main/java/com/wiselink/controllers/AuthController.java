/**
 * AuthController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:20:11
 */
package com.wiselink.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;
import com.wiselink.model.User;

/**
 * @author leo
 */
@Path("")
public class AuthController {
    @Get("login")
    public String login(Invocation inv) {
        User u = new User();
        u.setAccount("j4test");
        u.setPassword("j4testpass");
        String user = inv.getParameter("user"), pass = inv.getParameter("pass");
        if (u.getAccount().equals(user) && u.getPassword().equals(pass)) {
            return "@" + new ApiResult(new ApiStatus(0, "登录成功"), u.toJson());
        }
        return "@" + new ApiResult(new ApiStatus(1, "无效用户，测试阶段j4test:j4testpass可以登录"), null);
    }
}
