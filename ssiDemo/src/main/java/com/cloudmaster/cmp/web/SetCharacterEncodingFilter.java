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

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class SetCharacterEncodingFilter implements Filter {
    protected String encoding = null;

    protected String encodingWlc = null;

    protected FilterConfig filterConfig = null;

    protected boolean ignore = true;

    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if(req.getRequestURI().contains("ssoKnowledge")){
            chain.doFilter(request, response);
            return;
        }
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encod = selectEncoding(request);
            String encodWlc = selectEncodingWlc(request);
            if (request.getContentType() == null) {
                request.setCharacterEncoding(encodWlc);
                response.setContentType("application/x-www-form-urlencoded; charset=" + encodWlc);
                response.setCharacterEncoding(encodWlc);
            } else {
                request.setCharacterEncoding(encod);
            }
            //encod = null;
            //encodWlc = null;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig tempFilterConfig) throws ServletException {
        this.filterConfig = tempFilterConfig;
        this.encoding = tempFilterConfig.getInitParameter("encoding");
        this.encodingWlc = tempFilterConfig.getInitParameter("encoding");
        String value = tempFilterConfig.getInitParameter("ignore");
        if (value == null) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("true")) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("yes")) {
            this.ignore = true;
        } else {
            this.ignore = false;
        }
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }

    protected String selectEncodingWlc(ServletRequest request) {
        return (this.encodingWlc);
    }

}
