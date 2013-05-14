/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:29:50
 */
package com.wiselink.base;

import com.google.gson.Gson;

/**
 * @author leo
 */
public class ApiResult {
    private ApiStatus status;
    private String result;
    
    public ApiResult(ApiStatus status, String result) {
       this.status = status;
       this.result = result;
    }
    
    public String toJson() {
        return new Gson().toJson(this, ApiResult.class);
    }
    
    public String toString() {
        return toJson();
    }

    public ApiStatus getStatus() {
        return status;
    }

    public void setStatus(ApiStatus status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
