/**
 * AvatarDaoTest.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-11 上午10:05:51
 */
package com.wiselink.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.h2.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author leo
 */
@SuppressWarnings("easymock database, don't connect to server.")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AvatarDaoTest {
    @Autowired
    AvatarDAO dao;

    @Test
    public void test() throws IOException, DataAccessException, SQLException {
        String url = "avatar/u.1111";
        File f = new File("G:/head.jpg");
        InputStream is = new FileInputStream(f);
        int size = (int) f.length();
        byte[] data = new byte[size];
        IOUtils.readFully(is, data, 0, size);
        dao.add(url, data);
        byte[] got = dao.get(url);
        System.out.println(data.length);
        System.out.println(got.length);
        FileOutputStream os = new FileOutputStream(new File("G:/got.jpg"));
        os.write(got);
        os.close();
    }

}
