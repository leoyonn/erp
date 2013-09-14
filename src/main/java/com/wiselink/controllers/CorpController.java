/**
 * CorpController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午12:06:58
 */
package com.wiselink.controllers;

import java.util.Arrays;

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
import com.wiselink.model.param.QueryListParam;
import com.wiselink.model.supplier.Supplier;
import com.wiselink.model.supplier.SupplierMode;
import com.wiselink.model.supplier.SupplierStatus;
import com.wiselink.model.supplier.SupplierType;
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
    public String newCorp(Invocation inv, @NotBlank @Param("param") String param) {
        JSONObject json = JSONObject.fromObject(param);
        OrgType type = OrgType.value(json.optInt("type", -1));
        switch (type) {
            case Corp1: // fall through
            case Corp2: // fall through
            case Corp3: {
                Corp corp = (Corp) new Corp().fromJson(param);
                corp.setType(type.cname);
                return apiResult(corpService.newCorp(corp));
            }
            case Supplier: {
                Supplier supplier = (Supplier)new Supplier().fromJson(param);
                supplier.setType(type.cname);
                String myCorpId = getCorpIdFromCookie(inv); 
                return apiResult(corpService.newSupplier(myCorpId, supplier));
            }
            default: return failResult(ErrorCode.InvalidParam, "无效的公司类型：type，请查看corp/orgtypes接口");
        }
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
        JSONObject json = JSONObject.fromObject(param);
        OrgType type = OrgType.value(json.optInt("type", -1));
        switch (type) {
            case Corp1: // fall through
            case Corp2: // fall through
            case Corp3: {
                Corp corp = (Corp) new Corp().fromJson(param);
                corp.setType(type.cname);
                return apiResult(corpService.updateCorp(corp));
            }
            case Supplier: {
                Supplier supplier = (Supplier)new Supplier().fromJson(param);
                supplier.setType(type.cname);
                return apiResult(corpService.updateSupplier(supplier));
            }
            default: return failResult(ErrorCode.InvalidParam, "无效的公司类型：type，请查看corp/orgtypes接口");
        }
    }

    @Get("{id:[0-9]+}")
    public String getCorp(@Param("id") String id) {
        return apiResult(corpService.getCorp(id));
    }

    @SuppressWarnings("@Post")
    @Get("del")
    public String deleteCorp(@Param("param") String param) {
        JSONObject json = JSONObject.fromObject(param);
        String ids =json.optString("ids", "");
        LOGGER.info("delete corps: {} ", ids);
        if (StringUtils.isBlank(ids)) {
            return failResult(ErrorCode.BlankParam, "输入ids列表为空");
        }
        return apiResult(corpService.deleteCorps(Arrays.asList(ids.split(","))));
    }

    /**
     * 查询满足要求的公司
     * @return
     */
    @Get("query")
    public String queryCorps(Invocation inv, @NotBlank @Param("param") String param) {
        QueryListParam listParam = (QueryListParam) new QueryListParam().fromJson(param);
        String myCorpId = getCorpIdFromCookie(inv);
        return apiResult(corpService.queryCorps(listParam, myCorpId));
    }
    
    /**
     * just for test
     * @return
     */
    @Get("all")
    public String allCorps() {
        return apiResult(corpService.all());
    }
    
    @Get("supplier/modes")
    public String supplierModes() {
        return successResult(SupplierMode.all());
    }
    
    @Get("supplier/statuses")
    public String supplierStatuses() {
        return successResult(SupplierStatus.all());
    }
    
    @Get("supplier/types")
    public String supplierTypes() {
        return successResult(SupplierType.all());
    }
}
