/**
 * Positions.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-17 下午2:52:49
 */
package com.wiselink.model.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiselink.utils.Utils;

/**
 * 加载和维护所有岗位/职位列表
 * @author leo
 */
public class Positions {
    private static final Logger LOGGER = LoggerFactory.getLogger(Positions.class);
    private static final String POSITION_PATH = "conf/positions.xml";

    /**
     * 从配置文件中加载所有岗位
     */
    private void load() {
        LOGGER.debug("loading positions from {}...", POSITION_PATH);
        Document document = Utils.loadXmlDoc(POSITION_PATH);
        // positions -> postion
        Element rootEle = document.getRootElement();
        for (Object posEle: rootEle.elements()) {
            Element e = ((Element) posEle);
            Position pos = new Position(Integer.valueOf(e.attributeValue("code")),
                    e.attributeValue("name"), e.attributeValue("desc"));
            LOGGER.debug("loaded position {}...", pos);
            positions.put(pos.code, pos);
        }
    }

    private final Map<Integer, Position> positions = new HashMap<Integer, Position>();

    private Positions() {
        load();
    }

    private static final Positions instance = new Positions();

    public static Positions getInstance() {
        return instance;
    }

    /**
     * 获取所有的岗位
     * @return
     */
    public final Collection<Position> allPositions() {
        return positions.values();
    }

    /**
     * 获取code指定的岗位
     * 
     * @param code
     * @return
     */
    public Position getPosition(int code) {
        return positions.get(code);
    }
}
