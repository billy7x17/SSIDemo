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
package com.cloudmaster.cmp.web.page;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author <a href="mailto:hegq@neusoft.com">puras.he </a>
 * @version 1.0.0 18 Mar 2012
 */
public class TurnPageInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(TurnPageInterceptor.class);

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    public void init() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @seecom.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.
     * ActionInvocation)
     */
    @SuppressWarnings("unchecked")
    public String intercept(ActionInvocation invo) throws Exception {
        HttpServletRequest request = (HttpServletRequest) invo.getInvocationContext().get(
                ServletActionContext.HTTP_REQUEST);

        String url = request.getRequestURI();
        String param = "";

        if ("get".equalsIgnoreCase(request.getMethod())) {
            // param = request.getQueryString();
            StringBuffer params = new StringBuffer();
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String name = (String) paramNames.nextElement();
                Object obj = request.getParameterValues(name);
                if("msg".equalsIgnoreCase(name) || "message".equalsIgnoreCase(name) || "errorMsg".equalsIgnoreCase(name)){
                    continue;
                }
                String value = "";
                if (name.equals("viewExper")){
                    params.append(name + "=0&");
                }else{
                    if (obj instanceof String) {
                        value = (String) obj;
                        params.append(name + "=" + value + "&");
                    } else if (obj instanceof String[]) {
                        String[] values = (String[]) obj;
                        for (String v : values) {
                            params.append(name + "=" + v + "&");
                        }
                    } else {
                        value = obj.toString();
                        params.append(name + "=" + value + "&");
                    }
                }
            }

            param = params.toString();
            if (param.endsWith("&")) {
                param = param.substring(0, param.length() - 1);
            }

            if (log.isDebugEnabled()) {
                log.debug("URL ==>" + url);
                log.debug("Param ==>" + param);
                log.debug("Method ==>" + request.getMethod());
            }

        } else if ("post".equalsIgnoreCase(request.getMethod())) {
            StringBuffer params = new StringBuffer();
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String name = (String) paramNames.nextElement();
                Object obj = request.getParameterValues(name);
                if("msg".equalsIgnoreCase(name) || "message".equalsIgnoreCase(name) || "errorMsg".equalsIgnoreCase(name)){
                    continue;
                }
                String value = "";
                if(name == "viewExper"){
                    params.append(name + "=0&");
                }else{
                    if (obj instanceof String) {
                        value = (String) obj;
                        params.append(name + "=" + value + "&");
                    } else if (obj instanceof String[]) {
                        String[] values = (String[]) obj;
                        for (String v : values) {
                            params.append(name + "=" + v + "&");
                        }
                    } else {
                        value = obj.toString();
                        params.append(name + "=" + value + "&");
                    }
                }
            }

            param = params.toString();
            if (param.endsWith("&")) {
                param = param.substring(0, param.length() - 1);
            }
        }

        ActionContext.getContext().getParameters().put("url", url);
        ActionContext.getContext().getParameters().put("param", param);

        return invo.invoke();
    }

}
