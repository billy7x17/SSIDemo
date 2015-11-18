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
package com.cloudmaster.cmp.web.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 对用户的请求进行过滤，判断用户是否登录，若没有登录，则转向登录页面
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class LoginFilter implements Filter {
    private static LogService logger = LogService.getLogger(LoginFilter.class);

    private FilterConfig config;

    // 重新登录的地址
    private String loginPath;

    // 默认主页
    private String indexPath;

    // css,ico,jpg,gif组成的string，配置在web.xml中，对表单中的这些内容不进行过滤
    private String suffix;

    public void destroy() {

    }

    /*
     * @param ServletRequest
     * @param ServletResponse
     * @param FilterChain
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String requestUrl = request.getRequestURI();
        String contextPath = request.getContextPath();

        // 从session中获取用户信息
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
                SessionKeys.userInfo.toString());
        if (requestUrl.contains("HttpClientTest") || requestUrl.endsWith("Login.action")
                || requestUrl.endsWith("login.jsp") || requestUrl.contains("Authentication.jsp")) {
            chain.doFilter(req, res);
            return;
        } else {
            String[] str = suffix.split(",");
            for (String s : str) {
                if (requestUrl.endsWith(s)) {
                    chain.doFilter(req, res);
                    return;
                }
            }
        }

        if (null == userInfo) {
            response.sendRedirect(contextPath + loginPath);
            if (logger.isDebugEnable()) {
                logger.debug("该用户没有登录，转向登录页面。");
            }
        } else if (requestUrl.equals(contextPath)
                || requestUrl.substring(0, requestUrl.length() - 1).equals(contextPath)) {
            response.sendRedirect(contextPath + indexPath);
            if (logger.isDebugEnable()) {
                logger.debug("返回首页。");
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        loginPath = config.getInitParameter("loginPath");
        indexPath = config.getInitParameter("indexPath");
        suffix = config.getInitParameter("suffix");
    }
}
