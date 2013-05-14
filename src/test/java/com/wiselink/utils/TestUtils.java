/**
 * TestUtils.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午6:43:41
 */
package com.wiselink.utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author leo
 */
public class TestUtils {
    @Test
    public void testUtils() {
        Assert.assertEquals("", Utils.upperFirst(""));
        Assert.assertEquals("1", Utils.upperFirst("1"));
        Assert.assertEquals("_", Utils.upperFirst("_"));
        Assert.assertEquals("1a", Utils.upperFirst("1a"));
        Assert.assertEquals("A1", Utils.upperFirst("a1"));
        Assert.assertEquals("Aa", Utils.upperFirst("aa"));
        Assert.assertEquals("Z", Utils.upperFirst("z"));
        Assert.assertEquals("Zzz", Utils.upperFirst("zzz"));
        Assert.assertEquals("ZZ", Utils.upperFirst("zZ"));
        Assert.assertEquals("ZZ", Utils.upperFirst("ZZ"));
    }

    @Test
    public void testPrimitive() {
        Assert.assertEquals(true, Primitive.valueof("boolean", "true"));
        Assert.assertEquals(false, Primitive.valueof("boolean", "false"));
        Assert.assertEquals(Byte.MIN_VALUE, Primitive.valueof("byte", Byte.MIN_VALUE + ""));
        Assert.assertEquals(Byte.MAX_VALUE, Primitive.valueof("byte", Byte.MAX_VALUE + ""));
        Assert.assertEquals(Short.valueOf((short) 123), Primitive.valueof("short", "123"));
        Assert.assertEquals(Long.MAX_VALUE, Primitive.valueof("long", Long.MAX_VALUE + ""));
        Assert.assertEquals(Double.valueOf(123.0), Primitive.valueof("double", "123.0"));
        Assert.assertEquals(Float.valueOf(123.0123f), Primitive.valueof("float", "123.0123f"));
        Assert.assertEquals('c', Primitive.valueof("char", "c"));

        Assert.assertEquals(true, Primitive.valueofWrapper(java.lang.Boolean.class, "true"));
        Assert.assertEquals(false, Primitive.valueofWrapper(java.lang.Boolean.class, "false"));
        Assert.assertEquals(Byte.MIN_VALUE, Primitive.valueofWrapper(java.lang.Byte.class, Byte.MIN_VALUE + ""));
        Assert.assertEquals(Byte.MAX_VALUE, Primitive.valueofWrapper(java.lang.Byte.class, Byte.MAX_VALUE + ""));
        Assert.assertEquals(Short.valueOf((short) 123), Primitive.valueofWrapper(java.lang.Short.class, "123"));
        Assert.assertEquals(Long.MAX_VALUE, Primitive.valueofWrapper(java.lang.Long.class, Long.MAX_VALUE + ""));
        Assert.assertEquals(Double.valueOf(123.0), Primitive.valueofWrapper(java.lang.Double.class, "123.0"));
        Assert.assertEquals(Float.valueOf(123.0123f), Primitive.valueofWrapper(java.lang.Float.class, "123.0123f"));
        Assert.assertEquals('c', Primitive.valueofWrapper(java.lang.Character.class, "c"));
    }
}
