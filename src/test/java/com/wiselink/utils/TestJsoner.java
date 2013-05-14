/**
 * TestJsoner.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午6:43:29
 */
package com.wiselink.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gson.Gson;
import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;
import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.base.jsonable.JsonableEnum;
import com.wiselink.model.User;

/**
 * @author leo
 */
public class TestJsoner {
    @Test
    public void testGson() {
        String json = new ApiResult(ApiStatus.SUCCESS, "this is a test msg").toJson();
        System.out.println(json);
        System.out.println(new User().toJson());
    }
    @Test
    public void testReflect() throws SecurityException, NoSuchFieldException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Field f = Cj.class.getDeclaredField("f4");
        System.out.println(((JsonableEnum)f.getType().getFields()[0].get(null)).fromCode(1));
        Assert.assertTrue(JsonableEnum.class.isAssignableFrom(f.getType()));
        Assert.assertFalse(Modifier.isTransient(f.getModifiers()));
        f = Cj.class.getDeclaredField("f5");
        Assert.assertTrue(Modifier.isTransient(f.getModifiers()));
        f = Cj.class.getDeclaredField("f2");
        System.out.println(f.getType().isPrimitive());
        for (Field f1: Ej.A.getClass().getDeclaredFields()) {
            System.out.println(f1.getName() + "||" + f1.getType() + "||" + Ej.A.getClass());
        }
    }

    @Test
    public void testJsoner() {
        List<Integer> l = new ArrayList<Integer>();
        l.add(3);
        l.add(4);
        Cj cj = new Cj("aaa", 12L, l, Ej.A);
        String json = cj.toJson();
        System.out.println(json);
        cj.fromJson(json);
        System.out.println(cj);
    }
    
    protected static class Cj implements Jsonable {
        private String f1;
        private long f2;
        private List<Integer> f3;
        private Ej f4;
        private transient int f5;

        public Cj(String f1, Long f2, List<Integer> f3, Ej f4) {
            this.f1 = f1;
            this.f2 = f2;
            this.f3 = f3;
            this.f4 = f4;
        }
        
        public String getF1() {
            return f1;
        }

        public void setF1(String f1) {
            this.f1 = f1;
        }

        public Long getF2() {
            return f2;
        }

        public void setF2(long f2) {
            this.f2 = f2;
        }

        public List<Integer> getF3() {
            return f3;
        }

        public void setF3(List<Integer> f3) {
            this.f3 = f3;
        }

        public Ej getF4() {
            return f4;
        }

        public void setF4(Ej f4) {
            this.f4 = f4;
        }

        public int getF5() {
            return f5;
        }

        @Override
        public String toJson() {
            return new Gson().toJson(this, getClass());
        }

        @Override
        public Jsonable fromJson(String json) {
            return new Gson().fromJson(json, Cj.class);
        }
        
        public String toString() {
            return toJson();
        }
    }
    
    public enum Ej implements JsonableEnum {
        A(0, "AAA"),
        B(1, "BBB");

        private int code;
        private String name;

        Ej(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public Ej fromCode(int i) {
            switch (i) {
                case 0: return A;
                case 1: return B;
            }
            return null;
        }
    }


}
