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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 对用户请求的uri进行鉴权，鉴权成功，则继续用户的请求，鉴权失败，则转向失败页面
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class AuthenticateInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    public void destroy() {

    }

    public void init() {

    }

    @SuppressWarnings("unchecked")
    public String intercept(ActionInvocation invocation) throws Exception {
        Map session = invocation.getInvocationContext().getSession();

        // session为空时，返回到登录页面
        if (null == session) {
            return "LOGIN";
        }

        String actionName = invocation.getProxy().getActionName();
        if (actionName.endsWith("Login")) {
            return invocation.invoke();
        }

        UserInfo userInfo = (UserInfo) session.get(SessionKeys.userInfo);
        if (null == userInfo) {
            return "LOGIN";
        }

        Object object = invocation.getAction();

        if (object instanceof IAuthIdGetter) {
            String authId = ((IAuthIdGetter) object).getAuthId();

            HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext()
                    .get(ServletActionContext.HTTP_REQUEST);

            // 从session中获取authenticater对象，对用户的操作进行鉴权
            IAuthenticater authenticater = (IAuthenticater) request.getSession().getAttribute(
                    SessionKeys.authenticater.toString());

            // 若鉴权对象为空，则转向登录页面
            if (null == authenticater) {
                return "LOGIN";
            }
            // 鉴权成功，继续用户的请求
            if (authenticater.authenticateUrl(authId)) {
                return invocation.invoke();
            }
            // 鉴权失败，则转向失败页面
            return "NOAUTHORITY";
        }
        return invocation.invoke();
    }

}
