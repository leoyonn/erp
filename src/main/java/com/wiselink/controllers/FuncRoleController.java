/**
 * RoleController.java
 * [CopyRight]
 * @author leo leoyonn@gmail.com
 * @date 2013-6-11 下午3:29:21
 */
package com.wiselink.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.management.relation.Role;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.dao.FuncRoleDAO;
import com.wiselink.model.FuncRole;

/**
 * 用户角色相关的操作，例如创建角色、修改添加角色人物等
 * @author leo
 */
@Path("frole")
public class FuncRoleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncRoleController.class);
    @Autowired
    private FuncRoleDAO froleDao;

    /**
     * get role data by #code
     * 
     * @param code
     * @return
     */
    @Get("")
    public String getFrole(@Param("code")int code) {
        try {
            FuncRole frole = froleDao.find(code);
            LOGGER.debug("get func role: {}", frole);
            return successResult(frole.toJson());
        } catch (SQLException ex) {
            LOGGER.error("add func role " + code + " got exception!", ex);
            return failResult(ApiStatus.DATA_QUERY_FAILED);
        }
    }

    @Post("add")
    public String add(@Param("name") String name, @Param("desc") String desc) {
        LOGGER.debug("add func role of name: {}, desc: {}.", name, desc);
        if (froleDao.add(name, desc)) {
            FuncRole frole = null;
            try {
                frole = froleDao.findByName(name);
            } catch (SQLException ex) {
                LOGGER.error("add func role " + name + "|" + desc + " got exception!", ex);
            }
            if (frole != null) {
                LOGGER.debug("add func role success: {}.", frole);
                return successResult(frole.toJson());
            }
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED, "角色添加失败，请确认角色名不重复，或联系系统管理员");
    }
    
    /**
     * list all #num func-roles sorted by {@link Role#code} from #from 
     * @param from
     * @param num
     * @return
     */
    @Get("list")
    public String list(@Param("from") int from, @Param("num") int num) {
        List<FuncRole> roles = froleDao.list(from, num);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("list role from: {}, num: {}, got {}.", new Object[]{from, num, roles});
        }
        if (roles == null || roles.size() == 0) {
            return failResult(ApiStatus.DATA_EMPTY);
        }
        JSONArray array = new JSONArray();
        for (FuncRole role: roles) {
            array.add(role.toJson());
        }
        LOGGER.debug("list role returning{}.", array);
        return successResult(array.toString());
    }
}