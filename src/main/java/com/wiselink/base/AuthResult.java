/**
 * AuthResult.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-29 上午10:08:08
 */
package com.wiselink.base;


/**
 * @author leo
 */
public class AuthResult {
    public static final AuthResult INVALID_USER = new AuthResult(AuthStatus.INVALID_USER);
    public static final AuthResult WRONG_PASSWORD = new AuthResult(AuthStatus.WRONG_PASSWORD);
    public AuthStatus stat;
    public String passToken;
    public String userId;

    public AuthResult(AuthStatus status) {
        this.stat = status;
    }

    public AuthResult(AuthStatus status, String userId) {
        this.stat = status;
        this.userId = userId;
    }

    public AuthResult(AuthStatus status, String userId, String passToken) {
        this.stat = status;
        this.userId = userId;
        this.passToken = passToken;
    }
}
