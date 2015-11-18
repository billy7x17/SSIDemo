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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.neusoft.mid.iamp.logger.LogService;
import com.cloudmaster.cmp.web.authority.user.UserInfo;

/**
 * 对用户请求的报表ID以及登出进行鉴权，若鉴权成功，则继续用户的请求。 如果用户没有相应的操作权限，则转向鉴权失败页面。
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class AuthenticateFilter implements Filter {

    // private static LogService logger = LogService.getLogger(AuthenticateFilter.class);

    private FilterConfig config;

    private String relogin;

    public void destroy() {

    }

    /**
     * 获取用户请求的报表ID,若鉴权成功，则继续用户的请求；若鉴权失败，则转向失败页面
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (null == request.getSession()) {
            response.sendRedirect(request.getContextPath() + relogin);
            return;
        }

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
                SessionKeys.userInfo.toString());
        if (null == userInfo) {
            response.sendRedirect(request.getContextPath() + relogin);
            return;
        }

        // 获取请求uri中的reportId
        String reportId = request.getParameter("reportId");
        if (null == reportId) {
            chain.doFilter(req, res);
            return;
        }

        // 从session中取出authenticater对象，传入所需参数进行鉴权
        IAuthenticater authenticater = (IAuthenticater) request.getSession().getAttribute(
                SessionKeys.authenticater.toString());

        if (null == authenticater) {
            response.sendRedirect(request.getContextPath() + relogin);
            return;
        }

        // 鉴权成功，继续用户的请求
        if (authenticater.authenticateReport(reportId)) {
            chain.doFilter(req, res);
        } else {
            // 鉴权失败，转向鉴权失败页面
            String noauthority = request.getSession().getServletContext().getInitParameter(
                    "noauthority");

            response.sendRedirect(request.getContextPath() + noauthority);
            return;
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        relogin = config.getInitParameter("relogin");
    }

}
