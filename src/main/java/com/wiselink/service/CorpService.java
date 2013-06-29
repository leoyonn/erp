/**
 * CorpService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:27:12
 */
package com.wiselink.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.dao.CorpDAO;
import com.wiselink.dao.DeptDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.Org;
import com.wiselink.utils.IdUtils;

/**
 * 组织结构相关的服务
 * @author leo
 */
@Service
public class CorpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpService.class);

    @Autowired
    private CorpDAO corpDao;
    
    @Autowired
    private DeptDAO deptDao;

    /**
     * 新建一个公司
     * 
     * @param id
     * @param name
     * @param desc
     * @param address
     * @param tel
     * @param contact
     * @return
     * @throws ServiceException
     */
    public boolean newCorp(String id, String name, String desc, String address, String tel, String contact)
            throws ServiceException {
        try {
            return corpDao.addCorp(id, name, desc, address, tel, contact);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 查询一个公司
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    public Corp getCorp(String id) throws ServiceException {
        try {
            return corpDao.find(id);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * list all corps in :ids
     * 
     * @param from
     * @param num
     * @return
     * @throws ServiceException
     */
    public List<Corp> getCorps(Collection<String> ids) throws ServiceException {
        try {
            return corpDao.list(ids);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }


    /**
     * 添加一个新的部门
     * @param id
     * @param name
     * @param corpId
     * @param deptType
     * @return
     * @throws ServiceException 
     */
    public boolean newDept(String id,String name,String deptType,String corpId) throws ServiceException {
        try {
            return deptDao.addDept(id, name, deptType, corpId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取一个部门信息
     * @param id
     * @return
     * @throws ServiceException 
     */
    public Dept getDept(String id) throws ServiceException {
        try {
            return deptDao.find(id);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * list all depts in :ids  
     * 
     * @param from
     * @param num
     * @return
     * @throws ServiceException 
     */
    public List<Dept> getDepts(Collection<String> ids) throws ServiceException {
        try {
            return deptDao.list(ids);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * @param scopeIds
     * @return
     * @throws ServiceException 
     */
    public List<Org> getOrgs(List<String> scopeIds) throws ServiceException {
        if (scopeIds == null || scopeIds.size() == 0) {
            return Collections.emptyList();
        }
        List<String> corpIds = new ArrayList<String>();
        List<String> deptIds = new ArrayList<String>();
        for (String id: scopeIds) {
            if (IdUtils.isCorpIdLegal(id)) {
                corpIds.add(id);
            } else if (IdUtils.isDeptIdLegal(id)) {
                deptIds.add(id);
            } else {
                LOGGER.warn("bad id:" + id);
            }
        }
        List<Corp> corps = Collections.emptyList();
        List<Dept> depts = Collections.emptyList();
        try {
            corps = corpDao.list(corpIds);
            depts = deptDao.list(deptIds);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        List<Org> orgs = new ArrayList<Org>(corps.size() + depts.size());
        orgs.addAll(corps);
        orgs.addAll(depts);
        return orgs;
    }

}
