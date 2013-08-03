/**
 * SupplierController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午7:47:11
 */
package com.wiselink.controllers;

import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiselink.base.ApiStatus;
import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.supplier.Supplier;
import com.wiselink.model.supplier.SupplierMode;
import com.wiselink.model.supplier.SupplierStatus;
import com.wiselink.model.supplier.SupplierType;
import com.wiselink.service.SupplierService;

/**
 * @author leo
 */
@Path("supplier")
@LoginRequired
public class SupplierController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService service;

    /**
     * 获取所有的供货商类型
     * 
     * @return
     */
    @Get("types")
    public String types() {
        LOGGER.debug("All types are: {}", SupplierType.all());
        return successResult(SupplierType.all());
    }

    /**
     * 获取所有的购销形式
     */
    @Get("modes")
    public String modes() {
        LOGGER.debug("All modes are: {}", SupplierMode.all());
        return successResult(SupplierMode.all());
    }

    /**
     * 获取所有的工作上状态
     * 
     * @return
     */
    @Get("stats")
    public String stats() {
        LOGGER.debug("All statuses are: {}", SupplierStatus.all());
        return successResult(SupplierStatus.all());
    }

    /**
     * 添加一个新的供货商
     * 
     * @param supplier
     * @return
     */
    @SuppressWarnings("@Post")
    @Get("new")
    public String add(Invocation inv, @Param("json") String json) {
        String creatorId = getUserIdFromCookie(inv);
        Supplier supplier = ((Supplier) new Supplier().fromJson(json)).setCreatorId(creatorId);
        try {
            if (service.add(supplier)) {
                LOGGER.debug("add supplier: {} success.", supplier);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("add supplier " + json + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_INSERT_FAILED);
    }

    @SuppressWarnings("@Post")
    @Get("up")
    public String update(Invocation inv, @Param("json") String json) {
        Supplier supplier = ((Supplier) new Supplier().fromJson(json));
        try {
            if (service.update(supplier)) {
                LOGGER.debug("update supplier: {} success.", supplier);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("update supplier " + json + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_UPDATE_FAILED);
    }

    @SuppressWarnings("@Post")
    @Get("del")
    public String delete(Invocation inv, @Param("id") String id) {
        try {
            if (service.delete(id)) {
                LOGGER.debug("delete supplier: {} success.", id);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("delete supplier " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_DELETE_FAILED);
    }

    @SuppressWarnings("@Post")
    @Get("del/byname")
    public String deleteByName(Invocation inv, @Param("name") String name) {
        try {
            if (service.deleteByName(name)) {
                LOGGER.debug("delete by name supplier: {} success.", name);
                return successResult();
            }
        } catch (ServiceException ex) {
            LOGGER.error("delete supplier by name " + name + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_DELETE_FAILED);
    }

    @Get("find/byid")
    public String findById(Invocation inv, @Param("id") String id) {
        try {
            Supplier supplier = service.findById(id);
            if (supplier != null) {
                LOGGER.debug("find by id got: {} success.", supplier);
                return successResult(supplier);
            } else {
                return failResult(ApiStatus.DATA_EMPTY);
            }
        } catch (ServiceException ex) {
            LOGGER.error("find by id " + id + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    @Get("find/byname")
    public String findByName(Invocation inv, @Param("name") String name) {
        try {
            List<Supplier> suppliers = service.findByName(name);
            if (suppliers != null && suppliers.size() > 0) {
                LOGGER.debug("find by name got: {} success.", suppliers);
                return successResult(suppliers);
            } else {
                return failResult(ApiStatus.DATA_EMPTY);
            }
        } catch (ServiceException ex) {
            LOGGER.error("find by name " + name + " got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    @Get("find")
    public String find(Invocation inv, @Param("type") String type, @Param("mode") String mode, @Param("status") String status) {
        try {
            List<Supplier> suppliers = service.find(type, mode, status);
            if (suppliers != null && suppliers.size() > 0) {
                LOGGER.debug("find suppliers got: {} success.", suppliers);
                return successResult(suppliers);
            } else {
                return failResult(ApiStatus.DATA_EMPTY);
            }
        } catch (ServiceException ex) {
            LOGGER.error("find by <type:" + type + ", mode:" + mode + ", status:" + status + "> got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }

    @Get("all")
    public String all() {
        try {
            List<Supplier> suppliers = service.all();
            if (suppliers != null && suppliers.size() > 0) {
                LOGGER.debug("find suppliers got: {} success.", suppliers);
                return successResult(suppliers);
            } else {
                return failResult(ApiStatus.DATA_EMPTY);
            }
        } catch (ServiceException ex) {
            LOGGER.error("query all suppliers got exception:", ex);
        }
        return failResult(ApiStatus.DATA_QUERY_FAILED);
    }
}
