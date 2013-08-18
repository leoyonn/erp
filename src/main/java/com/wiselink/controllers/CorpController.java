/**
 * CorpController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午12:06:58
 */
package com.wiselink.controllers;

import java.util.Arrays;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.OrgType;
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
     * @param id
     * @param name
     * @param desc
     * @param address
     * @param tel
     * @param contact
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newCorp(Invocation inv, @Param("param") String param) {
        Corp corp = (Corp) new Corp().fromJson(param);
        LOGGER.info("new corp: {}...", corp);
        // TODO: id 检测，添加id util
        try {
            if (corpService.newCorp(corp)) {
                LOGGER.debug("new corp: {} success.", corp);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("new corp " + corp + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * 更新一个公司信息
     * @param inv
     * @param id
     * @param type
     * @param name
     * @param desc
     * @param address
     * @param tel
     * @param contact
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up")
    public String updateCorp(Invocation inv, @Param("param") String param) {
        Corp corp = (Corp) new Corp().fromJson(param);
        LOGGER.info("new corp: {}...", corp);
        // TODO: id 检测，添加id util
        try {
            if (corpService.updateCorp(corp)) {
                LOGGER.debug("updating corp: {} success.", corp);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("updating corp " + corp + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_UPDATE_FAILED);
    }

    /**
     * @param id
     * @return
     */
    @Get("1")
    public String getCorp(@Param("param") String param) {
        String id = JSONObject.fromObject(param).optString("id", "");
        if (StringUtils.isEmpty(id)) {
            return failResult(ApiStatus.INVALID_PARAMETER, "参数中Id为空");
        }
        try {
            Corp corp = corpService.getCorp(id);
            LOGGER.debug("get corp: {}.", corp);
            if (corp != null) {
                return successResult(corp);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get corp " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    /**
     * 获取一批公司
     * @param ids
     * @return
     */
    @Get("list")
    public String getCorps(@Param("param") String param) {
        JSONArray idsJson = JSONObject.fromObject(param).optJSONArray("ids");
        if (idsJson == null || idsJson.size() == 0) {
            return failResult(ApiStatus.INVALID_PARAMETER, "参数中Id列表为空");
        }
        List<String> ids = Arrays.asList((String[])idsJson.toArray(new String[idsJson.size()]));
        LOGGER.debug("getting corps:{}", ids);
        try {
            List<Corp> corps = corpService.getCorps(ids);
            LOGGER.debug("get corps: {}.", corps);
            if (corps != null && corps.size() > 0) {
                return successResult(corps, corps.size());
            }
        } catch (ServiceException ex) {
            LOGGER.error("get corp " + ids + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }
    
    /**
     * 获取所有的公司
     * @return
     */
    @Get("all")
    public String allCorps() {
        try {
            List<Corp> corps = corpService.allCorps();
            LOGGER.debug("get corps: {}.", corps);
            if (corps != null && corps.size() > 0) {
                return successResult(corps);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get all corps got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }
}
