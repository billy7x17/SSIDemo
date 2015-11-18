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
package com.cloudmaster.cmp.authority.auth;

import java.util.List;
import java.util.Map;

/**
 * 用于对用户进行鉴权的类。若用户有相应的权限，则继续用户的请求，若需要鉴权，则进行鉴权
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */

public class Authenticater implements IAuthenticater {

    private static final long serialVersionUID = 1L;

    private Map<String, String> authReportMap;

    private List<String> authIdList;

    /*
     * (non-Javadoc)
     * @see com.neusoft.mid.iamp.authority.auth.IAuthenticater#authenticateReport()
     */
    public boolean authenticateReport(String reportId) {

        if (authReportMap == null) {
//            return false;
            // 不做鉴权 TODO
            return true;
        }

        // 若authReportMap中没有包括该reportId，则认为鉴权失败，返回false
        if (!authReportMap.containsKey(reportId)) {
            return false;
        }

        // 若authReportMap中包括该reportId，则进行鉴权
        String authID = (String) authReportMap.get(reportId);
        // 鉴权成功，返回true
        if (null != authIdList) {
            for (String id : authIdList) {
                if (id.equals(authID)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map<String, String> getAuthReportMap() {
        return authReportMap;
    }

    public void setAuthReportMap(Map<String, String> authReportMap) {
        this.authReportMap = authReportMap;
    }

    public List<String> getAuthIdList() {
        return authIdList;
    }

    public void setAuthIdList(List<String> authIdList) {
        this.authIdList = authIdList;
    }

    /*
     * (non-Javadoc)
     * @see com.neusoft.mid.iamp.authority.auth.IAuthenticater#authenticate(java.lang.String)
     */
    public boolean authenticateUrl(String authId) {
        if (authIdList == null) {
            return false;
        }else { // 鉴权成功，继续用户的请求
            for (String id : authIdList) {
                if (id.equals(authId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
