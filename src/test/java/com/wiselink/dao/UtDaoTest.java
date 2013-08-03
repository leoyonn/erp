/**
 * UtDaoTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-21 上午11:11:48
 */
package com.wiselink.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.Ut;

/**
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UtDaoTest {
    @Autowired
    private UtDAO utDao;

    private void p(Object o) {
        System.out.println(o);
    }

    @Test
    public void test() {
        utDao.clear();
        final int N = 10;
        List<Ut> uts = new ArrayList<Ut>(N);
        for (int i = 0; i < N; i++) {
            uts.add(new Ut("100" + i, "leo-" + i, 28 + i, "13811111" + i));
        }
        // 1. add singly
        Ut ut = uts.get(0);
        utDao.add(ut.id, ut.name, ut.age, ut.tel);
        p(utDao.get(ut.getId()));
        // 2. add wholy
        utDao.add(uts.get(1));
        p(utDao.get(uts.get(1).id));
        // 3. update 
        utDao.update(ut.id, null, 0, ut.tel + "-302");
        p(utDao.get(ut.getId()));
        // 4. batch add and query
        utDao.add("1", "name1", 1, "2");
        utDao.add("2", "name2", 1, "3");
        utDao.add("3", "name3", 1, "3");
        utDao.add("4", "name4", 2, "3");
        p(utDao.query(new Ut(null, null, 1, "3")));
        p(utDao.query(new Ut(null, null, 1, null)));
        p(utDao.query(new Ut(null, null, 0, "3")));
        p(utDao.query(new Ut(null, null, 0, null)));
        utDao.clear();
    }
}
