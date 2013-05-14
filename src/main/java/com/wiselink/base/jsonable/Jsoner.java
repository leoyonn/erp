/**
 * Jsoner.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午4:34:22
 */
package com.wiselink.base.jsonable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.lang.model.type.PrimitiveType;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.wiselink.utils.Primitive;
import com.wiselink.utils.Utils;

/**
 * @deprecated
 * @author leo
 */
public class Jsoner {
    /**
     * @param v
     * @return
     * @throws JSONException
     */
    public static String toJson(Object v) throws JSONException {
        // null
        if (v == null) {
            return "null";
        }
        // multi, Iterable/Array
        if (isMulti(v)) {
            return toJsonArray(v);
        }
        // primitive and Map
        if (v instanceof String) {
            return (String) v;
        } else if (v instanceof PrimitiveType || v instanceof Number || v instanceof Boolean) {
            return String.valueOf(v);
        } else if (v instanceof JSON) {
            return v.toString();
        } else if (v instanceof Map) {
            JSONObject res = new JSONObject();
            for (Entry<?, ?> e: ((Map<?, ?>) v).entrySet()) {
                res.put(toJson(e.getKey()), toJson(e.getValue()));
            }
            return res.toString();
        }
        // jsonable
        else if (v instanceof JsonableEnum) {
            return String.valueOf(((JsonableEnum) v).getCode());
        }
        // Other objects
        return reflectToJson(v);
    }

    /**
     * @param v
     * @return
     */
    protected static String toJsonArray(Object v) throws JSONException {
        if (v == null) {
            return "null";
        } else if (v instanceof Iterable) {
            JSONArray res = new JSONArray();
            for (Object o: (Iterable<?>)v) {
                if (o == null) {
                    res.add(null); // TODO can this be null?
                } else if (isMulti(o)) {
                    res.add(toJsonArray(o));
                } else {
                    res.add(toJson(o));
                }
            }
            return res.toString();
        } else if (v.getClass().isArray()) {
            JSONArray res = new JSONArray();
            for (int i = 0; i < Array.getLength(v); i++) {
                Object o = Array.get(v, i);
                if (o == null) {
                    res.add(null); // TODO can this be null?
                } else if (isMulti(o)) {
                    res.add(toJsonArray(o));
                } else {
                    res.add(toJson(o));
                }
            }
            return res.toString();
        }
        throw new JSONException("Not an array/iterable object: " + v.getClass());
    }

    protected static String toJsonArray(Iterable<?> it) throws JSONException {
        JSONArray res = new JSONArray();
        return res.toString();
    }

    protected static boolean isMulti(Object v) {
        return (v instanceof Iterable) || (v.getClass().isArray());
    }

    /**
     * @param v
     * @return
     */
    protected static String reflectToJson(Object v) throws JSONException {
        JSONObject res = new JSONObject();
        for (Field f: v.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(f.getModifiers())) {
                continue;
            }
            Object fv = reflectGetFieldValue(v, f.getName());
            res.put(f.getName(), toJson(fv));
        }
        return res.toString();
    }

    private static Object reflectGetFieldValue(Object v, String fieldName) throws JSONException {
        try {
            Method m = v.getClass().getMethod("get" + Utils.upperFirst(fieldName));
            return m.invoke(v);
        } catch (Exception ex) {
            // IllegalArgumentException e1, IllegalAccessException,
            // InvocationTargetException, SecurityException,
            // NoSuchMethodException
            throw new JSONException("Reflect " + v + " of field<" + fieldName + "> got exception!", ex);
        }
    }

    private static void reflectSetFieldValue(Object v, Field f, Object fv) throws JSONException {
        try {
            Method m = v.getClass().getMethod("set" + Utils.upperFirst(f.getName()), f.getType());
            fv = f.getType().cast(fv);
            m.invoke(v, fv);
        } catch (Exception ex) {
            // IllegalArgumentException e1, IllegalAccessException,
            // InvocationTargetException, SecurityException,
            // NoSuchMethodException
            throw new JSONException("Reflect " + v + " of field<" + f + "> got exception!", ex);
        }
    }

    protected static JsonableEnum reflectJsonableEnum(Field f, int code) {
        try {
            return ((JsonableEnum)f.getType().getFields()[0].get(null)).fromCode(code);
        } catch (Exception e) {
            // IllegalArgumentException SecurityException IllegalAccessException 
            return null;
        }
    }

    /**
     * get data from $json and set into $v.
     * @param json
     * @param v
     */
    public static void fromJson(String jsonStr, Jsonable v) throws JSONException {
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Field f: v.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(f.getModifiers())) {
                continue;
            }
            String fvStr = json.optString(f.getName());
            if (StringUtils.isEmpty(fvStr)) {
                continue;
            }
            reflectSetValue(v, f, fvStr);
        }
    }
    
    protected static Method getSetter(Class<?> c, Field f) throws JSONException {
        try {
            return c.getMethod("set" + Utils.upperFirst(f.getName()), f.getType());
        } catch (Exception e) {
            // SecurityException SuchMethodException
            throw new JSONException("class '" + c + "' has no setter of '" + f + "'.");
        }
    }

    // TODO: not completed.
    protected static void reflectSetValue(Jsonable v, Field f, String fvStr) throws JSONException {
        Method m = getSetter(v.getClass(), f);
        Object fv = null;
        if (f.getType().isPrimitive()) {
            fv = Primitive.valueof(f.getType().toString(), fvStr);
        } else if (Primitive.isWrapper(f.getType())) {
            fv = Primitive.valueofWrapper(f.getType(), fvStr);
        } else if (JsonableEnum.class.isAssignableFrom(f.getType())) {
            fv = reflectJsonableEnum(f, Integer.valueOf(fvStr));
        }else if (Jsonable.class.isAssignableFrom(f.getType())) {
            try {
                Jsonable jfv = (Jsonable) f.getType().newInstance();
                jfv.fromJson(fvStr);
                fv = jfv;
            } catch (Exception ex) {
                // InstantiationException IllegalAccessException
                throw new JSONException("Reflect field " + f + " got exception!", ex);
            }
        } else if (f.getType().isArray()) {
            // TODO
        } else if (Iterator.class.isAssignableFrom(f.getType())) {
            // TODO
        } else if (Map.class.isAssignableFrom(f.getType())) {
            // TODO
        }
        reflectSetFieldValue(v, f, fv);
        try {
            m.invoke(v, fv);
        } catch (Exception ex) {
            // IllegalArgumentException IllegalAccessException InvocationTargetException
            throw new JSONException("Invoke method:" + m + " of " + v + "<param:" + fv + ">", ex);
        }
    }
}