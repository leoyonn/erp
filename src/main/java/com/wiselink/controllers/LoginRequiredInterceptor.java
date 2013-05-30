/**
 * LoginRequiredInterceptor.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-26 上午11:21:55
 */
package com.wiselink.controllers;

import java.lang.annotation.Annotation;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiselink.base.ApiResult;
import com.wiselink.base.AuthResult;
import com.wiselink.base.Constants;
import com.wiselink.controllers.LoginRequiredChecker.LoginRequiredCheckResult;
import com.wiselink.controllers.annotations.LoginRequired;

/**
 * @author leo
 */
public class LoginRequiredInterceptor extends ControllerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequiredInterceptor.class);

    private LoginRequiredChecker checker;

    public LoginRequiredInterceptor() {
        checker = new LoginRequiredChecker();
    }

    @Override
    public int getPriority() {
        return Constants.INTERCEPTOR_PRIORITY_LOGIN;
    }

    @Override
    public Class<? extends Annotation> getRequiredAnnotationClass() {
        return LoginRequired.class;
    }

    @Override
    public Object before(Invocation inv) throws Exception {
        LOGGER.error("error: enter LoginRequiredInterceptor with invocation: {}...", inv);
        LOGGER.warn("warn: enter LoginRequiredInterceptor with invocation: {}...", inv);
        LOGGER.debug("enter LoginRequiredInterceptor with invocation: {}...", inv);
        LoginRequiredCheckResult result = checker.doCheck(inv.getRequest(), null, true);
        LOGGER.debug("exit ssorequired, result {}", result.success);
        if (result.success) {
            inv.addModel("uuid", result.uuid);
            inv.addModel("ssecurity", result.ssecurity);
            return true;
        } else {
            return ApiResult.authFailed(AuthResult.LOGIN_REQUIRED);
        }
    }
}