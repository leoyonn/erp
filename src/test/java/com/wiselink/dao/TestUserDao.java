/**
 * TestUserDao.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 15, 2013 6:10:05 PM
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.util.Random;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.User;
import com.wiselink.model.base.DataRole;
import com.wiselink.model.base.FuncRole;
import com.wiselink.model.base.UserCategory;
import com.wiselink.model.base.UserStatus;
import com.wiselink.model.base.UserType;
import com.wiselink.utils.IdUtils;

/**
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserDao {

    @Autowired
    private UserDAO userDao;

    @Qualifier("jade.dataSource.com.wiselink.dao")
    @Autowired
    private BasicDataSource dataSource;


    @Before
    public void init() {
        /*
        try {
            Connection conn = dataSource.getConnection();
            Statement st = conn.createStatement();
            st.execute("drop all objects;");
            st.execute("runscript from '" + new DefaultResourceLoader()
                    .getResource("sql/test.sql").getURL().toString() + "'");
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @Test
    public void test() throws SQLException {
        User user = new User();
        String account = "account-" + new Random().nextInt(); 
        user.setAccount(account)
        .setAvatar("http://avatar/1.jpg")
        .setCat(UserCategory.CORP_L0.name())
        .setCity("石家庄市")
        .setCorpId("corp1111")
        .setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()))
        .setDeptId("dept1111")
        .setDesc("我是一名测试用户")
        .setDrole(DataRole.NULL.name())
        .setEmail("test@test.com")
        .setFrole(FuncRole.NULL.name())
        .setId(IdUtils.genUserId(account))
        .setName("名字1")
        .setOpUserId(11110000L)
        .setPassword("pass1")
        .setPhone("13811811111")
        .setProvince("河北省")
        .setStat(UserStatus.NULL.name())
        .setTel("010-11111111")
        .setType(UserType.CEO.name())
        .setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userDao.addUser(user);
        System.out.println(userDao);
        Assert.assertEquals(user.getPassword(), userDao.getPassword(user.getId()));
        User u2 = userDao.getUser(user.getAccount());
        Assert.assertEquals(user.getPassword(), u2.getPassword());
        User u3 = userDao.getUser(user.getId());
        Assert.assertEquals(user.getPassword(), u3.getPassword());
    }
}
