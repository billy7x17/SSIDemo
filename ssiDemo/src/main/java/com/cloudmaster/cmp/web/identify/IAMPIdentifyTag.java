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
package com.cloudmaster.cmp.web.identify;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.cloudmaster.cmp.authority.auth.IAuthenticater;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.core.CommonStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 这个自定义标签作用是：对需要鉴权的按钮或链接进行鉴权
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue </a>
 * @version 1.0.0 18 Mar 2012
 */
public class IAMPIdentifyTag extends BodyTagSupport {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用于日志输出
     */
    private static LogService logger = LogService.getLogger(IAMPIdentifyTag.class);

    /**
     * 标签需要的参数，权限点
     */
    private String authid;

    /**执行该类时，会执行该方法
     * 返回值:
     * EVAL_BODY_INCLUDE 表示会包含标签体中的内容
     * SKIP_BODY         表示不会包含标签体中的内容
     */
    public int doStartTag() throws JspException {
        // 获取到传进来的权限点
        // 判断当前用户是否有该权限点
        // 从session中获取拉拉的接口对象
        // 然后把权限点放入接口对象中
        // 根据接口对象返回的boolean值，就可以判断，该用户是否有看该权限点的权限。
        HttpSession session = this.pageContext.getSession();
        if (null == session) {
//            logger.error(ValidateStatusEnum.SESSIONISNULL_EXCEPTION_CODE,
//                    "鉴权时获取session为空，导致鉴权失败，请您重新登录，若再次登录时仍出现同样问题，请联系管理员！");

            logger.error(CommonStatusCode.COMMON_BUSINESS_EXCEPTION,
                 "鉴权时获取session为空，导致鉴权失败，请您重新登录，若再次登录时仍出现同样问题，请联系管理员！");
        }
        IAuthenticater authenticater = (IAuthenticater) session
                .getAttribute(SessionKeys.authenticater.toString());
        if (null == authenticater) {
//            logger.error(ValidateStatusEnum.AUTHENTICATERISNULL_EXCEPTION_CODE,
//            "鉴权时获取session中的authenticater为空，导致鉴权失败，请您重新登录，若再次登录时仍出现同样问题，请联系管理员！");
            logger.error(CommonStatusCode.COMMON_BUSINESS_EXCEPTION,
                  "鉴权时获取session中的authenticater为空，导致鉴权失败，请您重新登录，若再次登录时仍出现同样问题，请联系管理员！");
        }

        if (logger.isDebugEnable()) {
            logger.debug("session = " + session);
            logger.debug("authenticater = " + authenticater);
        }

        // 有权限，就显示包含的内容
        if (authenticater.authenticateUrl(authid)) {
            return EVAL_BODY_INCLUDE;
        }
        // 没有权限，就不显示包含的内容
        return SKIP_BODY;
    }

    public String getAuthid() {
        return authid;
    }

    public void setAuthid(String authid) {
        this.authid = authid;
    }

}
