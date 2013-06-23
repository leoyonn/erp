/**
 * DataMocker.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-22 下午9:11:08
 */
package com.wiselink.mock;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.wiselink.dao.FuncRoleFuncsDAO;
import com.wiselink.dao.FuncRoleInfoDAO;
import com.wiselink.dao.FuncRoleUsersDAO;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * 
 * @author leo
 */
public class Mocker {
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
            code[i] = 100 + i;
            name[i] = "测试功能角色-" + i;
            desc[i] = "这是一个测试功能角色啊-" + i;
            corpId[i] = "1100" + i;
            deptId[i] = "1200" + i;
            creatorId[i] = "1300" + i;
        }
    }

    @Test
    public void mock() throws DataAccessException, SQLException {
        mockFuncRoles();
    }

    private void p(String s) {
        System.out.println(s);
    }

    private void mockFuncRoles() throws DataAccessException, SQLException {
        p("cleared:" + infoDao.clear());
        FuncRoleInfo info = infoDao.find(code[0]);
        Assert.assertNull(info);
        // add 0
        for (int i = 0; i < N; i ++) {
            infoDao.add(name[i], desc[i], corpId[i], deptId[i], creatorId[i]);
            code[i] = infoDao.findByName(name[i]).code;
        }
    }
}
