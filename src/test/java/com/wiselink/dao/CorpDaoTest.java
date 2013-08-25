/**
 * CorpDaoTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-25 下午10:30:12
 */
package com.wiselink.dao;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.org.Corp;

/**
 * 
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CorpDaoTest {
    @Autowired
    private CorpDAO corpDao;

    @Test
    public void test() throws DataAccessException, SQLException {
        corpDao.delete("1020301100");
        boolean ok = corpDao.addCorp("1020301100", "省分公司", "中国人保河北省xx市分公司", "河北石家庄的省分公司", "河北省石家庄市石头路二大街",
                "048-95518", "周晓明", "1021300000");
        System.out.println(ok);
        ok = corpDao.updateCorp("1020301100", "省分公司", "中国人保河北省xx市分公司", "河北石家庄的省分公司", "河北省石家庄市石头路二大街",
                "048-95518-2", "周晓明", "1021300000");
        System.out.println(ok);
        ok = corpDao.updateCorp((Corp) new Corp()
                .setAddress("河北省石家庄市石头路二大街").setTel("048-95518-2")
                .setContact("周大明").setSuperCorpId("1021300000")
                .setId("1020301100").setType("省分公司")
                .setName("中国人保河北省xx市分公司").setDesc("河北石家庄的省分公司"));
        System.out.println(ok);
    }
}
