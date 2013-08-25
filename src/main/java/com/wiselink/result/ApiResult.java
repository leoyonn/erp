/**
 * ApiResult.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-4 下午11:29:50
 */
package com.wiselink.result;

import org.apache.commons.lang.StringUtils;

import com.wiselink.utils.Utils;

/**
 * @author leo
 */
public class ApiResult {
    private ErrorCode error = ErrorCode.Success;
    private String reason = "";
    private String detail = "";
    private int total = 0;

    public ApiResult(ErrorCode error, String reason, String detail, int total) {
        this.error = error;
        this.reason = reason;
        this.detail = detail;
        this.total = total;
    }
    public ApiResult(ErrorCode error, String reason) {
        this(error, reason, "", 0);
    }

    public ApiResult(ErrorCode error) {
        this(error, "", "", 0);
    }

    public ApiResult(String detail) {
        this(ErrorCode.Success, "", detail, 0);
    }

    public ApiResult(String detail, int total) {
        this(ErrorCode.Success, "", detail, total);
    }

    public String toJson() {
        String detail_ = Utils.fixJsonValueQuota(detail);
        String reason_ = Utils.fixJsonValueQuota(reason); 
        return "{\"status\":" + error.toJson() + ",\"reason\":" + reason_
                + ",\"detail\":" + detail_ + ",\"total\":" + total + "}";
    }

    public String toJsonApiResult() {
        return "@json:" + toJson();
    }

    public String toString() {
        return toJson();
    }

    public ErrorCode getError() {
        return error;
    }

    public ApiResult setError(ErrorCode error) {
        this.error = error;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public ApiResult setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public ApiResult setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
