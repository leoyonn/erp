/**
 * SupplierDaoTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-21 下午2:48:05
 */
package com.wiselink.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.model.supplier.Supplier;
import com.wiselink.model.supplier.SupplierMode;
import com.wiselink.model.supplier.SupplierStatus;
import com.wiselink.model.supplier.SupplierType;

/**
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SupplierDaoTest {
    @Autowired
    private SupplierDAO dao;

    @Test
    public void test() throws DataAccessException, SQLException {
        dao.clear();
        final int N = 10;
        Supplier[] sups = new Supplier[N];
        for (int i = 0; i < N; i ++) {
            sups[i] = new Supplier().setContact("周润发." + i)
                    .setCreatorId("10203040" + i)
                    .setDesc("说明" + i)
                    .setEmail("sdf@sdfsdfsfd.sdf" + i)
                    .setEndTime(new Timestamp(System.currentTimeMillis()))
                    .setId("1111" + i)
                    .setMode(SupplierMode.Direct.cname)
                    .setName("某供货商公司" + i)
                    .setStartTime(new Timestamp(System.currentTimeMillis() + 100000 + i) )
                    .setStatus(SupplierStatus.Normal.cname)
                    .setTel("95518")
                    .setType(SupplierType.Limited.cname);
        }
        final int NM = N / 2, NT = N * 2 / 3, NS = N * 3 / 4;
        for (int i = 0; i < NM; i++) {
            sups[i].setMode(SupplierMode.Central.cname);
        }
        for (int i = 0; i < NT; i++) {
            sups[i].setType(SupplierType.Individual.cname);
        }
        for (int i = 0; i < NS; i++) {
            sups[i].setStatus(SupplierStatus.Stop.cname);
        }
        // add
        Supplier sup = sups[0];
        dao.add(sup.id, sup.name, sup.desc, sup.type, sup.mode, sup.contact, sup.tel, sup.email, sup.status,
                sup.startTime, sup.endTime, sup.creatorId);
        Supplier got = dao.findById(sup.id);
        Assert.assertEquals(got.id, sup.id);
        Assert.assertEquals(got.status, sup.status);
        got = dao.findByName(sup.name).get(0);
        Assert.assertEquals(got.id, sup.id);
        Assert.assertEquals(got.status, sup.status);
        // add by whole
        sup = sups[1];
        dao.add(sup);
        got = dao.findByName(sup.name).get(0);
        Assert.assertEquals(got.id, sup.id);
        Assert.assertEquals(got.status, sup.status);
        List<Supplier> l = dao.all();
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(sups[0].startTime, l.get(0).startTime);
        Assert.assertEquals(sups[1].endTime, l.get(1).endTime);
        sup = sups[2];
        sup.setEndTime(null).setStartTime(null);
        dao.add(sup);
        got = dao.findByName(sup.name).get(0);
        Assert.assertTrue(got.startTime.after(new Timestamp(System.currentTimeMillis() - 2000)));
        Assert.assertTrue(got.endTime.after(new Timestamp(System.currentTimeMillis() - 2000)));
        Assert.assertTrue(dao.delete(sup.id));
        // delete by id
        Assert.assertTrue(dao.delete(sups[0].id));
        Assert.assertEquals(0, dao.findByName(sups[0].name).size());
        // delete by name
        Assert.assertTrue(dao.deleteByName(sups[1].name));
        Assert.assertEquals(0, dao.findByName(sups[0].name).size());
        l = dao.all();
        Assert.assertEquals(0, l.size());
        // query
        for (int i = 0; i < N; i ++) {
            dao.add(sups[i]);
        }
        l = dao.find(SupplierType.Natural.cname, null, null);
        Assert.assertEquals(0, l.size());
        l = dao.find(SupplierType.Individual.cname, null, null);
        Assert.assertEquals(NT, l.size());
        Assert.assertEquals(sups[0].email, l.get(0).email);
        Assert.assertEquals(sups[NT - 1].creatorId, l.get(NT - 1).creatorId);
        l = dao.find(null, SupplierMode.General.cname, null);
        Assert.assertEquals(0, l.size());
        l = dao.find(SupplierMode.Central.cname, null, null);
        Assert.assertEquals(0, l.size());
        l = dao.find(null, SupplierMode.Central.cname, null);
        Assert.assertEquals(NM, l.size());
        Assert.assertEquals(sups[0].desc, l.get(0).desc);
        Assert.assertEquals(sups[NM - 1].creatorId, l.get(NM - 1).creatorId);
        l = dao.find(null, null, SupplierStatus.Delete.cname);
        Assert.assertEquals(0, l.size());
        l = dao.find(null, null, SupplierStatus.Stop.cname);
        Assert.assertEquals(NS, l.size());
        Assert.assertEquals(sups[0].desc, l.get(0).desc);
        Assert.assertEquals(sups[NS - 1].creatorId, l.get(NS - 1).creatorId);
        // update
        sup = sups[1];
        sup.setId(sups[0].id);
        dao.update(sup.id, sup.name, sup.desc, sup.type, sup.mode, sup.contact, sup.tel, sup.email, sup.status,
                sup.startTime, sup.endTime);
        got = dao.findById(sup.id);
        Assert.assertEquals(got.id, sup.id);
        Assert.assertEquals(got.desc, sup.desc);
        Assert.assertEquals(got.name, sup.name);
        Assert.assertEquals(got.creatorId, sups[0].creatorId);
        Supplier sup2 = sups[2];
        dao.update(sup.id, sup2.name, sup2.desc, sup2.type, null, null, null, null, null,
                sup2.startTime, null);
        got = dao.findById(sup.id);
        Assert.assertEquals(got.desc, sup2.desc);
        Assert.assertEquals(got.name, sup2.name);
        got = dao.findById(sup2.id);
        Assert.assertEquals(got.desc, sup2.desc);
        String contact = sup2.contact, tel = sup2.tel;
        sup2 = sups[3].setId(sups[2].id);
        sup2.setName(sups[3].name).setDesc(sups[4].desc).setEmail(sups[5].email).setContact(null).setTel(null);
        dao.update(sup2);
        got = dao.findById(sup2.id);
        Assert.assertEquals(got.desc, dao.findByName(sups[3].name).get(0).desc);
        Assert.assertEquals(got.name, sups[3].name);
        Assert.assertEquals(got.desc, sups[4].desc);
        Assert.assertEquals(got.email, sups[5].email);
        Assert.assertEquals(got.contact, contact);
        Assert.assertEquals(got.tel, tel);
        dao.clear();
    }
}
