/**
 * OperResult.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-24 下午4:25:21
 */
package com.wiselink.result;


/**
 * @author leo
 */
public class OperResult<T> {
    public ErrorCode error;
    public String reason;
    public T result;
    public int total;

    public OperResult(ErrorCode error, String reason) {
        this.error = error;
        this.reason = reason;
    }

    public OperResult(T result) {
        this(ErrorCode.Success, "");
        this.result = result;
    }

    public OperResult(T result, int total) {
        this(result);
        this.total = total;
    }

    public ErrorCode getError() {
        return error;
    }

    public OperResult<T> setError(ErrorCode error) {
        this.error = error;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public OperResult<T> setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public T getResult() {
        return result;
    }

    public OperResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public OperResult<T> setTotal(int total) {
        this.total = total;
        return this;
    }
}
