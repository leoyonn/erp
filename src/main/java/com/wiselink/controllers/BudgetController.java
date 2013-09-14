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
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.model.budget.Budget;
import com.wiselink.model.budget.BudgetProgress;
import com.wiselink.model.budget.BudgetStatus;
import com.wiselink.model.budget.BudgetType;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
import com.wiselink.service.BudgetService;

/**
 * 
 * @author leo
 */
@LoginRequired
@Path("budget")
public class BudgetController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;
    
    @Get("statuses")
    public String statuses() {
        return successResult(BudgetStatus.all());
    }

    @Get("progresses")
    public String processes() {
        return successResult(BudgetProgress.all());
    }

    @Get("types")
    public String types() {
        return successResult(BudgetType.all());
    }

    /**
     * 创建一个新的部门，name/id一旦设置不可更改
     * 
     * @param inv
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newBudget(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("new budget with param: {}...", param);
        Budget budget = (Budget) new Budget().fromJson(param);
        budget.setCreatorId(getUserIdFromCookie(inv));
        OperResult<Budget> r = budgetService.newBudget(budget);
        return apiResult(r);
    }



    @SuppressWarnings("@Post")
    @Get("new")
    public String add(Invocation inv, @Param("budget") String budgetJson) {
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
