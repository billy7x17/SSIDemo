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
package com.cloudmaster.cmp.web.authority.role;

import java.util.ArrayList;
import java.util.List;

import com.cloudmaster.cmp.web.authority.auth.AuthInfo;

/**
 * 构造角色的权限树结构
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class RoleTreeCreator implements IRoleTreeCreator {

    private static final int FIRSTFINALPOSTION = 2;

    private static final int SECONDFINALPOSTION = 5;

    private static final int THIRDINALPOSTION = 8;
    
    public List<AuthInfo> creatRoleTree(List<AuthInfo> authorityList) {

        List<AuthInfo> authRootList = new ArrayList<AuthInfo>();
        List<AuthInfo> level1 = new ArrayList<AuthInfo>();
        List<AuthInfo> level2 = new ArrayList<AuthInfo>();
        List<AuthInfo> level3 = new ArrayList<AuthInfo>();
        List<AuthInfo> level4 = new ArrayList<AuthInfo>();

        /**
         * modify by sunwei
         */
        for (AuthInfo obj : authorityList) {
            if (obj.getAuthId().endsWith("_00_00_00")) {
                // 判断结尾“_00_00_00”为第一级放置level1中
                level1.add(obj);
            } else if (obj.getAuthId().endsWith("_00_00")) {
                // 判断结尾“_00_00”为第二级放置level2中
                level2.add(obj);
            } else if (obj.getAuthId().endsWith("_00")) {
                // 判断结尾“_00”为第三级放置level3中
                level3.add(obj);
            } else {
                // 第四级放置level4中
                level4.add(obj);
            }
        }
        for (AuthInfo obj : level1) {
            String startWith = obj.getAuthId().substring(0, FIRSTFINALPOSTION);
            List<AuthInfo> child = new ArrayList<AuthInfo>();
            obj.setAuthSubList(child);
            authRootList.add(obj);
            // 遍历level2
            for (AuthInfo sobj : level2) {
                if (sobj.getAuthId().startsWith(startWith)) {
                    String sstartWith = sobj.getAuthId().substring(0, SECONDFINALPOSTION);
                    List<AuthInfo> schild = new ArrayList<AuthInfo>();
                    sobj.setAuthSubList(schild);
                    child.add(sobj);
                    // 遍历level3
                    for (AuthInfo tobj : level3) {
                        if (tobj.getAuthId().startsWith(sstartWith)) {
                            String tstartWith = tobj.getAuthId().substring(0, THIRDINALPOSTION);
                            List<AuthInfo> tchild = new ArrayList<AuthInfo>();
                            tobj.setAuthSubList(tchild);
                            schild.add(tobj);
                            // 遍历level4
                            for (AuthInfo fobj : level4) {
                                if (fobj.getAuthId().startsWith(tstartWith)) {
                                    List<AuthInfo> fchild = new ArrayList<AuthInfo>();
                                    fobj.setAuthSubList(fchild);
                                    tchild.add(fobj);
                                }

                            }
                        }

                    }
                }

            }
        }
        return authRootList;

    }
}
