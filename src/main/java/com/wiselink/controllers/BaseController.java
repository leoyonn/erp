/**
 * BaseController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-26 下午9:19:14
 */
package com.wiselink.controllers;

import java.util.Collection;

import net.sf.json.JSONArray;

import com.wiselink.base.ApiResult;
import com.wiselink.base.ApiStatus;
import com.wiselink.base.jsonable.Jsonable;

/**
 * 
 * @author leo
 */
public abstract class BaseController {
    protected String apiResult(ApiResult res) {
        return "@json:" + res.toJson();
    }

    protected String successResult() {
        return "@json:" + new ApiResult(ApiStatus.SUCCESS);    
    }

    protected String successResult(String result) {
        return "@json:" + new ApiResult(ApiStatus.SUCCESS, result);    
    }

    protected String successResult(Jsonable result) {
        return "@json:" + new ApiResult(ApiStatus.SUCCESS, result.toJson());    
    }

    protected String successResult(Collection<? extends Jsonable> all) {
        if (all == null || all.size() == 0) {
            return failResult(ApiStatus.DATA_EMPTY);
        }
        JSONArray arr = new JSONArray();
        for (Jsonable j: all) {
            arr.add(j.toJson());
        }
        return "@json:" + new ApiResult(ApiStatus.SUCCESS, arr.toString());
    }

    protected String failResult(ApiStatus status, String result) {
        return "@json:" + new ApiResult(status, result);    
    }

    protected String failResult(ApiStatus status) {
        return "@json:" + new ApiResult(status, "");    
    }
}
