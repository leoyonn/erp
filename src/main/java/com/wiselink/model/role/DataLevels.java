/**
 * DataLevels.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-14 下午10:02:30
 */
package com.wiselink.model.role;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiselink.base.Config;

/**
 * 加载和维护所有的数据角色级别
 * @author leo
 */
public class DataLevels {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLevel.class);
    private static final String DATA_ROLE_PATH = Config.path("conf/data-role.xml");

    /**
     * 从配置文件中加载所有功能模块
     */
    private void load() {
        LOGGER.debug("loading data-levels from {}...", DATA_ROLE_PATH);
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new File(DATA_ROLE_PATH));
        } catch (DocumentException ex) {
            throw new RuntimeException(ex);
        }
        // data-role >> levels >> level
        Element levelsEle = document.getRootElement().element("levels");
        for (Object levelEle: levelsEle.elements()) {
            Element le = ((Element) levelEle);
            DataLevel level = new DataLevel(Integer.valueOf(le.attributeValue("code")),
                    le.attributeValue("name"), le.attributeValue("desc"));
            LOGGER.debug("loaded data level {}...", level);
            levels.put(level.code, level);
        }
    }

    private final Map<Integer, DataLevel> levels = new HashMap<Integer, DataLevel>();

    private DataLevels() {
        load();
    }

    private static final DataLevels instance = new DataLevels();

    public static DataLevels getInstance() {
        return instance;
    }

    /**
     * 获取所有的功能
     * @return
     */
    public final Collection<DataLevel> allLevels() {
        return levels.values();
    }

    /**
     * 获取code指定的功能
     * 
     * @param code
     * @return
     */
    public DataLevel getLevel(int code) {
        return levels.get(code);
    }

}
