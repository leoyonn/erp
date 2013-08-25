/**
 * CorpController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午12:06:58
 */
package com.wiselink.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.OrgType;
import com.wiselink.result.ErrorCode;
import com.wiselink.service.CorpService;

/**
 * 公司/机构管理相关的接口
 * @author leo
 */
@Path("corp")
@LoginRequired
public class CorpController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpController.class);
    @Autowired
    private CorpService corpService;

    /**
     * 获取所有的组织结构类型
     * @return
     */
    @Get("orgtypes")
    public String orgTypes() {
        LOGGER.debug("All types are: {}", OrgType.allAsJson());
        return successResult(OrgType.allAsJson());
    }

    /**
     * 创建一个新的公司，不能与已有公司重复。name/id一旦设置不可更改
     * @param inv
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newCorp(Invocation inv, @Param("param") String param) {
        // TODO creator verification
        Corp corp = (Corp) new Corp().fromJson(param);
        LOGGER.info("new corp: {}...", corp);
        return apiResult(corpService.newCorp(corp));
    }

    /**
     * 更新一个公司信息
     * @param inv
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up")
    public String updateCorp(Invocation inv, @Param("param") String param) {
        Corp corp = (Corp) new Corp().fromJson(param);
        LOGGER.info("update corp: {}...", corp);
        return apiResult(corpService.updateCorp(corp));
    }

    @Get("{id:[0-9]+}")
    public String getCorp(@Param("id") String id) {
        return apiResult(corpService.getCorp(id));
    }

    @SuppressWarnings("@Post")
    @Get("del")
    public String deleteCorp(@Param("param") String param) {
        String id = JSONObject.fromObject(param).optString("id", "");
        if (StringUtils.isEmpty(id)) {
            return failResult(ErrorCode.InvalidParam, "参数中Id为空");
        }
        return apiResult(corpService.deleteCorp(id));
    }
    
    /**
     * 获取所有的公司
     * @return
     */
    @Get("all")
    public String allCorps() {
        return apiResult(corpService.allCorps());
    }
}
