/**
 * AuthResult.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-29 上午10:08:08
 */
package com.wiselink.result;


/**
 * @author leo
 */
public class Auth {
    public static final Auth InvalidUser = new Auth(ErrorCode.InvalidUser);
    public static final Auth WrongPassword = new Auth(ErrorCode.WrongPassword);

    public ErrorCode error;
    public String id;
    public String corpId;
    public String deptId;
    public String account;
    public String password;
    public String passToken;

    public Auth() {}
    
    public Auth(ErrorCode error) {
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }

    public Auth setError(ErrorCode error) {
        this.error = error;
        return this;
    }

    public String getId() {
        return id;
    }

    public Auth setId(String id) {
        this.id = id;
        return this;
    }

    public String getCorpId() {
        return corpId;
    }

    public Auth setCorpId(String corpId) {
        this.corpId = corpId;
        return this;
    }

    public String getDeptId() {
        return deptId;
    }

    public Auth setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Auth setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Auth setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPassToken() {
        return passToken;
    }

    public Auth setPassToken(String passToken) {
        this.passToken = passToken;
        return this;
    }
}
