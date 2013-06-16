/**
 * DataException.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午6:12:28
 */
package com.wiselink.exception;

/**
 * 数据读写相关的异常
 * 
 * @author leo
 */
public class DataException extends Exception {
    private static final long serialVersionUID = 1L;

    public DataException() {
        super();
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}
