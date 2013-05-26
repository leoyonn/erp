/**
 * SqlBuilder.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-25 下午4:30:08
 */
package com.wiselink.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @SuppressWarnings("unchecked")
 * @author leo
 */
public class SqlBuilder {
    public static enum Type {
        INSERT, UPDATE, SELECT
    }

    private Type type = Type.INSERT;
    private StringBuilder sb = new StringBuilder();
    private String tableName = "";
    private String[] params = null;
    private String[] values = null;

    public SqlBuilder(Type type) {
        this.type = type;
    }
    
    public SqlBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
    
    public SqlBuilder setParams(String ...params) {
        this.params = params;
        return this;
    }
    
    public SqlBuilder setValues(String ...values) {
        this.values = values;
        return this;
    }

    private static boolean empty(String[] arr) {
        return arr == null || arr.length == 0;
    }

    private String buildInsert() {
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("tableName can not be null.");
        }
        sb.append("INSERT INTO ").append(tableName).append(" ");
        if (empty(params) && empty(values)) {
            return sb.append("VALUES()").toString();
        }
        if (empty(params) || empty(values) || params.length != values.length) {
            throw new IllegalArgumentException("params and values not match!");
        }
        for (String p: params) {
            sb.append(p).append(",");
        }
        sb.setCharAt(sb.length() - 1, ' ');
        sb.append("VALUES(");
        for (String v: values) {
            sb.append(v).append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    /**
     * @return
     * @throws IllegalArgumentException
     */
    public String build() throws IllegalArgumentException {
        switch (type) {
            case INSERT: return buildInsert();
            case SELECT: return buildSelect();
            case UPDATE: return buildUpdate();
        }
        throw new IllegalArgumentException("not supported");
    }

    /**
     * @return
     */
    private String buildUpdate() {
        throw new RuntimeException("not implemented yet");
    }

    /**
     * @return
     */
    private String buildSelect() {
        throw new RuntimeException("not implemented yet");
    }
        
}
