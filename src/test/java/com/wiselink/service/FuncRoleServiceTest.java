/**
 * FuncRoleServiceTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-22 下午7:40:07
 */
package com.wiselink.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.wiselink.result.OperResult;

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
        OperResult<List<FuncModule>> r = service.allModules();
        List<FuncModule> modules = r.result;
        int i = 0;
        for (Iterator<?> it = modules.iterator(); it.hasNext() && i < 3; i ++) {
            FuncModule module = (FuncModule) it.next();
            List<Func> funcs = module.getFuncs();
            for (int j = 0; j < Math.min(2, funcs.size()); j ++) {
                fcodes.add(funcs.get(j).code);
            }
        }
        // Assert.assertEquals(6, fcodes.size());
        // List<Func> funcs = service.getFuncs(fcodes);
        // Assert.assertEquals(6, funcs.size());
        // System.out.println(funcs);
        // Assert.assertNotNull(funcs.get(5).name);
        // FuncRoleInfo r = new FuncRoleInfo().setName(name[0]).setDesc(desc[0])
        // .setCorpId(corpId[0]).setDeptId(deptId[0]).setCreatorId(creatorId[0]);
        // FuncRole role = service.newFuncRole(r).result;
        // System.out.println(role);
        // r = new FuncRoleInfo().setName(name[1]).setDesc(desc[1])
        // .setCorpId(corpId[1]).setDeptId(deptId[1]).setCreatorId(creatorId[1]);
        // role = service.newFuncRole(r);
        // System.out.println(role);
        // List<FuncRoleInfo> infos = service.getFuncRoles(new
        // QueryListParam().setPage(1).setSize(10));
        // System.out.println(infos);
        // Integer []f1 = new Integer[]{1, 2, 3};
        // String []u1 = new String[]{"1", "2", "3"};
        // service.updateFuncRole(code[0], Collections.<Integer>emptyList(),
        // Arrays.asList(f1),
        // Collections.<String>emptyList(), Arrays.asList(u1));
        // role = service.getFuncRole(code[0]);
        // System.out.println(role);
    }
}
