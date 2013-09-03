/**
 * ListParam.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-17 上午11:09:05
 */
package com.wiselink.model.param;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.wiselink.base.jsonable.Jsonable;

/**
 * @author leo
 */
public class QueryListParam implements Jsonable {
    public static enum QueryType {
        and, or, not;
        
        public static QueryType value(String v) {
            try {
                return valueOf(v);
            } catch (Exception ex) {
                return and;
            }
        }
    }

    public int page;

    public int size;

    public QueryType q = QueryType.and;

    public Map<String, String> fields = new HashMap<String, String>();

    
    public int getPage() {
        return page;
    }

    public QueryListParam setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public QueryListParam setSize(int size) {
        this.size = size;
        return this;
    }

    public QueryType getQ() {
        return q;
    }

    public QueryListParam setQ(QueryType q) {
        this.q = q;
        return this;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public QueryListParam setFields(Map<String, String> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this, getClass());
    }

    @Override
    public Jsonable fromJson(String json) {
        JSONObject j = JSONObject.fromObject(json);
        this.page = j.optInt("page", 1);
        this.size = j.optInt("size", 15);
        JSONObject where = j.getJSONObject("where");
        for (@SuppressWarnings("unchecked") Iterator<String> it = where.keys(); it.hasNext();) {
            String k = it.next();
            if (k.equals("con")) {
                this.q = QueryType.value(where.getString(k));
            } else {
                this.fields.put(k, where.getString(k));
            }
        }
        return this;
    }
    
    @Override
    public String toString() {
        return toJson();
    }

    public String getStringField(String key, String defaultValue) {
        String v = fields.get(key);
        if (StringUtils.isBlank(v)) {
            v = defaultValue;
        }
        return v;
    }

    public String getLikeField(String key, String defaultValue) {
        return "%" + getStringField(key, defaultValue) + "%";
    }

    public int getIntField(String key, int defaultValue) {
        String v = fields.get(key);
        if (StringUtils.isBlank(v)) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(v);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
