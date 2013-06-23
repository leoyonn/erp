/**
 * DataRoleDaosTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-23 下午8:41:04
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.role.DataRoleInfo;

/**
 * 
 * @author leo
 */
@SuppressWarnings("easymock database, don't connect to server.")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DataRoleDaosTest {
    @Autowired
    private DataRoleInfoDAO infoDao;
    @Autowired
    private DataRoleScopesDAO scopesDao;
    @Autowired
    private DataRoleUsersDAO usersDao;

    int N = 3;
    int code[] = new int[N];
    int levelCode[] = new int[N];
    String name[] = new String[N];
    String desc[] = new String[N];
    String corpId[] = new String[N];
    String deptId[] = new String[N];
    String creatorId[] = new String[N];

    @Before
    public void setup() {
        for (int i = 0; i < N; i ++) {
            code[i] = 101000 + i;
            name[i] = "测试数据角色-" + i;
            desc[i] = "这是一个数据角色啊-" + i;
            corpId[i] = "1100" + i;
            deptId[i] = "1200" + i;
            levelCode[i] = N - i;
            creatorId[i] = "1300" + i;
        }
    }
    
    private void p(String s) {
        System.out.println(s);
    }

    @Test
    public void testDataRoleInfoDao() throws SQLException, DataAccessException {
        // clear
        p("cleared:" + infoDao.clear());
        DataRoleInfo info = infoDao.find(code[0]);
        Assert.assertNull(info);
        // add 0
        Assert.assertTrue(infoDao.add(name[0], desc[0], levelCode[0], corpId[0], deptId[0], creatorId[0]));
        info = infoDao.findByName(name[0]);
        p("add: " + info);
        Assert.assertEquals(info.name, name[0]);
        Assert.assertEquals(info.desc, desc[0]);
        Assert.assertEquals(info.levelCode, levelCode[0]);
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
        Assert.assertTrue(infoDao.update(code[0], name[1], desc[1], levelCode[1], corpId[1], deptId[1]));
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
            infoDao.add(name[1], desc[1], levelCode[1], corpId[1], deptId[1], creatorId[1]);
            Assert.fail();
        } catch (Exception ex) {}
        info = infoDao.find(code[1]);
        Assert.assertNull(info);
        // set back name 0
        Assert.assertTrue(infoDao.update(code[0], name[0], desc[0], levelCode[0], corpId[0], deptId[0]));
        // add 1
        Assert.assertTrue(infoDao.add(name[1], desc[1], levelCode[1], corpId[1], deptId[1], creatorId[1]));
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
            infoDao.update(code[0], name[1], desc[0], levelCode[0], corpId[0], deptId[0]);
            Assert.fail();
        } catch (Exception ex) {}
        info = infoDao.find(code[0]);
        Assert.assertEquals(info.code, code[0]);
        Assert.assertEquals(info.name, name[0]);
        Assert.assertEquals(info.desc, desc[0]);
        Assert.assertTrue(infoDao.add(name[2], desc[2], levelCode[2], corpId[2], deptId[2], creatorId[2]));
        info = infoDao.findByName(name[2]);
        p("add: " + info);
        code[2] = info.code;
        Assert.assertEquals(info.name, name[2]);
        Assert.assertEquals(info.desc, desc[2]);
        // list
        List<DataRoleInfo> l = infoDao.list(0, 10);
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
    public void testDataRoleScopesDao() throws DataAccessException, SQLException {
        scopesDao.clear();
        List<String> l = scopesDao.getScopes(code[0]);
        Assert.assertEquals(0, l.size());
        Assert.assertTrue(scopesDao.addScopeToRole(code[0], "1001"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[0], "1002"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[0], "1003"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[1], "1001"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[1], "1022"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[1], "1003"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[2], "1001"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[2], "1002"));
        Assert.assertTrue(scopesDao.addScopeToRole(code[2], "1033"));
        l = (scopesDao.getScopes(code[0]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(String.valueOf(1001), l.get(0));
        Assert.assertEquals(String.valueOf(1002), l.get(1));
        Assert.assertEquals(String.valueOf(1003), l.get(2));
        l = (scopesDao.getScopes(code[1]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(String.valueOf(1001), l.get(0));
        Assert.assertEquals(String.valueOf(1022), l.get(2));
        Assert.assertEquals(String.valueOf(1003), l.get(1));
        l = (scopesDao.getScopes(code[2]));
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(String.valueOf(1001), l.get(0));
        Assert.assertEquals(String.valueOf(1002), l.get(1));
        Assert.assertEquals(String.valueOf(1033), l.get(2));
        Assert.assertTrue(scopesDao.delete(code[0], "1001"));
        l = (scopesDao.getScopes(code[0]));
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(String.valueOf(1002), l.get(0));
        Assert.assertTrue(scopesDao.clear());
        Assert.assertEquals(0, scopesDao.getScopes(code[0]).size());
    }
    
    @Test
    public void testDataRoleUsersDao() throws DataAccessException, SQLException {
        p("" + usersDao.clear());
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
