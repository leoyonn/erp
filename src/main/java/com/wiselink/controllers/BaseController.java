/**
 * BaseController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-5-26 下午9:19:14
 */
package com.wiselink.controllers;

import java.util.Collection;
import java.util.Collections;

import net.paoding.rose.web.Invocation;
import net.sf.json.JSONArray;

import com.wiselink.base.jsonable.Jsonable;
import com.wiselink.result.ApiResult;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
import com.wiselink.utils.CookieUtils;

/**
 * 
 * @author leo
 */
public abstract class BaseController {
    protected static String apiResult(ApiResult res) {
        return res.toJsonApiResult();
    }

    protected static <T> String apiResult(OperResult<T> r) {
        if (r.getError() != ErrorCode.Success) {
            return failResult(r.error, r.reason);
        }
        if (r.result instanceof String) {
            return successResult((String) r.result);
        } else if (r.result instanceof Jsonable) {
            return successResult((Jsonable) r.result);
        } else if (r.result instanceof Collection<?>) {
            @SuppressWarnings("unchecked")
            Collection<? extends Jsonable> res = (Collection<? extends Jsonable>) r.result;
            return successResult(res, r.total);
        } else {
            return successResult(r.result);
        }
    }

    protected static String successResult() {
        return new ApiResult(ErrorCode.Success).toJsonApiResult();
    }

    protected static String successResult(String result) {
        return new ApiResult(result).toJsonApiResult();
    }

    protected static <T> String successResult(T result) {
        return new ApiResult(result.toString()).toJsonApiResult();
    }

    protected static String successResult(Jsonable result) {
        return new ApiResult(result.toJson()).toJsonApiResult();
    }

    protected static String successResult(Collection<? extends Jsonable> all, int total) {
        if (all == null) {
            all = Collections.emptyList();
        }
        JSONArray arr = new JSONArray();
        for (Jsonable j: all) {
            arr.add(j.toJson());
        }
        return new ApiResult(arr.toString(), total).toJsonApiResult();
    }

    protected static String successResult(Collection<? extends Jsonable> all) {
        return successResult(all, all == null ? 0 : all.size());
    }

    protected static String failResult(ErrorCode error, String reason) {
        return new ApiResult(error, reason).toJsonApiResult();
    }

    protected static String failResult(ErrorCode error) {
        return new ApiResult(error).toJsonApiResult();
    }
    
    protected String getUserIdFromCookie(Invocation inv) {
        return CookieUtils.getUserId(inv);
    }

    protected String getCorpIdFromCookie(Invocation inv) {
        return CookieUtils.getCorpId(inv);
    }

    protected String getDeptIdFromCookie(Invocation inv) {
        return CookieUtils.getDeptId(inv);
    }
}
