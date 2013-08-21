/**
 * TestUserDao.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 15, 2013 6:10:05 PM
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.user.UserRaw;

/**
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {
    @Autowired
    private UserDAO userDao;

    @Autowired
    private UserInfoDAO userInfoDao;

    @Autowired
    private UserRoleDAO roleDao;

    @Before
    public void init() {
    }

    private void p(Object o) {
        System.out.println(o);
    }

    @Test
    public void testUser() throws DataAccessException, SQLException {
        p(userDao.delete("1020300003", "1020300000"));
        p(userDao.delete("1020300004", "1020300000"));
        p(userDao.add("1020300003", "account-3", "张阿三", "pass1", "http://avatar.com/3.jpg",
                "zhangsan@picc.com", "13811811888", "95518-1", "我是张三", "河北省", "石家庄市",
                "1020300000", "1020300000", 0, 0, 101051, 102017, 0, "1020300000", "1020300001"));
        UserRaw raw = new UserRaw().setId("1020300004")
                .setAccount("account-4").setName("李阿四").setAvatar("http://avatar.com/4.jpg")
                .setEmail("zhangsan@picc.com").setPhone("13811811888").setTel("95518-1")
                .setDesc("我是张三").setProvince("河北省").setCity("石家庄市")
                .setCreateTime(null).setCreatorId("1020300000").setUpdateTime(null).setOperId("1020300000")
                .setCatCode(0).setPosCode(0).setFroleCode(101051).setDroleCode(102017)
                .setStatCode(0).setCorpId("1020300000").setDeptId("1020300001");
        p(userDao.add(raw, "pass1"));

        p(userDao.update("1020300003", "account-3", "张阿三", "http://avatar.com/3.jpg",
                "zhangsan@picc.com", "13811811888", "95518-1", "我是张三啊", "河北省", "石家庄市",
                "1020300000", "1020300000", 0, 0, 101051, 102017, 0, "1020300000", "1020300001"));

        p(userDao.update(raw.setDesc("我是李四啊")));
        p(userDao.updatePasswordById(raw.id, "pass1", "pass4"));
        p(userDao.getPasswordByAccount(raw.account));
        p(userDao.updatePasswordByAccount(raw.account, "pass4", "pass1"));
        p(userDao.getPasswordById(raw.id));
        p(userDao.getUserByAccount(raw.account));
        p(userDao.getUserById("1020300003"));
        p(userDao.all());
        p(userDao.queryUsers("%阿%", "", "", -1, -1, -1, 0, 10));
        p(userDao.getUserCardsById(Arrays.asList(new String[]{"1020300003", "1020300004"})));
        // p(userDao.delete(raw.id, "1020300000"));
        // p(userDao.delete("1020300003", "1020300003"));
        // p(userDao.delete("1020300003", "1020300000"));
        // p(userDao.all());
    }
    /*
    @Test
    public void testUserInfo() throws SQLException, DataAccessException {
        userInfoDao.clear();
        UserInfo info = n)ew UserInfo("1020300001", "account-1", "周鸿祎", "pass1", "http://avatar.com/1.jpg",
                "hongyi@picc.com", "13811811888", "95518-1", "人称教主啊我", "河北省", "石家庄市", null, "1020300000", null, "1020300000");
        UserInfo info2 = new UserInfo("1020300002", "account-2", "周鸿er", "pass2", "http://avatar.com/2.jpg",
                "honger@picc.com", "13811811882", "95518-2", "人称教主2啊我", "河北省", "保定市", null, "1020300001", null, "1020300001");
        // add
        boolean ok = userInfoDao.add(info.id, info.account, info.name, "pass1", info.avatar,
                info.email, info.phone, info.tel, info.desc, info.province, info.city, info.creatorId, info.operId);
        // get by id
        UserInfo got = userInfoDao.getUserById(info.id);
        p(info);
        p(got);
        Assert.assertTrue(ok);
        Assert.assertEquals(info.id, got.id);
        Assert.assertEquals(info.account, got.account);
        Assert.assertEquals(info.name, got.name);
        Assert.assertEquals(info.avatar, got.avatar);
        Assert.assertEquals(info.email, got.email);
        Assert.assertEquals(info.phone, got.phone);
        Assert.assertEquals(info.tel, got.tel);
        Assert.assertEquals(info.desc, got.desc);
        Assert.assertEquals(info.province, got.province);
        Assert.assertEquals(info.city, got.city);
        Assert.assertEquals(info.creatorId, got.creatorId);
        Assert.assertEquals(info.operId, got.operId);
        Assert.assertNotNull(got.createTime);
        Assert.assertNotNull(got.updateTime);
        // get by account
        got = userInfoDao.getUserByAccount(info.account);
        Assert.assertEquals(info.id, got.id);
        Assert.assertEquals(info.account, got.account);
        Assert.assertEquals(info.name, got.name);
        // unique: id, account
        try {
            userInfoDao.add(info.id, info.account + "a", info.name, "pass1", info.avatar,
                    info.email, info.phone, info.tel, info.desc, info.province, info.city, info.creatorId, info.operId);
            Assert.fail();
        } catch (Exception ex) {}
        try {
            userInfoDao.add(info.id + 1, info.account, info.name, "pass1", info.avatar,
                    info.email, info.phone, info.tel, info.desc, info.province, info.city, info.creatorId, info.operId);
            Assert.fail();
        } catch (Exception ex) {}
        // add another
        ok = userInfoDao.add(info2.id, info2.account, info2.name, "pass2", info2.avatar,
                info2.email, info2.phone, info2.tel, info2.desc, info2.province, info2.city, info2.creatorId, info2.operId);
        got = userInfoDao.getUserById(info2.id);
        Assert.assertTrue(ok);
        Assert.assertEquals(info2.id, got.id);
        Assert.assertEquals(info2.account, got.account);
       ) Assert.assertEquals(info2.name, got.name);
        p(info2);
        p(got);
        // list
        List<UserInfo> l = userInfoDao.getUsersById(Arrays.asList(new String[]{info.id, info2.id}));
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(info.id, l.get(0).id);
        Assert.assertEquals(info.account, l.get(0).account);
        Assert.assertEquals(info.name, l.get(0).name);
        Assert.assertEquals(info2.id, l.get(1).id);
        Assert.assertEquals(info2.account, l.get(1).account);
        Assert.assertEquals(info2.name, l.get(1).name);
        l = userInfoDao.getUsersById(Arrays.asList(new String[]{info.id, info2.id, "11001100"}));
        p(l);
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(info.id, l.get(0).id);
        Assert.assertEquals(info2.id, l.get(1).id);
        // update
        String oldAccount = info.account;
        ok = userInfoDao.update(info.id, info.account = info.account + "-b", info.name + "2号", info.avatar,
                info.email, info.phone, info.tel, info.desc, info.province, info.city, info.creatorId, info.operId);
        got = userInfoDao.getUserById(info.id);
        Assert.assertTrue(ok);
        Assert.assertEquals(info.id, got.id);
        Assert.assertEquals(info.account, got.account);
        Assert.assertEquals(info.name + "2号", got.name);
        Assert.assertNull(userInfoDao.getUserByAccount(oldAccount));
        got = userInfoDao.getUserByAccount(info.account);
        Assert.assertEquals(info.id, got.id);
        Assert.assertEquals(info.account, got.account);
        Assert.assertEquals(info.name + "2号", got.name);
        // update conflict
        try {
            userInfoDao.update(info.id, info2.account, info.name, info.avatar,
                    info.email, info.phone, info.tel, info.desc, info.province, info.city, info.creatorId, info.operId);
            Assert.fail();
        } catch (Exception ex) {}
        // get pass
        UserPass pass = userInfoDao.getPasswordById(info.id);
        Assert.assertEquals("pass1", pass.password);
        Assert.assertEquals(info.id, pass.id);
        Assert.assertEquals(info.account, pass.account);
        // get pass
        pass = userInfoDao.getPasswordByAccount(info.account);
        Assert.assertEquals("pass1", pass.password);
        Assert.assertEquals(info.id, pass.id);
        Assert.assertEquals(info.account, pass.account);
        Assert.assertTrue(userInfoDao.updatePasswordById(info.id, "pass1", "pass1id"));
        pass = userInfoDao.getPasswordByAccount(info.account);
        Assert.assertEquals("pass1id", pass.password);
        Assert.assertTrue(userInfoDao.updatePasswordByAccount(info.account, "pass1id", "pass1account"));
        pass = userInfoDao.getPasswordByAccount(info.account);
        Assert.assertEquals("pass1account", pass.password);
        Assert.assertFalse(userInfoDao.updatePasswordById(info.id, "pass1", "pass1id"));
        pass = userInfoDao.getPasswordByAccount(info.account);
        Assert.assertEquals("pass1account", pass.password);
        // delete
        Assert.assertTrue(userInfoDao.delete(info.id));
        Assert.assertNull(userInfoDao.getUserById(info.id));
        Assert.assertNull(userInfoDao.getUserByAccount(info.account));
        userInfoDao.clear();
    }

    @Test
    public void testUserRole() throws SQLException, DataAccessException {
        roleDao.clear();
        UserRoleC rolec = new UserRoleC("1020300001", 1, 1, 1, 1, 1, "11", "1111");
        UserRoleC rolec2 = new UserRoleC("1020300002", 2, 2, 2, 1, 1, "11", "1111");
        Assert.assertTrue(roleDao.addUserRole("1020300001", 1, 1, 1, 1, 1, "11", "1111"));
        UserRoleC got = roleDao.find(rolec.id);
        Assert.assertEquals(rolec.id, got.id);
        Assert.assertEquals(rolec.catCode, got.catCode);
        Assert.assertEquals(rolec.posCode, got.posCode);
        Assert.assertEquals(rolec.froleCode, got.froleCode);
        Assert.assertEquals(rolec.droleCode, got.droleCode);
        Assert.assertEquals(rolec.statCode, got.statCode);
        Assert.assertEquals(rolec.corpId, got.corpId);
        Assert.assertEquals(rolec.deptId, got.deptId);
        roleDao.updateUserRole("1020300001", 1, 2, 1, 2, 1, "11", "1112");
        got = roleDao.find(rolec.id);
        Assert.assertEquals(rolec.id, got.id);
        Assert.assertEquals(rolec.catCode, got.catCode);
        Assert.assertEquals(2, got.posCode);
        try {
            Assert.assertTrue(roleDao.addUserRole("1020300001", 1, 1, 1, 1, 1, "11", "1111"));
            Assert.fail();
        } catch (Exception ex) {}
        Assert.assertTrue(roleDao.addUserRole("1020300002", 2, 2, 2, 1, 1, "11", "1111"));
        got = roleDao.find(rolec2.id);
        Assert.assertEquals(rolec2.id, got.id);
        Assert.assertEquals(rolec2.catCode, got.catCode);
        // list
        List<UserRoleC> l = roleDao.getRoles(Arrays.asList(new String[] {"1020300002", "1020300001", "1020300003", "4"}));
        p(l);
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(1, l.get(0).catCode);
        Assert.assertEquals(2, l.get(1).catCode);
        // delete
        Assert.assertTrue(roleDao.delete(rolec.id));
        Assert.assertNull(roleDao.find(rolec.id));
        roleDao.clear();
    }
    */
}
