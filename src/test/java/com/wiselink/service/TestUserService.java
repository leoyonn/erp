/**
 * TestUserService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 15, 2013 6:12:22 PM
 */
package com.wiselink.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author leo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserService {

    @Autowired
    UserService servcie;
    
    @Test
    public void test() {
        UserService service = new UserService();
        System.out.println(service.checkPassword(1, "111"));
    }
}
