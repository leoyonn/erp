/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:29:50
 */
package com.wiselink.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leo
 */
public class ApiResult {
    private static final Map<AuthStatus, ApiResult> AUTH_FAIL = new HashMap<AuthStatus, ApiResult>();
    static {
        for (AuthStatus authResult: AuthStatus.values()) {
            if (authResult != AuthStatus.SUCCESS) {
                AUTH_FAIL.put(authResult, new ApiResult(ApiStatus.fromAuthResult(authResult), null));
            }
        }
    }

    private ApiStatus status;

    private String result = "";

    public ApiResult(ApiStatus status) {
        this.status = status;
    }

    public ApiResult(ApiStatus status, String result) {
        this.status = status;
       this.result = result;
    }

    public String toJson() {
        if (!result.startsWith("[") && !result.startsWith("{")) {
            result = "\"" + result + "\"";
        }
        return "{\"status\":" + status.toJson() + ",\"result\":" + result + "}";
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
    public static ApiResult authFailed(AuthStatus authResult) {
        return AUTH_FAIL.get(authResult);
    }
}
