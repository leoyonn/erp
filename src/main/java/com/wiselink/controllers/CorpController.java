/**
 * CorpController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午12:06:58
 */
package com.wiselink.controllers;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.Trimmed;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.DeptType;
import com.wiselink.model.org.OrgType;
import com.wiselink.service.CorpService;

/**
 * 公司/机构管理相关的接口
 * @author leo
 */
@Path("corp")
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
    public String newCorp(Invocation inv, @Trimmed @Param("id") String id, @Param("name") String name,
            @Param("desc") String desc, @Param("address") String address, @Param("tel") String tel,
            @Param("contact") String contact) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adding corp: {}|{}|{}|{}|{}|{}", new Object[]{id, name, desc, address, tel, contact});
        }
        // TODO: id 检测，添加id util
        try {
            if (corpService.newCorp(id, name, desc, address, tel, contact)) {
                LOGGER.debug("adding corp: {} success.", id);
                return successResult(new Corp(id, name, desc, address, tel, contact).toJson());
            }
        } catch (ServiceException ex) {
            LOGGER.error("adding corp " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
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
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * @param ids
     * @return
     */
    @Get("list")
    public String getCorps(@Param("ids") Collection<String> ids) {
        try {
            List<Corp> corps = corpService.getCorps(ids);
            LOGGER.debug("get corps: {}.", corps);
            if (corps != null && corps.size() > 0) {
                return successResult(corps);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get corp " + ids + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }


    /**
     * 创建一个新的部门，name/id一旦设置不可更改
     * @param inv
     * @param id
     * @param name
     * @param deptType
     * @param corpId
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new/dept")
    public String newDept(Invocation inv, @Trimmed @Param("id") String id, @Param("name") String name,
            @Param("deptType") String deptType, @Param("corpId") String corpId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adding corp: {}|{}|{}|{}|{}|{}", new Object[]{id, name, deptType, corpId});
        }
        // TODO 检查id合法性
        try {
            if (corpService.newDept(id, name, deptType, corpId)) {
                LOGGER.debug("adding dept: {} success.", id);
                return successResult(new Dept(id, name, deptType, corpId).toJson());
            }
        } catch (ServiceException ex) {
            LOGGER.error("adding corp " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }
    
    /**
     * 获取一个部门信息
     * @param id
     * @return
     * @throws SQLException, DataAccessException
     */
    @Get("dept/1")
    public String getDept(String id) {
        try {
            Dept dept = corpService.getDept(id);
            LOGGER.debug("get dept: {}.", dept);
            if (dept != null) {
                return successResult(dept);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get dept " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * list all depts in :ids  
     * 
     * @param from
     * @param num
     * @return
     */
    @Get("dept/list")
    public String getDepts(Collection<String> ids) {
        try {
            List<Dept> depts = corpService.getDepts(ids);
            LOGGER.debug("get depts: {}.", depts);
            if (depts != null && depts.size() > 0) {
                return successResult(depts);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get depts " + ids + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }
}
