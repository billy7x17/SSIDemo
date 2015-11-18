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
package com.cloudmaster.cmp.web;

import java.util.ResourceBundle;

import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 所有Action的父类,提供基本数据操作接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue </a>
 * @version 1.0.0 18 Mar 2012
 */
public abstract class BaseAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    protected IbatisDAO ibatisDAO;

    protected String msg;
    
    protected String errorMsg ;

    // 用于鉴权的权限点ID
    protected String authId;

    /**
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        if (null != msg) {
            this.addActionMessage(msg);
        }
    }

    
    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        if (null != errorMsg) {
            this.addActionMessage(errorMsg);
        }
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo = (UserInfo) ActionContext.getContext().getSession()
                .get(SessionKeys.userInfo);
        return userInfo;

    }

    
    //liujingwei add begin
    public String getText(String aTextName,String[] args ) {
        String junit = System.getProperty("junit");
        /*
         * 只有在junit运行环境时才使用以下判断方式
         * 测试使用，不对args参数进行模式匹配处理
         * 防止上线的时候对运行时有影响
         */
        if ("true".equalsIgnoreCase(junit)) {
            String bundleName = this.getClass().getPackage().getName();

            String rv = aTextName;

            try {
                // 首先递归查找
                rv = getRecursionText(bundleName, aTextName);
                /*
                 * 因为找不到key会抛出异常，所以最后查找全局资源
                 * 即便发生异常也不会影响前面的查找的结果
                 */
                rv = ResourceBundle.getBundle("ApplicationResources").getString(aTextName);
            } catch (Exception e) {
            }// end try

            return rv;
        }
        return super.getText(aTextName, args);
    }
    
    @Override
    public String getText(String aTextName) {
        String junit = System.getProperty("junit");
        /*
         * 只有在junit运行环境时才使用以下判断方式
         * 防止上线的时候对运行时有影响
         */
        if ("true".equalsIgnoreCase(junit)) {
            String bundleName = this.getClass().getPackage().getName();

            String rv = aTextName;

            try {
                // 首先递归查找
                rv = getRecursionText(bundleName, aTextName);
                /*
                 * 因为找不到key会抛出异常，所以最后查找全局资源
                 * 即便发生异常也不会影响前面的查找的结果
                 */
                rv = ResourceBundle.getBundle("ApplicationResources").getString(aTextName);
            } catch (Exception e) {
            }// end try

            return rv;
        }
        return super.getText(aTextName);
    }

    /**
     * 递归获得资源内容
     * @param bundleName
     * @param aTextName
     * @return
     */
    private String getRecursionText(String bundleName, String aTextName) {
        String rv = aTextName;

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName + ".package");
            rv = bundle.getString(aTextName);
        } catch (Exception e) {
            if (bundleName.lastIndexOf('.') > 0) {
                bundleName = bundleName.substring(0, bundleName.lastIndexOf('.'));
                rv = getRecursionText(bundleName, aTextName);
            }
            return rv;
        }// end try

        return rv;
    }
    //liujingwei add finish
}
