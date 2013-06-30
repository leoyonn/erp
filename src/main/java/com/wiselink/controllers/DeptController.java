/**
 * DeptController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-29 下午8:47:08
 */
package com.wiselink.controllers;

import java.sql.SQLException;
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
import com.wiselink.controllers.annotations.Trimmed;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.DeptType;
import com.wiselink.model.org.OrgType;
import com.wiselink.service.CorpService;

/**
 * 
 * @author leo
 */
@Path("dept")
public class DeptController  extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeptController.class);
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
     * 获取所有的部门类型
     * @return
     */
    @Get("types")
    public String deptTypes() {
        LOGGER.debug("All dept types are: {}", DeptType.allAsJson());
        return successResult(DeptType.allAsJson());
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
    @Get("new")
    public String newDept(Invocation inv, @Trimmed @Param("id") String id, @Param("name") String name,
            @Param("desc") String desc, @Param("deptType") String deptType, @Param("corpId") String corpId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adding corp: {}|{}|{}|{}", new Object[]{id, name, deptType, corpId});
        }
        // TODO 检查id合法性
        try {
            if (corpService.newDept(id, name, desc, deptType, corpId)) {
                LOGGER.debug("adding dept: {} success.", id);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("adding corp " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * 更新一个部门信息
     * @param inv
     * @param id
     * @param name
     * @param desc
     * @param deptType
     * @param corpId
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up")
    public String updateDept(Invocation inv, @Trimmed @Param("id") String id, @Param("name") String name,
            @Param("desc") String desc, @Param("deptType") String deptType, @Param("corpId") String corpId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updating corp: {}|{}|{}|{}", new Object[]{id, name, deptType, corpId});
        }
        // TODO 检查id合法性
        try {
            if (corpService.updateDept(id, name, desc, deptType, corpId)) {
                LOGGER.debug("updating dept: {} success.", id);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("adding corp " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_UPDATE_FAILED);
    }
    
    /**
     * 获取一个部门信息
     * @param id
     * @return
     * @throws SQLException, DataAccessException
     */
    @Get("1")
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
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    /**
     * list all depts in :ids  
     * 
     * @param from
     * @param num
     * @return
     */
    @Get("list")
    public String getDepts(@Param("ids") String ids) {
        // TODO @NotBlank
        if (StringUtils.isEmpty(ids)) {
            return failResult(ApiStatus.INVALID_PARAMETER);
        }
        List<String> idlist = Arrays.asList(ids.split(","));
        LOGGER.debug("getting depts:{}", idlist);
        try {
            List<Dept> depts = corpService.getDepts(idlist);
            LOGGER.debug("got depts: {}.", depts);
            if (depts != null && depts.size() > 0) {
                return successResult(depts);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get depts " + ids + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    /**
     * 获取所有的部门列表
     * @return
     */
    @Get("all/1corp")
    public String allDepts(@Param("corpId") String corpId) {
        try {
            List<Dept> depts = corpService.allDepts(corpId);
            LOGGER.debug("get depts: {}.", depts);
            if (depts != null && depts.size() > 0) {
                return successResult(depts);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get all depts of " + corpId + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    /**
     * 获取所有的部门列表
     * @return
     */
    @Get("all")
    public String allDepts() {
        try {
            List<Dept> depts = corpService.allDepts();
            LOGGER.debug("get depts: {}.", depts);
            if (depts != null && depts.size() > 0) {
                return successResult(depts);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get all depts got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }
}
