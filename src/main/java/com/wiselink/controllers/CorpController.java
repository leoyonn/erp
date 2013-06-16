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
import com.wiselink.model.base.CorpType;
import com.wiselink.model.org.Corp;

/**
 * 公司/机构管理相关的接口
 * @author leo
 */
@Path("corp")
public class CorpController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private CorpDAO corpDao;

    /**
     * get type of #code. if #code < 0, returns all types.
     * @param inv
     * @param code
     * @return
     */
    @Get("type")
    public String type(Invocation inv, @Param("code") int code) {
        LOGGER.debug("listing corp type of: {}", code);
        if (code >= 0) {
            CorpType type = CorpType.fromCode(code);
            LOGGER.debug("Type is: {}", type);
            if (type == null) {
                return failResult(ApiStatus.INVALID_PARAMETER, "parameter:<code> is invalid");
            }
            return successResult(type.json);
        }
        LOGGER.debug("AllType is: {}", CorpType.allAsJson());
        return successResult(CorpType.allAsJson());
    }

    /**
     * get type of #name. if #name == 'all', returns all types.
     * @param inv
     * @param name
     * @return
     */
    @Get("type/byname")
    public String type(Invocation inv, @Trimmed @Param("name") String name) {
        LOGGER.debug("listing corp type of: {}", name);
        if (name.equalsIgnoreCase("ALL")) {
            LOGGER.debug("AllType is: {}", CorpType.allAsJson());
            return successResult(CorpType.allAsJson());
        }
        CorpType type = CorpType.valueOf(name.toUpperCase());
        LOGGER.debug("corp type is: {}", type);
        if (type != null) {
            return successResult(type.json);
        }
        return failResult(ApiStatus.INVALID_PARAMETER, "parameter:<name> is invalid");
    }

    /**
     * create a corp to database.
     * @param inv
     * @param user
     * @param password
     * @return
     */
    @Post("add")
    public String addCorp(Invocation inv, @Trimmed @Param("id") String id, @Param("name") String name,
            @Param("desc") String desc,
            @Param("address") String address, @Param("tel") String tel, @Param("contact") String contact) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adding corp: {}|{}|{}|{}|{}|{}", new Object[]{id, name, desc, address, tel, contact});
        }
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
}
