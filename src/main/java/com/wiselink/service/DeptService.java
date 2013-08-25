/**
 * DeptService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-24 下午4:18:00
 */
package com.wiselink.service;

import java.util.ArrayList;
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
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
import com.wiselink.utils.IdUtils;

/**
 * 
 * @author leo
 */
@Service
public class DeptService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeptService.class);

    @Autowired
    private CorpDAO corpDao;
    
    @Autowired
    private DeptDAO deptDao;

    /**
     * 添加一个新的部门
     * 
     * @param dept
     * @return
     */
    public OperResult<Dept> newDept(Dept dept) {
        try {
            // 1 verify corp id: should be legal in corp dao.
            Corp corp = corpDao.find(dept.corpId);
            if (corp == null) {
                return r(ErrorCode.InvalidParam, "查无此corpId:" + dept.corpId);
            }
            // 2 verify dept name: should not exist under same corpId
            Dept got = deptDao.findByName(dept.name, dept.corpId);
            if (got != null) {
                return r(ErrorCode.InvalidParam, "此corpId:" + dept.corpId + "下同名部门已存在：" + got.id);
            }
            // 3 get max id of current corp.
            String maxId = deptDao.maxDeptId(dept.corpId);
            if (StringUtils.isBlank(maxId)) {
                maxId = dept.corpId;
            }
            // 4 do add dept
            dept.id = String.valueOf(Long.valueOf(maxId) + 1);
            if (!deptDao.addDept(dept.id, dept.name, dept.desc, dept.deptType, dept.corpId)) {
                return r(ErrorCode.DbInsertFail, "添加部门失败，请检查参数");
            }
            // 5. return result.
            dept.setCorp(corp);
            return r(dept);
        } catch (Exception ex) {
            LOGGER.error("add dept: " + dept + " got ex", ex);
            return r(ErrorCode.DbInsertFail, "添加部门失败：" , ex);
        }
    }

    /**
     * 修改一个部门的信息
     * 
     * @param dept
     * @return
     */
    public OperResult<Dept> updateDept(Dept dept) {
        try {
            // 1 verify corp id: should be legal in corp dao.
            Corp corp = corpDao.find(dept.corpId);
            if (corp == null) {
                return r(ErrorCode.InvalidParam, "查无此corpId:" + dept.corpId);
            }
            // 2 do update dept
            if(deptDao.updateDept(dept.id, dept.name, dept.desc, dept.deptType, dept.corpId)) {
                return r(dept.setCorp(corp));
            }
        } catch (Exception ex) {
            LOGGER.error("update dept: " + dept + " got ex", ex);
            return r(ErrorCode.DbUpdateFail, "更新部门失败：" , ex);
        }
        return r(ErrorCode.DbUpdateFail, "更新部门失败，请检查参数");
    }

    /**
     * 删除指定部门
     * 
     * @param ids
     * @return
     */
    public OperResult<String> deleteDept(List<String> ids) {
        int n = -1;
        try {
            n = deptDao.delete(ids);
        } catch (Exception ex) {
            LOGGER.error("delete depts: " + ids + " got ex", ex);
            return new OperResult<String>(ErrorCode.DbDeleteFail, ex.getMessage());
        }
        if (n > 0) {
            return new OperResult<String>("成功删除" + n + "个部门");
        } else {
            return new OperResult<String>(ErrorCode.DbDeleteFail, "删除部门失败，请检查参数");
        }
    }

    /**
     * 获取部门信息
     * 
     * @param id
     * @return
     */
    public OperResult<Dept> getDept(String id) {
        Dept dept = null;
        Corp corp = null;
        try {
            dept = deptDao.find(id);
            if (dept == null) {
                return r(ErrorCode.DbQueryFail, "获取部门失败，无此id的部门");
            }
            corp = corpDao.find(dept.corpId);
            if (corp == null) {
                return r(ErrorCode.InvalidParam, "获取部门失败，部门corpId不合法：" + dept.corpId);
            }
        } catch (Exception ex) {
            LOGGER.error("get dept: " + id + " got ex", ex);
            return r(ErrorCode.DbQueryFail, "获取部门失败：" , ex);
        }
        return r(dept.setCorp(corp));
    }

    /**
     * 通过或名称获取部门
     * 
     * @param name
     * @param corpId
     * @return
     */
    public OperResult<Dept> getDeptByName(String name, String corpId) {
        Dept dept = null;
        Corp corp = null;
        try {
            corp = corpDao.find(corpId);
            if (corp == null) {
                return r(ErrorCode.InvalidParam, "无此corpId对应的公司：" + corpId);
            }
            dept = deptDao.findByName(name, corpId);
            if (dept == null) {
                return r(ErrorCode.DbQueryFail, "获取部门失败，无此名称的部门");
            }
        } catch (Exception ex) {
            LOGGER.error("get dept: " + name + ", corpId:" + corpId + " got ex", ex);
            return r(ErrorCode.DbQueryFail, "获取部门失败：" , ex);
        }
        return r(dept.setCorp(corp));
    }

    /**
     * 根据参数查询所有符合要求的部门
     * 
     * @param param
     * @return
     * @throws ServiceException
     */
    public OperResult<List<Dept>> queryDepts(QueryListParam param, String corpId) {
        String name = param.getLikeField("name", "");
        int from = (param.page - 1) * param.size + 1;
        int to = from + param.size - 1;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("list depts of name: {} from {} to {}", new Object[]{name, from, to});
        }
        Corp corp = null;
        int total = 0;
        List<Dept> depts = Collections.emptyList();
        try {
            // verify corp
            corp = corpDao.find(corpId);
            if (corp == null) {
                return r(ErrorCode.InvalidParam, "无此corpId对应的公司：" + corpId);
            }
            if (!StringUtils.isEmpty(name)) {
                // get total
                total = deptDao.countByName(name, corpId);
                if (total <= 0) {
                    return r(ErrorCode.DbEmpty, "查无相关数据");
                }
                // get depts
                depts = deptDao.queryByName(name, corpId, from, to);
            } else {
                depts = deptDao.all();
                total = depts.size();
            }
        } catch (Exception ex) {
            LOGGER.error("get dept: " + name + ", corpId:" + corpId + " got ex", ex);
            return r(ErrorCode.DbQueryFail, "获取部门失败：" , ex);
        }
        for (Dept dept: depts) {
            dept.setCorp(corp);
        }
        return r(depts, total);
    }

    /**
     * 获取一个公司所有的部门列表
     * @return
     * @throws ServiceException
     */
    public OperResult<List<Dept>> allDepts(String corpId) {
        Corp corp = null;
        List<Dept> depts = Collections.emptyList();
        try {
            // verify corp
            corp = corpDao.find(corpId);
            if (corp == null) {
                return r(ErrorCode.InvalidParam, "无此corpId对应的公司：" + corpId);
            }
            depts = deptDao.all(corpId);
        } catch (Exception ex) {
            LOGGER.error("get depts of corpId:" + corpId + " got ex", ex);
            return r(ErrorCode.DbQueryFail, "获取部门失败：" , ex);
        }
        for (Dept dept: depts) {
            dept.setCorp(corp);
        }
        return r(depts, depts.size());
    }

    /**
     * 获取所有的部门列表
     * 
     * @return
     */
    public OperResult<List<Dept>> allDepts() {
        List<Dept> depts = Collections.emptyList();
        try {
            depts = deptDao.all();
        } catch (Exception ex) {
            LOGGER.error("get all depts got ex", ex);
            return r(ErrorCode.DbQueryFail, "获取部门失败：" , ex);
        }
        return r(depts, depts.size());
    }

    /**
     * @param scopeIds
     * @return
     * @throws ServiceException 
     */
    @Deprecated
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
