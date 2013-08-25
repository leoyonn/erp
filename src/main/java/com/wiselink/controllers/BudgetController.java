/**
 * BudgetController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-4 上午11:19:55
 */
package com.wiselink.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.result.ErrorCode;

/**
 * 
 * @author leo
 */
@LoginRequired
@Path("budget")
public class BudgetController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);

    @SuppressWarnings("@Post")
    @Get("declare")
    public String declare(Invocation inv, @Param("budget") String budgetJson) {
        return failResult(ErrorCode.InvalidParam, "budget/declare not implementted yet:" + budgetJson);
    }

    @SuppressWarnings("@Post")
    @Get("up")
    public String update(Invocation inv, @Param("budget") String budgetJson) {
        return failResult(ErrorCode.InvalidParam, "budget/up not implementted yet:" + budgetJson);
    }

    @SuppressWarnings("@Post")
    @Get("approve")
    public String approve(Invocation inv, @Param("budget") String budgetJson) {
        return failResult(ErrorCode.InvalidParam, "budget/up not implementted yet:" + budgetJson);
    }

//    public String 
    
}
