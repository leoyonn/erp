/**
 * OrgService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-16 下午3:27:12
 */
package com.wiselink.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiselink.dao.CorpDAO;
import com.wiselink.dao.DeptDAO;
import com.wiselink.model.org.Org;

/**
 * 组织结构相关的服务
 * @author leo
 */
@Service
public class OrgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgService.class);

    @Autowired
    private CorpDAO corpDao;
    
    @Autowired
    private DeptDAO deptDao;
    /**
     * @param scopes
     * @return
     */
    public List<Org> getOrgs(List<String> orgIds) {
        // TODO 分流到corpDao、
        return null;
                
    }
    
}
