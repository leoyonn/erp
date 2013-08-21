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

import org.apache.commons.lang.StringUtils;
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
import com.wiselink.model.param.QueryListParam;
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
     * @param corp
     * @throws ServiceException
     */
    public boolean newCorp(Corp corp) throws ServiceException {
        try {
            return corpDao.addCorp(corp.id, corp.type, corp.name, corp.desc, corp.address, corp.tel, corp.contact);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 修改一个公司的信息
     * @param corp
     * @return
     * @throws ServiceException
     */
    public boolean updateCorp(Corp corp) throws ServiceException {
        try {
            return corpDao.updateCorp(corp.id, corp.type, corp.name, corp.desc, corp.address, corp.tel, corp.contact);
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
     * 获取所有的公司
     * @return
     * @throws ServiceException
     */
    public List<Corp> allCorps() throws ServiceException {
        try {
            return corpDao.all();
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }


    /**
     * 添加一个新的部门
     * 
     * @param dept
     * @return
     * @throws ServiceException
     */
    public Dept newDept(Dept dept) throws ServiceException {
        try {
            String maxId = deptDao.maxDeptId(dept.corpId);
            if (StringUtils.isBlank(maxId)) {
                maxId = dept.corpId;
            }
            dept.id = String.valueOf(Long.valueOf(maxId) + 1);
            if (deptDao.addDept(dept.id, dept.name, dept.desc, dept.deptType, dept.corpId)) {
                return dept;
            } else {
                throw new ServiceException("write into database failed");
            }
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 修改一个部门的信息
     * 
     * @param dept
     * @return
     * @throws ServiceException
     */
    public Dept updateDept(Dept dept) throws ServiceException {
        try {
            if(deptDao.updateDept(dept.id, dept.name, dept.desc, dept.deptType, dept.corpId)) {
                return dept;
            } else {
                throw new ServiceException("write into database failed");
            }
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 删除指定部门
     * @param id
     * @return
     * @throws ServiceException
     */
    public boolean deleteDept(String id) throws ServiceException {
        try {
            return deptDao.delete(id);
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
     * 根据参数查询所有符合要求的部门
     * 
     * @param param
     * @return
     * @throws ServiceException
     */
    public List<Dept> queryDepts(QueryListParam param) throws ServiceException {
        try {
            String name = param.getLikeField("name", "");
            int from = (param.page - 1) * param.size + 1;
            int to = from + param.size - 1;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("list depts of name: {} from {} to {}", new Object[]{name, from, to});
            }
            if (!StringUtils.isEmpty(name)) {
                return deptDao.queryByName(name, from, to);
            } else {
                return deptDao.all();
            }
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public int countDepts(QueryListParam param) throws ServiceException {
        try {
            String name = param.getFields().get("name");
            if (!StringUtils.isEmpty(name)) {
                return deptDao.countByName(name);
            } else {
                return deptDao.count();
            }
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取一个公司所有的部门列表
     * @return
     * @throws ServiceException
     */
    public List<Dept> allDepts(String corpId) throws ServiceException {
        try {
            return deptDao.all(corpId);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * 获取所有的部门列表
     * @return
     * @throws ServiceException
     */
    public List<Dept> allDepts() throws ServiceException {
        try {
            return deptDao.all();
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
