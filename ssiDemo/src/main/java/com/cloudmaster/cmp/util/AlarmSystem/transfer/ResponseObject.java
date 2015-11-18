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

package com.cloudmaster.cmp.util.AlarmSystem.transfer;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author <a href="mailto:gong.x@neusoft.com">gong.x </a>
 * @version $Revision 1.1 $ Dec 5, 2012 10:26:12 AM
 */
public class ResponseObject extends TransportObject {

    private static final String STATUS_KEY = "code";

    private static final String ERR = "error";

    public static ResponseObject fromStream(DataInput in) throws IOException {
        ResponseObject response = new ResponseObject();
        String key, val;
        key = in.readUTF();
        while (key.length() != 0) {
            // while (!key.equals("end")) {
            val = in.readUTF();
            response.setValue(key, val);

            key = in.readUTF();
        }
        return response;
    }

    public String getStatus() {
        return this.getValue(STATUS_KEY);
    }

    public String getError() {
        return this.getValue(ERR);
    }

    public void setError(String msg) {
        this.setValue(ERR, msg);
        this.setStatus("1");
    }

    public void setStatus(String code) {
        this.setValue(STATUS_KEY, code);
    }
}
