/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:29:50
 */
package com.wiselink.base;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author leo
 */
public class ApiResult {
    private static final Map<AuthResult, ApiResult> AUTH_FAIL = new HashMap<AuthResult, ApiResult>();
    static {
        for (AuthResult authResult: AuthResult.values()) {
            if (authResult != AuthResult.SUCCESS) {
                AUTH_FAIL.put(authResult, new ApiResult(ApiStatus.fromAuthResult(authResult), null));
            }
        }
    }

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

    /**
     * @param authResult
     * @return
     */
    public static ApiResult authFailed(AuthResult authResult) {
        return AUTH_FAIL.get(authResult);
    }
}
