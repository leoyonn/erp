/**
 * FuncRoleServiceTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-22 下午7:40:07
 */
package com.wiselink.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.exception.ServiceException;
import com.wiselink.model.role.Func;
import com.wiselink.model.role.FuncModule;
import com.wiselink.model.role.FuncRole;
import com.wiselink.model.role.FuncRoleInfo;

/**
 * @author leo
 */
@SuppressWarnings("easymock database, don't connect to server.")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FuncRoleServiceTest {
    @Autowired
    private FuncRoleService service;

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
    public void test() throws ServiceException, DataAccessException, SQLException {
        service.clearAll();
        List<Integer> fcodes = new ArrayList<Integer>();
        Collection<FuncModule> modules = service.allModules();
        int i = 0;
        for (Iterator<?> it = modules.iterator(); it.hasNext() && i < 3; i ++) {
            FuncModule module = (FuncModule) it.next();
            List<Func> funcs = module.getFuncs();
            for (int j = 0; j < Math.min(2, funcs.size()); j ++) {
                fcodes.add(funcs.get(j).code);
            }
        }
        Assert.assertEquals(6, fcodes.size());
        List<Func> funcs = service.getFuncs(fcodes);
        Assert.assertEquals(6, funcs.size());
        System.out.println(funcs);
        Assert.assertNotNull(funcs.get(5).name);
        FuncRole role = service.newFuncRole(name[0], desc[0], corpId[0], deptId[0], creatorId[0]);
        System.out.println(role);
        role = service.newFuncRole(name[1], desc[1], corpId[1], deptId[1], creatorId[1]);
        System.out.println(role);
        List<FuncRoleInfo> infos = service.getFuncRoles(code[0], 2);
        System.out.println(infos);
        Integer []f1 = new Integer[]{1, 2, 3};
        Integer []f2 = new Integer[]{4, 5, 6};
        String []u1 = new String[]{"1", "2", "3"};
        String []u2 = new String[]{"4", "5", "6"};
        service.updateFuncRole(code[0], Collections.<Integer>emptyList(), Arrays.asList(f1),
                Collections.<String>emptyList(), Arrays.asList(u1));
        role = service.getFuncRole(code[0]);
        System.out.println(role);
    }
}
