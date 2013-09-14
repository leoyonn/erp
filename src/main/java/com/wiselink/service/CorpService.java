/**
 * CorpService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:27:12
 */
package com.wiselink.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.dao.CorpDAO;
import com.wiselink.dao.DeptDAO;
import com.wiselink.dao.SupplierDAO;
import com.wiselink.exception.ServiceException;
import com.wiselink.model.org.Corp;
import com.wiselink.model.org.Dept;
import com.wiselink.model.org.OrgType;
import com.wiselink.model.param.QueryListParam;
import com.wiselink.model.supplier.Supplier;
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;
import com.wiselink.utils.Utils;

/**
 * 组织结构相关的服务
 * @author leo
 */
@Service
public class CorpService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpService.class);

    @Autowired
    private CorpDAO corpDao;
    
    @Autowired
    private DeptDAO deptDao;

    @Autowired
    private SupplierDAO supplierDao;
    
    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    /**
     * 新建一个公司
     * 
     * @param corp
     */
    public OperResult<Corp> newCorp(Corp corp) {
        try {
            if (corpDao.addCorp(corp)) {
                return r(corp);
            } else {
                return r(ErrorCode.DbInsertFail, "添加公司失败，请检查参数");
            }
        } catch (Exception ex) {
            LOGGER.error("add corp: " + corp + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "添加公司失败：" , ex);
        }
    }

    /**
     * 修改一个公司的信息
     * @param corp
     * @return
     */
    public OperResult<Corp> updateCorp(Corp corp) {
        LOGGER.debug("update corp: {}", corp);
        try {
            if (corpDao.updateCorp(corp)) {
                return r(corp);
            } else {
                return r(ErrorCode.DbUpdateFail, "修改公司失败，请检查参数");
            }
        } catch (Exception ex) {
            LOGGER.error("update corp: " + corp + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "修改公司失败：", ex);
        }
    }


    /**
     * 查询一个公司
     * 
     * @param code
     * @return
     * @throws ServiceException
     */
    public OperResult<Integer> deleteCorps(List<String> ids) {
        OperResult<Integer> cnt = userService.countUsersInCorps(ids);
        if (cnt.error != ErrorCode.Success) {
            return r(cnt.error, cnt.reason);
        } else if (cnt.result > 0) {
            return r(ErrorCode.AuthDenied, "不能删除还有用户存在的公司");
        }
        for (String id: ids) {
            OperResult<List<Corp>> subs = allSubCorps(id);
            if (subs.error != ErrorCode.Success) {
                return r(subs.error, subs.reason);
            } else if (subs.result.size() > 0) {
                return r(ErrorCode.AuthDenied, "不能删除还有子公司存在的公司");
            }
            OperResult<List<Dept>> depts = deptService.allDepts(id);
            if (depts.error != ErrorCode.Success) {
                return r(depts.error, depts.reason);
            } else if (depts.result.size() > 0) {
                if (depts.result.size() == 1 && "大客户部".equals(depts.result.get(0).name)) {
                    try {
                        deptDao.delete(depts.result.get(0).id);
                    } catch (Exception ex) {
                        return r(ErrorCode.DbDeleteFail, "删除公司对应的#大客户部#失败：" , ex);
                    }
                } else {
                    return r(ErrorCode.AuthDenied, "不能删除还有子部门存在的公司");
                }
            }
        }
        try {
            return r(corpDao.delete(ids));
        } catch (Exception ex) {
            LOGGER.error("delete corp: " + ids + " got exception", ex);
            return r(ErrorCode.DbDeleteFail, "删除公司失败：" , ex);
        }
    }

    /**
     * 查询一个公司
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    public OperResult<Corp> getCorp(String id) {
        try {
            Corp corp = corpDao.find(id);
            if (corp != null) {
                return r(corp);
            } else {
                corp = supplierDao.findById(id);
                if (corp != null) {
                    return r(corp);
                }
                return r(ErrorCode.DbQueryFail, "无此id对应的公司");
            }
        } catch (Exception ex) {
            LOGGER.error("query corp: " + id + " got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找公司失败：" , ex);
        }
    }

    /**
     * 获取所有的公司
     * 
     * @return
     */
    public OperResult<List<Corp>> queryCorps(QueryListParam param, String myCorpId) {
        String name = param.getLikeField("name", "");
        int typeInt = param.getIntField("type", -1);
        OrgType type = OrgType.value(typeInt);
        int from = (param.page - 1) * param.size;
        int to = from + param.size;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("query depts of param: {} from {} to {}", new Object[]{param, from, to});
        }
        List<Corp> result = new ArrayList<Corp>();
        try {
            switch (type) {
                case Supplier: {
                    String mode = param.getStringField("mode", "");
                    String stype = param.getStringField("stype", "");
                    String status = param.getStringField("status", "");
                    // TODO: 下级单位供货的公司
                    List<String> suppliers = supplierDao.getSuppliers(myCorpId);
                    switch (param.q) {
                        case and: result.addAll(supplierDao.queryByAnd(name, mode, stype, status, suppliers));
                        break;
                        case or:  result.addAll(supplierDao.queryByOr(name, mode, stype, status, suppliers));
                        break;
                        default:
                            break;
                    }
                    break;
                }
                default: {
                    if (typeInt == 0) {
                        result.addAll(corpDao.allNotSupplier(name, myCorpId));
                    } else {
                        // TODO: 下级单位供货的公司
                        List<String> suppliers = supplierDao.getSuppliers(myCorpId);
                        result.addAll(corpDao.all(name, myCorpId, suppliers));
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("get all corps: got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找公司失败：", ex);
        }
        int total = result.size();
        result = Utils.sublist(from, to, result);
        return r(result, total);
    }

    /**
     * 获取指定公司id的所有子公司（一级）
     * 
     * @param corpId
     * @return
     */
    public OperResult<List<Corp>> allSubCorps(String corpId) {
        try {
            return r(corpDao.subCorps(corpId));
        } catch (Exception ex) {
            LOGGER.error("get all sub corps of " + corpId + ": got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找子公司公司失败：" , ex);
        }
    }

    /**
     * @param fromJson
     * @return
     */
    public OperResult<Supplier> newSupplier(String myCorpId, Supplier supplier) {
        try {
            if (!supplierDao.add(supplier)) {
                return r(ErrorCode.DbInsertFail, "添加供货商失败，请检查参数");
            }
            supplierDao.addRelation(myCorpId, supplier.id);
            
        } catch (Exception ex) {
            LOGGER.error("add supplier : " + supplier + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "添加供货商失败：" , ex);
        }
        Dept dept = (Dept) new Dept(null, "大客户部", "", "", supplier.id).setCreatorId(supplier.creatorId);
        OperResult<Dept> r = deptService.newDept(dept);
        if (r.error != ErrorCode.Success) {
            return r(r.error, "未供货商添加#大客户部#" + dept + "失败");
        }
        return r(supplier);
    }

    /**
     * 修改一个公司的信息
     * @param corp
     * @return
     */
    public OperResult<Supplier> updateSupplier(Supplier supplier) {
        LOGGER.debug("update supplier: {}", supplier);
        try {
            if (supplierDao.update(supplier)) {
                return r(supplier);
            } else {
                return r(ErrorCode.DbUpdateFail, "修改供货商失败，请检查参数");
            }
        } catch (Exception ex) {
            LOGGER.error("update supplier: " + supplier + " got exception", ex);
            return r(ErrorCode.DbInsertFail, "修改供货商失败：", ex);
        }
    }

    public OperResult<List<Corp>> all() {
        try {
            return r(corpDao.all());
        } catch (Exception ex) {
            LOGGER.error("get all corps got exception", ex);
            return r(ErrorCode.DbQueryFail, "查询所有公司：", ex);
        }
    }
}
