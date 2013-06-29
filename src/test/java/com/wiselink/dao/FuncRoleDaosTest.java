/**
 * FuncRoleDaosTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-22 下午1:56:29
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.role.FuncRoleInfo;

/**
 * @author leo
 */
@SuppressWarnings("easymock database, don't connect to server.")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FuncRoleDaosTest {
    @Autowired
    private FuncRoleInfoDAO infoDao;
    @Autowired
    private FuncRoleFuncsDAO funcsDao;
    @Autowired
    private FuncRoleUsersDAO usersDao;

    int N = 3;
    int code[] = new int[N];
    String name[] = new String[N];
    String desc[] = new String[N];
    String corpId[] = new String[N];
    String deptId[] = new String[N];
    String creatorId[] = new String[N];

    @Before
    public void setup() {
        for (int i = 0; i < N; i ++) {
            code[i] = 101000 + i;
            name[i] = "测试功能角色-" + i;
            desc[i] = "这是一个测试功能角色啊-" + i;
            corpId[i] = "1100" + i;
            deptId[i] = "1200" + i;
            creatorId[i] = "1300" + i;
        }
    }
    
    private void p(String s) {
        System.out.println(s);
    }

    @Test
    public void testFuncRoleInfoDao() throws SQLException, DataAccessException {
        // clear
        p("cleared:" + infoDao.clear());
        FuncRoleInfo info = infoDao.find(code[0]);
        Assert.assertNull(info);
        // add 0
        Assert.assertTrue(infoDao.add(name[0], desc[0], corpId[0], deptId[0], creatorId[0]));
        info = infoDao.findByName(name[0]);
        p("add: " + info);
        Assert.assertEquals(info.name, name[0]);
        Assert.assertEquals(info.desc, desc[0]);
        Assert.assertEquals(info.corpId, corpId[0]);
        Assert.assertEquals(info.deptId, deptId[0]);
        Assert.assertEquals(info.creatorId, creatorId[0]);
        Assert.assertNotNull(info.createTime);
        Assert.assertNotNull(info.updateTime);
        code[0] = info.code;
        info = infoDao.find(code[0]);
        Assert.assertEquals(info.code, code[0]);
        Assert.assertEquals(info.name, name[0]);
        Assert.assertEquals(info.desc, desc[0]);
        info = infoDao.findByName(name[1]);
        Assert.assertNull(info);
        // modify name -> 1
        Assert.assertTrue(infoDao.update(code[0], name[1], desc[1], corpId[1], deptId[1]));
        info = infoDao.find(code[0]);
        p("mod: " + info);
        Assert.assertEquals(info.code, code[0]);
        Assert.assertEquals(info.name, name[1]);
        Assert.assertEquals(info.desc, desc[1]);
        info = infoDao.findByName(name[1]);
        Assert.assertEquals(info.code, code[0]);
        Assert.assertEquals(info.name, name[1]);
        Assert.assertEquals(info.desc, desc[1]);
        info = infoDao.findByName(name[0]);
        Assert.assertNull(info);
        // can't add because name 1
        try {
            infoDao.add(name[1], desc[1], corpId[1], deptId[1], creatorId[1]);
            Assert.fail();
        } catch (Exception ex) {}
        info = infoDao.find(code[1]);
        Assert.assertNull(info);
        // set back name 0
        Assert.assertTrue(infoDao.update(code[0], name[0], desc[0], corpId[0], deptId[0]));
        // add 1
        Assert.assertTrue(infoDao.add(name[1], desc[1], corpId[1], deptId[1], creatorId[1]));
        info = infoDao.find(code[0]);
        p("mod: " + info);
        Assert.assertEquals(info.code, code[0]);
        Assert.assertEquals(info.name, name[0]);
        Assert.assertEquals(info.desc, desc[0]);
        info = infoDao.findByName(name[1]);
        p("add: " + info);
        code[1] = info.code;
        Assert.assertEquals(info.name, name[1]);
        Assert.assertEquals(info.desc, desc[1]);
        // can't modify
        try {
            infoDao.update(code[0], name[1], desc[0], corpId[0], deptId[0]);
            Assert.fail();
        } catch (Exception ex) {}
        info = infoDao.find(code[0]);
        Assert.assertEquals(info.code, code[0]);
        Assert.assertEquals(info.name, name[0]);
        Assert.assertEquals(info.desc, desc[0]);
        Assert.assertTrue(infoDao.add(name[2], desc[2], corpId[2], deptId[2], creatorId[2]));
        info = infoDao.findByName(name[2]);
        p("add: " + info);
        code[2] = info.code;
        Assert.assertEquals(info.name, name[2]);
        Assert.assertEquals(info.desc, desc[2]);
        // list
        List<FuncRoleInfo> l = infoDao.list(0, 10);
        p("list: " + l);
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(name[0], l.get(0).name);
        Assert.assertEquals(name[1], l.get(1).name);
        Assert.assertEquals(name[2], l.get(2).name);
        l = infoDao.list(code[0], 10);
        p("list: " + l);
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(name[0], l.get(0).name);
        Assert.assertEquals(name[1], l.get(1).name);
        Assert.assertEquals(name[2], l.get(2).name);
        l = infoDao.list(code[1], 10);
        p("list: " + l);
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(name[1], l.get(0).name);
        l = infoDao.list(code[2], 10);
        p("list: " + l);
        Assert.assertEquals(1, l.size());
        Assert.assertEquals(name[2], l.get(0).name);
        l = infoDao.list(code[2] + 1, 10);
        p("list: " + l);
        Assert.assertEquals(0, l.size());
        // list by collections
        l = infoDao.list(Arrays.asList(new Integer[]{1323232, code[0], code[2], code[1], 1212}));
        p("listc: " + l);
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(name[0], l.get(0).name);
        Assert.assertEquals(name[1], l.get(1).name);
        Assert.assertEquals(name[2], l.get(2).name);
        // delete
        Assert.assertTrue(infoDao.delete(code[1]));
        l = (infoDao.list(0, 100));
        p("list: " + l);
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(name[0], l.get(0).name);
        Assert.assertEquals(name[2], l.get(1).name);
        l = (infoDao.list(code[0], 10));
        p("list: " + l);
        Assert.assertEquals(2, l.size());
        l = (infoDao.list(code[1], 10));
        p("list: " + l);
        Assert.assertEquals(1, l.size());
        l = (infoDao.list(code[2], 10));
        Assert.assertEquals(1, l.size());
        Assert.assertEquals(name[2], l.get(0).name);
        // clear
        Assert.assertEquals(2, infoDao.clear());
        Assert.assertEquals(0, infoDao.list(0, 100).size());
    }

    @Test
    public void testFuncRoleFuncsDao() throws DataAccessException, SQLException {
        funcsDao.clear();
        List<Integer> l = funcsDao.getFuncs(code[0]);
        Assert.assertEquals(0, l.size());
        Assert.assertTrue(funcsDao.addFuncToRole(code[0], 1001));
        Assert.assertTrue(funcsDao.addFuncToRole(code[0], 1002));
        Assert.assertTrue(funcsDao.addFuncToRole(code[0], 1003));
        Assert.assertTrue(funcsDao.addFuncToRole(code[1], 1001));
        Assert.assertTrue(funcsDao.addFuncToRole(code[1], 1022));
        Assert.assertTrue(funcsDao.addFuncToRole(code[1], 1003));
        Assert.assertTrue(funcsDao.addFuncToRole(code[2], 1001));
        Assert.assertTrue(funcsDao.addFuncToRole(code[2], 1002));
        Assert.assertTrue(funcsDao.addFuncToRole(code[2], 1033));
        l = (funcsDao.getFuncs(code[0]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(Integer.valueOf(1001), l.get(0));
        Assert.assertEquals(Integer.valueOf(1002), l.get(1));
        Assert.assertEquals(Integer.valueOf(1003), l.get(2));
        l = (funcsDao.getFuncs(code[1]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(Integer.valueOf(1001), l.get(0));
        Assert.assertEquals(Integer.valueOf(1022), l.get(2));
        Assert.assertEquals(Integer.valueOf(1003), l.get(1));
        l = (funcsDao.getFuncs(code[2]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(Integer.valueOf(1001), l.get(0));
        Assert.assertEquals(Integer.valueOf(1002), l.get(1));
        Assert.assertEquals(Integer.valueOf(1033), l.get(2));
        Assert.assertTrue(funcsDao.delete(code[0], 1001));
        l = (funcsDao.getFuncs(code[0]));
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(Integer.valueOf(1002), l.get(0));
        Assert.assertTrue(funcsDao.clear());
        Assert.assertEquals(0, funcsDao.getFuncs(code[0]).size());
    }
    
    @Test
    public void testFuncRoleUsersDao() throws DataAccessException, SQLException {
        System.out.println(usersDao.clear());
        List<String> l = usersDao.getUsers(code[0]);
        Assert.assertEquals(0, l.size());
        Assert.assertTrue(usersDao.addUserToRole(code[0], "1001"));
        Assert.assertTrue(usersDao.addUserToRole(code[0], "1002"));
        Assert.assertTrue(usersDao.addUserToRole(code[0], "1003"));
        Assert.assertTrue(usersDao.addUserToRole(code[1], "1001"));
        Assert.assertTrue(usersDao.addUserToRole(code[1], "1022"));
        Assert.assertTrue(usersDao.addUserToRole(code[1], "1003"));
        Assert.assertTrue(usersDao.addUserToRole(code[2], "1001"));
        Assert.assertTrue(usersDao.addUserToRole(code[2], "1002"));
        Assert.assertTrue(usersDao.addUserToRole(code[2], "1033"));
        l = (usersDao.getUsers(code[0]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals("1001", l.get(0));
        Assert.assertEquals("1002", l.get(1));
        Assert.assertEquals("1003", l.get(2));
        l = (usersDao.getUsers(code[1]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals("1001", l.get(0));
        Assert.assertEquals("1022", l.get(2));
        Assert.assertEquals("1003", l.get(1));
        l = (usersDao.getUsers(code[2]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals("1001", l.get(0));
        Assert.assertEquals("1002", l.get(1));
        Assert.assertEquals("1033", l.get(2));
        Assert.assertTrue(usersDao.delete(code[0], "1001"));
        l = (usersDao.getUsers(code[0]));
        Assert.assertEquals(2, l.size());
        Assert.assertEquals("1002", l.get(0));
        Assert.assertTrue(usersDao.clear());
        Assert.assertEquals(0, usersDao.getUsers(code[0]).size());
    }
}