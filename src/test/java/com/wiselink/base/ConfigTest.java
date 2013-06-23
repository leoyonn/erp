/**
 * Config.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 上午10:54:34
 */
package com.wiselink.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leo
 */
public final class ConfigTest {
    @Test
    public void testLogger() {
        Logger LOGGER = LoggerFactory.getLogger(ConfigTest.class);
        LOGGER.debug("{DEBUG} test 啊啊啊");
        LOGGER.info("{INFO} test 啊啊啊");
        LOGGER.warn("{WARN} test 啊啊啊");
        LOGGER.error("{ERROR} test 啊啊啊");
    }
}