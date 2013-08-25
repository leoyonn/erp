/**
 * CorpService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:27:12
 */
package com.wiselink.service;

import java.util.Arrays;
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
import com.wiselink.result.ErrorCode;
import com.wiselink.result.OperResult;

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
     * @param id
     * @return
     * @throws ServiceException
     */
    public OperResult<Boolean> deleteCorp(String id) {
        OperResult<Integer> cnt = userService.countUsersInCorps(Arrays.asList(new String[]{id}));
        if (cnt.error != ErrorCode.Success) {
            return r(cnt.error, cnt.reason);
        } else if (cnt.result > 0) {
            return r(ErrorCode.AuthDenied, "不能删除还有用户存在的公司");
        }
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
            return r(ErrorCode.AuthDenied, "不能删除还有子部门存在的公司");
        }
        try {
            return r(corpDao.delete(id));
        } catch (Exception ex) {
            LOGGER.error("delete corp: " + id + " got exception", ex);
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
    public OperResult<List<Corp>> allCorps() {
        try {
            return r(corpDao.all());
        } catch (Exception ex) {
            LOGGER.error("get all corps: got exception", ex);
            return r(ErrorCode.DbQueryFail, "查找公司失败：" , ex);
        }
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
}
