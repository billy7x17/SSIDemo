/*******************************************************************************
 * @(#)TransportObject.java Dec 4, 2012
 *
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.cloudmaster.cmp.util.AlarmSystem.transfer;

import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author <a href="mailto:hanbj@neusoft.com">baojun.han </a>
 * @version $Revision 1.1 $ Dec 4, 2012 2:32:31 PM
 */
public class TransportObject {
    private Map<String, String> vals = new LinkedHashMap<String, String>();

    public void setValue(String key, String value) {
        if (key == null || value == null) {
            return;
        }
        vals.put(key, value);
    }

    public String getValue(String key) {
        return vals.get(key);
    }

    public void toStream(DataOutput out) throws IOException {
        for (Entry<String, String> entry : vals.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            out.writeUTF(key);
            out.writeUTF(val);
        }
        out.writeUTF(new String(""));
    }
}
