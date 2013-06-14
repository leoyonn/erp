/**
 * FuncService.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午2:46:06
 */
package com.wiselink.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wiselink.model.role.Func;
import com.wiselink.model.role.FuncModule;
import com.wiselink.model.role.FuncModules;

/**
 * 
 * @author leo
 */
@Service
public class FuncService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncService.class);
    private static final FuncModules modules = FuncModules.getInstance();
    public FuncService() {
        LOGGER.info("func service init...");
    }
    
    public List<Func> getFuncs(List<Integer> funcCodes) {
        List<Func> funcs = new ArrayList<Func>();
        for (int code: funcCodes) {
            funcs.add(modules.getFunc(code));
        }
        return funcs;
    }
    
    public Collection<FuncModule> allModules() {
        return modules.allModules();
    }
}
