/**
 * DeptDaoTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-17 上午11:36:02
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author leo
 */
@SuppressWarnings("easymock database, don't connect to server.")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DeptDaoTest {
    @Autowired
    private DeptDAO deptDao;

    private void p(Object o) {
        System.out.println(o);
    }

    @Test
    public void testDeptDao() throws SQLException, DataAccessException {
        p(deptDao.count());
        p(deptDao.all());
        p(deptDao.delete(Arrays.asList(new String[]{"10002", "10003", "10010", "20001"})));
        p(deptDao.delete("10001"));
        p(deptDao.count());
        p(deptDao.all());
        p(deptDao.addDept("10001", "测试部门1", "测试部门1", "技术部", "10000"));
        p(deptDao.addDept("10002", "测试部门2", "测试部门2", "技术部", "10000"));
        p(deptDao.addDept("10003", "测试部门3", "测试部门3", "技术部", "10000"));
        p(deptDao.addDept("10010", "测试部门10", "测试部门10", "技术部", "10000"));
        p(deptDao.addDept("20001", "测试部门21", "测试部门21", "技术部", "20000"));
        p(deptDao.queryByName("%测%", "10000", 1, 3));
        p(deptDao.countByName("%1%", "10000"));
        p(deptDao.maxDeptId("10000"));
    }   

}
