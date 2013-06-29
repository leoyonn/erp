/**
 * TestIdUtils.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-29 上午10:26:29
 */
package com.wiselink.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author leo
 */
public class TestIdUtils {
    @Test
    public void testUserIdLegal() {
        Assert.assertFalse(IdUtils.isUserIdLegal(""));
        Assert.assertFalse(IdUtils.isUserIdLegal("abc"));
        Assert.assertFalse(IdUtils.isUserIdLegal("abc1234567"));
        Assert.assertFalse(IdUtils.isUserIdLegal("123456789"));
        Assert.assertTrue(IdUtils.isUserIdLegal("0123456789"));
        Assert.assertTrue(IdUtils.isUserIdLegal("1000000000"));
        Assert.assertTrue(IdUtils.isUserIdLegal("0000000001"));
    }

    @Test
    public void testUserAccountLegal() {
        Assert.assertFalse(IdUtils.isUserAccountLegal(""));
        Assert.assertFalse(IdUtils.isUserAccountLegal("1"));
        Assert.assertFalse(IdUtils.isUserAccountLegal("1232323"));
        Assert.assertFalse(IdUtils.isUserAccountLegal("---"));
        Assert.assertFalse(IdUtils.isUserAccountLegal("1-_"));
        Assert.assertFalse(IdUtils.isUserAccountLegal("-_-"));
        Assert.assertFalse(IdUtils.isUserAccountLegal("a1"));
        Assert.assertFalse(IdUtils.isUserAccountLegal("a123456789012345678901234567890"));

        Assert.assertTrue(IdUtils.isUserAccountLegal("d-_-b"));
        Assert.assertTrue(IdUtils.isUserAccountLegal("o____________________________o"));
        Assert.assertTrue(IdUtils.isUserAccountLegal("q-q"));
        Assert.assertTrue(IdUtils.isUserAccountLegal("T_A_T"));
        Assert.assertTrue(IdUtils.isUserAccountLegal("a12"));
        Assert.assertTrue(IdUtils.isUserAccountLegal("a12345678901234567890123456789"));
    }
}
