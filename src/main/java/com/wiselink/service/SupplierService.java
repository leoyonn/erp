/**
 * SupplierService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-20 下午7:55:57
 */
package com.wiselink.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.dao.SupplierDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.supplier.Supplier;

/**
 * @author leo
 */
@Service
public class SupplierService {

    @Autowired
    private SupplierDAO supplierDao;

    /**
     * 添加一个新的供货商
     * 
     * @return
     * @throws ServiceException
     */
    public boolean add(Supplier supplier) throws ServiceException {
        supplier.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            return supplierDao.add(supplier);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * @param supplier
     * @return
     * @throws ServiceException
     */
    public boolean update(Supplier supplier) throws ServiceException {
        try {
            return supplierDao.update(supplier);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }
    
    public int clear() throws ServiceException {
        try {
            return supplierDao.clear();
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }
    public boolean delete(String id) throws ServiceException {
        try {
            return supplierDao.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public boolean deleteByName(String name) throws ServiceException {
        try {
            return supplierDao.deleteByName(name);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public Supplier findById(String id) throws ServiceException {
        try {
            return supplierDao.findById(id);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Supplier> findByName(String name) throws ServiceException {
        try {
            return supplierDao.findByName(name);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Supplier> find(String type, String mode, String status) throws ServiceException {
        try {
            return supplierDao.find(type, mode, status);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Supplier> all() throws ServiceException {
        try {
            return supplierDao.all();
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }
}
