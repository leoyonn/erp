/**
 * BudgetDaoTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-9-9 下午1:21:46
 */
package com.wiselink.dao;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.budget.Budget;

/**
 * 
 * @author leo
 */
@SuppressWarnings("easymock database, don't connect to server.")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BudgetDaoTest {
    @Autowired
    private BudgetDAO dao;
    @Test
    public  void test() throws DataAccessException, SQLException {
        int code = dao.newCode();
        Budget budget = new Budget().setCode(code).setOrg("org");
        dao.add(budget);
        System.out.println(dao.find(code));
        dao.up(budget.setOrg("org2"));
        System.out.println(dao.find(code));
        dao.delete(code);
        System.out.println(dao.all());
    }
    
}
