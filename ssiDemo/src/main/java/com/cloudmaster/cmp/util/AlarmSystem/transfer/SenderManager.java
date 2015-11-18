/*******************************************************************************
 * @(#)SenderManager.java Dec 4, 2012
 *
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.cloudmaster.cmp.util.AlarmSystem.transfer;

import java.util.Map;

/**
 * @author <a href="mailto:gong.x@neusoft.com">gong.x </a>
 * @version $Revision 1.1 $ Dec 4, 2012 3:18:57 PM
 */
public class SenderManager {
    public ResponseObject send(TransportObject report)
            throws Exception {
        // 默认ConnectionTimeout、Timeout为1分钟
        HttpSender sender = new HttpSender(1000 * 30, 1000 * 60 * 2);
        return sender.send(report);
    }

    public ResponseObject send(Object obj, Map<String, String> paramMap) throws Exception {
        // 默认ConnectionTimeout、Timeout为1分钟
        HttpSender sender = new HttpSender(1000 * 30, 1000 * 60 * 2);
        return sender.send(obj, paramMap);
    }
}
