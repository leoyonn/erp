/**
 * BudgetService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:27:12
 */
package com.wiselink.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.dao.BudgetDAO;
import com.wiselink.model.budget.Budget;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;

/**
 * 采购预算相关的服务
 * @author leo
 */
@Service
public class BudgetService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetService.class);

    @Autowired
    private BudgetDAO budgetDao;

    /**
     * 新建一个预算
     * 
     * <pre>
     * 2、新增预算申报
     * 参数：预算年度，预算部门(部门id)，申报额度，申报类型（id），备注。
     * 
     * 约束要求：
     * A、同一部门，正在审批的年度申报，正常数据（状态为正常，完成）只能有一条。 
     * B、只能申请本年度和下一年度的预算，不能为过去的年份申报
     * </pre>
     * 
     * @param budget
     * @return
     */
    public OperResult<Budget> newBudget(Budget budget) {
        try {
            budget.setCode(budgetDao.newCode());
            boolean ok = budgetDao.add(budget);
            if (!ok) {
                return r(ErrorCode.DbInsertFail, "添加采购预算失败，请检查参数");
            }
        } catch (Exception ex) {
            LOGGER.error("add budget: " + budget + " got ex", ex);
            return r(ErrorCode.DbInsertFail, "添加部门失败：", ex);
        }
        return r(budget);
    }    
    
    
}
