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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.controllers.annotations.Trimmed;
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
    public String newCorp(Invocation inv, @Trimmed @Param("id") String id, @Param("type") String type,
            @Param("name") String name, @Param("desc") String desc, @Param("address") String address,
            @Param("tel") String tel, @Param("contact") String contact) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adding corp: {}|{}|{}|{}|{}|{}|{}", new Object[]{id, type, name, desc, address, tel, contact});
        }
        // TODO: id 检测，添加id util
        try {
            if (corpService.newCorp(id, type, name, desc, address, tel, contact)) {
                LOGGER.debug("adding corp: {} success.", id);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("adding corp " + id + " got exception:", ex);
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
    public String updateCorp(Invocation inv, @Trimmed @Param("id") String id, @Param("type") String type,
            @Param("name") String name, @Param("desc") String desc, @Param("address") String address,
            @Param("tel") String tel, @Param("contact") String contact) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updating corp: {}|{}|{}|{}|{}|{}|{}", new Object[]{id, type, name, desc, address, tel, contact});
        }
        // TODO: id 检测，添加id util
        try {
            if (corpService.updateCorp(id, type, name, desc, address, tel, contact)) {
                LOGGER.debug("updating corp: {} success.", id);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("updating corp " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_UPDATE_FAILED);
    }

    /**
     * @param id
     * @return
     */
    @Get("1")
    public String getCorp(@Param("id") String id) {
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
    public String getCorps(@Param("ids") String ids) {
        // TODO @NotBlank
        if (StringUtils.isEmpty(ids)) {
            return failResult(ApiStatus.INVALID_PARAMETER);
        }
        List<String> idlist = Arrays.asList(ids.split(","));
        LOGGER.debug("getting corps:{}", idlist);
        try {
            List<Corp> corps = corpService.getCorps(idlist);
            LOGGER.debug("get corps: {}.", corps);
            if (corps != null && corps.size() > 0) {
                return successResult(corps);
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
