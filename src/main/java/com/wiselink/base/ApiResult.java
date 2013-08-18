/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:29:50
 */
package com.wiselink.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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

    private String detail = "";
    
    private int total = 0;

    public ApiResult(ApiStatus status) {
        this.status = status;
    }

    public ApiResult(ApiStatus status, String result) {
        this.status = status;
        this.detail = result;
    }

    public ApiResult(ApiStatus status, String result, int total) {
        this.status = status;
        this.detail = result;
        this.total = total;
    }

    public String toJson() {
        if (StringUtils.isEmpty(detail)) {
            detail = "\"\"";
        } else if (!detail.startsWith("[") && !detail.startsWith("{") && !detail.startsWith("\"")) {
            detail = "\"" + detail + "\"";
        }
        return "{\"status\":" + status.toJson() + ",\"detail\":" + detail + ",\"total\":" + total + "}";
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
        return detail;
    }

    public void setResult(String result) {
        this.detail = result;
    }

    /**
     * @param authResult
     * @return
     */
    public static ApiResult authFailed(AuthStatus authResult) {
        return AUTH_FAIL.get(authResult);
    }
}
