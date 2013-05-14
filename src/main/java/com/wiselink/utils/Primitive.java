/**
 * Primitive.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午10:06:12
 */
package com.wiselink.utils;

import java.util.HashSet;
import java.util.Set;


/**
 * usage: Primitive.valueOf("long", "111L")
 * for converting string into primitive's wrapper.
 * @author leo
 */
public class Primitive {
    public static enum Type {
        BOOLEAN,
        BYTE,
        SHORT,
        INT,
        LONG,
        CHAR,
        FLOAT,
        DOUBLE;

        public static Type get(String name) {
            return Type.valueOf(name.toUpperCase());
        }
    }
    
    public static Object valueof(String type, String value) {
        Type t = Type.get(type);
        if (t == null) {
            return null;
        }
        switch(t) {
            case BOOLEAN: return Boolean.valueOf(value);
            case BYTE: return Byte.valueOf(value);
            case SHORT: return Short.valueOf(value);
            case INT: return Integer.valueOf(value);
            case LONG: return Long.valueOf(value);
            case CHAR: return value.charAt(0);
            case FLOAT: return Float.valueOf(value);
            case DOUBLE: return Double.valueOf(value);
        }
        return value;
    }

    private static final Set<Class<?>> WRAPPERS = new HashSet<Class<?>>();
    static {
        WRAPPERS.add(java.lang.Boolean.class);
        WRAPPERS.add(java.lang.Byte.class);
        WRAPPERS.add(java.lang.Short.class);
        WRAPPERS.add(java.lang.Integer.class);
        WRAPPERS.add(java.lang.Long.class);
        WRAPPERS.add(java.lang.Double.class);
        WRAPPERS.add(java.lang.Float.class);
        WRAPPERS.add(java.lang.Character.class);
    }

    public static boolean isWrapper(Class<?> c) {
        return WRAPPERS.contains(c);
    }

    public static Object valueofWrapper(Class<?> wrapperClass, String value) {
        if (wrapperClass.equals(java.lang.Boolean.class)) {
            return Boolean.valueOf(value);
        } else if (wrapperClass.equals(java.lang.Byte.class)) {
            return Byte.valueOf(value);
        } else if (wrapperClass.equals(java.lang.Short.class)) {
            return Short.valueOf(value);
        } else if (wrapperClass.equals(java.lang.Integer.class)) {
            return Integer.valueOf(value);
        } else if (wrapperClass.equals(java.lang.Long.class)) {
            return Long.valueOf(value);
        } else if (wrapperClass.equals(java.lang.Character.class)) {
            return value.charAt(0);
        } else if (wrapperClass.equals(java.lang.Float.class)) {
            return Float.valueOf(value);
        } else if (wrapperClass.equals(java.lang.Double.class)) {
            return Double.valueOf(value);
        }
        return value;
    }

}
