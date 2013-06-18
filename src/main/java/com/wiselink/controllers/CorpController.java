/**
 * CorpController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午12:06:58
 */
package com.wiselink.controllers;

import java.sql.SQLException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.Trimmed;
import com.wiselink.dao.CorpDAO;
import com.wiselink.dao.DeptDAO;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.DeptType;
import com.wiselink.model.org.OrgType;

/**
 * 公司/机构管理相关的接口
 * @author leo
 */
@Path("corp")
public class CorpController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private CorpDAO corpDao;

    @Autowired
    private DeptDAO deptDao;

    /**
     * 获取所有的组织结构类型
     * @return
     */
    @Get("types")
    public String orgTypes() {
        LOGGER.debug("All types are: {}", OrgType.allAsJson());
        return successResult(OrgType.allAsJson());
    }

    /**
     * 获取所有的部门类型
     * @return
     */
    @Get("types/dept")
    public String deptTypes() {
        LOGGER.debug("All dept types are: {}", DeptType.allAsJson());
        return successResult(DeptType.allAsJson());
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
    @Post("new")
    public String newCorp(Invocation inv, @Trimmed @Param("id") String id, @Param("name") String name,
            @Param("desc") String desc, @Param("address") String address, @Param("tel") String tel,
            @Param("contact") String contact) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adding corp: {}|{}|{}|{}|{}|{}", new Object[]{id, name, desc, address, tel, contact});
        }
        // TODO: id 检测，添加id util
        try {
            if (corpDao.addCorp(id, name, desc, address, tel, contact)) {
                LOGGER.debug("adding corp: {} success.", id);
                return successResult(new Corp(id, name, desc, address, tel, contact).toJson());
            }
        } catch (SQLException ex) {
            LOGGER.error("adding corp " + id + " got exception:", ex);
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
    @Post("new/dept")
    public String newDept(Invocation inv, @Trimmed @Param("id") String id, @Param("name") String name,
            @Param("deptType") String deptType, @Param("corpId") String corpId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adding corp: {}|{}|{}|{}|{}|{}", new Object[]{id, name, deptType, corpId});
        }
        // TODO 检查id合法性
        try {
            if (deptDao.addDept(id, name, deptType, corpId)) {
                LOGGER.debug("adding dept: {} success.", id);
                return successResult(new Dept(id, name, deptType, corpId).toJson());
            }
        } catch (SQLException ex) {
            LOGGER.error("adding corp " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }
}
