/**
 * DeptController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-29 下午8:47:08
 */
package com.wiselink.controllers;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.DeptType;
import com.wiselink.model.org.OrgType;
import com.wiselink.model.param.QueryListParam;
import com.wiselink.service.CorpService;

/**
 * 
 * @author leo
 */
@Path("dept")
@LoginRequired
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
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newDept(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("new dept with param: {}...", param);
        Dept dept = (Dept) new Dept().fromJson(param);
        DeptType type = DeptType.valueOf(dept.type);
        if (type == null) {
            return failResult(ApiStatus.INVALID_PARAMETER, "参数type非法");
        }
        dept.type = DeptType.valueOf(dept.type).cname;
        LOGGER.info("new dept: {}...", dept);
        // TODO 检查id合法性
        try {
            dept = corpService.newDept(dept);
            LOGGER.debug("adding dept: {} success.", dept);
            return successResult(dept);
        } catch (ServiceException ex) {
            LOGGER.error("adding corp " + dept + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    /**
     * 更新一个部门信息
     * 
     * @param inv
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("up")
    public String updateDept(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("update dept with param: {}...", param);
        Dept dept = (Dept) new Dept().fromJson(param);
        LOGGER.info("update dept: {}", dept);
        // TODO 检查id合法性
        try {
            dept = corpService.updateDept(dept);
            LOGGER.debug("updating dept: {} success.", dept);
            return successResult();
        } catch (ServiceException ex) {
            LOGGER.error("adding corp " + dept + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_UPDATE_FAILED);
    }
    
    /**
     * 删除一个部门信息
     * 
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("del")
    public String deleteDept(@NotBlank @Param("param") String param) {
        LOGGER.info("delete dept: {}...", param);
        String id = JSONObject.fromObject(param).optString("id", "");
        if (StringUtils.isBlank(id)) {
            return failResult(ApiStatus.INVALID_PARAMETER, "参数中ID不可为空");
        }
        // TODO permission check
        try {
            boolean ok = corpService.deleteDept(id);
            if (ok) {
                return successResult("删除部门成功");
            }
        } catch (ServiceException ex) {
            LOGGER.error("delete dept failed:" + id, ex);
            return failResult(ApiStatus.DATA_DELETE_FAILED);
        }
        return failResult(ApiStatus.DATA_DELETE_FAILED);
    }

    /**
     * 获取一个部门信息
     * 
     * @param param
     * @return
     * @throws SQLException, DataAccessException
     */
    @Get("1")
    public String getDept(@NotBlank @Param("param") String param) {
        LOGGER.info("get dept: {}...", param);
        String id = JSONObject.fromObject(param).optString("id", "");
        if (StringUtils.isBlank(id)) {
            return failResult(ApiStatus.INVALID_PARAMETER, "参数中ID不可为空");
        }
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
    @Get("query")
    public String queryDepts(@NotBlank @Param("param") String param) {
        LOGGER.info("list depts: {}", param);
        QueryListParam listParam = (QueryListParam) new QueryListParam().fromJson(param);
        LOGGER.info("list depts with list param: {}", listParam);
        try {
            List<Dept> depts = corpService.queryDepts(listParam);
            int total = corpService.countDepts(listParam);
            LOGGER.debug("got depts: {}.", depts);
            if (depts != null && depts.size() > 0) {
                return successResult(depts, total);
            }
        } catch (ServiceException ex) {
            LOGGER.error("get depts " + listParam + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    /**
     * 获取所有的部门列表
     * @return
     */
    @Get("all/1corp")
    public String allDepts(@NotBlank @Param("param") String param) {
        LOGGER.info("all dept with param: {}...", param);
        String corpId = JSONObject.fromObject(param).optString("corpId", "");
        if (StringUtils.isBlank(corpId)) {
            return failResult(ApiStatus.INVALID_PARAMETER, "参数中corpId为空");
        }
        try {
            List<Dept> depts = corpService.allDepts(corpId);
            LOGGER.debug("get depts: {}.", depts);
            return successResult(depts);
        } catch (ServiceException ex) {
            LOGGER.error("get all depts of " + corpId + " got exception:", ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
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
            return successResult(depts);
        } catch (ServiceException ex) {
            LOGGER.error("get all depts got exception:", ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
    }
}
