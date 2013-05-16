/**
 * TestSecurityService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 15, 2013 6:12:22 PM
 */
package com.wiselink.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.service.SecurityService;

/**
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSecurityServicex {

    @Autowired
    SecurityService servcie;
    
    @Test
    public void test() {
        SecurityService service = new SecurityService();
        System.out.println(service.checkPassword(1, "111"));
    }
}
