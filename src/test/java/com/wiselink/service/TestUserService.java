/**
 * TestUserService.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date May 15, 2013 6:12:22 PM
 */
package com.wiselink.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wiselink.utils.HttpUtils;

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
        System.out.println(service.checkPassword("1", "111"));
    }
    @Test
    public void testByHttp() {
        final String LOGIN_URL = "http://localhost:8080/auth/login";
        // good request
        Map<String, String> params = new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;
            {
                put("user", "account-1369537284743");
                put("password", "pass1");
            }
        };
        String res = HttpUtils.httpPost(LOGIN_URL, params);
        System.out.println(res);
    }
    

}
