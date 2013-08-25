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
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.DeptType;
import com.wiselink.model.org.OrgType;
import com.wiselink.model.param.QueryListParam;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
import com.wiselink.service.DeptService;
import com.wiselink.service.UserService;

/**
 * 
 * @author leo
 */
@Path("dept")
@LoginRequired
public class DeptController  extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;

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
     * 
     * @param inv
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String newDept(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("new dept with param: {}...", param);
        // TODO: creatorId
        Dept dept = (Dept) new Dept().fromJson(param);
        if (StringUtils.isBlank(dept.getName()) || StringUtils.isBlank(dept.corpId)) {
            return failResult(ErrorCode.InvalidParam, "参数值为空：(name:" + dept.getName() + ", corpId:" + dept.getCorpId());
        }
        DeptType type = DeptType.valueOf(dept.deptType);
        if (type != null) {
            dept.deptType = type.cname;
        }
        OperResult<Dept> r = deptService.newDept(dept);
        return apiResult(r);
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
        if (StringUtils.isBlank(dept.getName()) || StringUtils.isBlank(dept.corpId) || StringUtils.isBlank(dept.id)) {
            return failResult(ErrorCode.InvalidParam, "参数值为空：(id:" + dept.id
                    + ",name:" + dept.getName() + ", corpId:" + dept.getCorpId());
        }
        DeptType type = DeptType.valueOf(dept.deptType);
        if (type != null) {
            dept.deptType = type.cname;
        }
        OperResult<Dept> r = deptService.updateDept(dept);
        return apiResult(r);
    }

    /**
     * 删除一个部门信息
     * 
     * @param param
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("del")
    public String deleteDept(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("delete dept: {}...", param);
        String ids = JSONObject.fromObject(param).optString("ids", "");
        if (StringUtils.isBlank(ids)) {
            return failResult(ErrorCode.InvalidParam, "参数中ID不可为空");
        }
        // String user = getUserIdFromCookie(inv); // TODO creatorId?
        List<String> idList = Arrays.asList(ids.split(","));
        OperResult<Integer> cnt = userService.countUsersInDepts(idList);
        if (cnt.getError() != ErrorCode.Success) {
            return apiResult(cnt);
        } else if (cnt.result > 0) {
            return failResult(ErrorCode.DbDeleteFail, "尚有员工在此部门中，删除失败");
        }
        OperResult<String> r = deptService.deleteDept(idList);
        return apiResult(r);
    }

    /**
     * 获取一个部门信息
     * TODO: use deptId as url string
     * 
     * @param param
     * @return
     * @throws SQLException, DataAccessException
     */
    @Get("{id:[0-9]+}")
    public String getDept(@NotBlank @Param("id") String id) {
        LOGGER.info("get dept: {}...", id);
        return apiResult(deptService.getDept(id));
    }

    /**
     * 通过name参数模糊查询部门信息
     * 
     * @param param
     * @return
     */
    @Get("query")
    public String queryDepts(Invocation inv, @NotBlank @Param("param") String param) {
        LOGGER.info("list depts: {}", param);
        QueryListParam listParam = (QueryListParam) new QueryListParam().fromJson(param);
        String corpId = getCorpIdFromCookie(inv);
        LOGGER.info("list depts with list param: {}", listParam);
        OperResult<List<Dept>> r = deptService.queryDepts(listParam, corpId);
        return apiResult(r);
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
            return failResult(ErrorCode.InvalidParam, "参数中corpId为空");
        }
        OperResult<List<Dept>> r = deptService.allDepts(corpId);
        return apiResult(r);
    }

    /**
     * 获取所有的部门列表
     * 
     * @return
     */
    @Get("all")
    public String allDepts() {
        return apiResult(deptService.allDepts());
    }
}
