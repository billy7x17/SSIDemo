/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.performance.util;

import java.io.Serializable;
import java.util.Map;
/**
 * 转换性能指标属性
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
@SuppressWarnings("rawtypes")
public class PropertyValueChange implements Serializable {
    private static final long serialVersionUID = -4251447579788685017L;
 
    private Map map;
    public String change(String tagName, String tagValue) {
        String values = (String) map.get(tagName);
        if (values != null) {
            String[] array = values.split(";");
            for (int i = 0; i < array.length; i++) {
                String[] tagValues = array[i].split(":");
                if (tagValues.length >= 2) {
                    if (tagValues[0].equals(tagValue)) {
                        return tagValues[1];
                    }
                }
            }
        } else {
            return tagValue;
        }
        return tagValue;
    }
    public Map getMap() {
        return map;
    }
    public void setMap(Map map) {
        this.map = map;
    }
}
