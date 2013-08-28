/**
 * FuncModules.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-12 下午2:56:15
 */
package com.wiselink.model.role;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiselink.utils.Utils;

/**
 * 管理系统中所有的功能模块
 * @author leo
 */
public class FuncModules {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncModules.class);
    private static final String FUNC_ROLE_PATH = "conf/func-role.xml";

    /**
     * 从配置文件中加载所有功能模块
     */
    private void load() {
        LOGGER.debug("loading modules from {}...", FUNC_ROLE_PATH);
        Document document = Utils.loadXmlDoc(FUNC_ROLE_PATH);
        Element root = document.getRootElement();
        for (Object moduleEle: root.elements()) {
            Element me = ((Element) moduleEle);
            FuncModule module = new FuncModule(Integer.valueOf(me.attributeValue("code")),
                    me.attributeValue("name"), me.attributeValue("desc"));
            LOGGER.debug("loaded module {}...", module);
            for (Object funcEle: me.elements()) {
                Element fe = ((Element) funcEle);
                Func func = new Func(Integer.valueOf(fe.attributeValue("code")),
                        fe.attributeValue("name"), fe.attributeValue("desc"), module.code)
                        .setModuleName(module.name);
                module.addFunc(func);
                LOGGER.debug("loaded func {}...", module);
            }
            modules.put(module.code, module);
        }
        
        for (Map.Entry<Integer, FuncModule> e: modules.entrySet()) {
            for (Func func: e.getValue().getFuncs()) {
                funcs.put(func.code, func);
            }
        }
    }

    private final Map<Integer, FuncModule> modules = new HashMap<Integer, FuncModule>();
    private final Map<Integer, Func> funcs = new HashMap<Integer, Func>();

    private FuncModules() {
        load();
    }

    private static final FuncModules instance = new FuncModules();

    public static FuncModules getInstance() {
        return instance;
    }

    /**
     * 获取所有的功能模块
     * @return
     */
    public final Collection<FuncModule> allModules() {
        return modules.values();
    }

    /**
     * 获取所有的功能
     * @return
     */
    public final Collection<Func> allFuncs() {
        return funcs.values();
    }

    public final Map<Integer, Func> allFuncsMap() {
        return funcs;
    }

    /**
     * 获取code指定的功能模块
     * 
     * @param code
     * @return
     */
    public FuncModule getModule(int code) {
        return modules.get(code);
    }

    /**
     * 获取code指定的功能
     * 
     * @param code
     * @return
     */
    public Func getFunc(int code) {
        return funcs.get(code);
    }
}