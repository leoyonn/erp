/**
 * TestUserDao.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 15, 2013 6:10:05 PM
 */
package com.wiselink.dao;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.user.UserRole;

/**
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserDao {

    @Autowired
    private UserInfoDAO userDao;

    @Qualifier("jade.dataSource.com.wiselink.dao")
    @Autowired
    private BasicDataSource dataSource;


    @Before
    public void init() {
        userDao.getClass();
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
    public void test() throws SQLException, DataAccessException {
/*        User user = new User();
        String account = "account-" + 3; 
        user.setAccount(account)
        .setAvatar("http://avatar/3.jpg")
        .setCity("石家庄市")
        .setCorpId("corp1111")
        .setCreateTime(System.currentTimeMillis())
        .setDeptId("dept1111")
        .setDesc("我是一名测试用户3")
        .setEmail("test@test.com")
        .setId(IdUtils.genUserId(account))
        .setName("名字3")
        .setOpUserId("11110000")
        .setPassword("pass3")
        .setPhone("13811811111")
        .setProvince("河北省")
        .setTel("010-11111111")
        .setUpdateTime(System.currentTimeMillis());
        userDao.addUser(
                user.id,
                user.account,
                user.name,
                user.password,
                user.avatar,
                user.email,
                user.phone,
                user.tel,
                user.desc,
                user.corpId,
                user.deptId,
                user.province,
                user.city,
                user.opUserId);
        System.out.println(userDao);
        Assert.assertEquals(user.getPassword(), userDao.getPassword(user.getId()));
        User u2 = userDao.getUserByAccount(user.getAccount());
        Assert.assertEquals(user.getPassword(), u2.getPassword());
        User u3 = userDao.getUserById(user.getId());
        Assert.assertEquals(user.getPassword(), u3.getPassword());
  */      
        UserRole role = new UserRole();
//                .setCat(UserCategory.Corp.name())
//                .setDrole(DataRole.NULL.name())
//                .setFrole("")
//                .setStat(UserStatus.NULL.name())
//                .setType(UserType.CEO.name());
        // TODO test role
        System.out.println(role);
    }
}
