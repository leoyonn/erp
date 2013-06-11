/**
 * BaseController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-26 下午9:19:14
 */
package com.wiselink.controllers;

import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;

/**
 * 
 * @author leo
 */
public abstract class BaseController {
    protected String apiResult(ApiResult res) {
        return "@json:" + res.toJson();
    }
    
    protected String successResult(String result) {
        return "@json:" + new ApiResult(ApiStatus.SUCCESS, result);    
    }

    protected String failResult(ApiStatus status, String result) {
        return "@json:" + new ApiResult(status, result);    
    }

    protected String failResult(ApiStatus status) {
        return "@json:" + new ApiResult(status, "");    
    }
}
