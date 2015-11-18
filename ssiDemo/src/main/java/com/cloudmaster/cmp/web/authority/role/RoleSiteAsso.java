package com.cloudmaster.cmp.web.authority.role;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * <b>Application describing: 创建用户，添加角色瞬间调用ajax 判断是否有中心管理员的角色</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-7-8 下午1:41:29
 */
public class RoleSiteAsso extends BaseAction {

    private static final long serialVersionUID = -8531385570253662020L;

    private static LogService logger = LogService.getLogger(RoleSiteAsso.class);

    private String roleId;

    public String execute() {

        Integer flag = 2; // 默认只有站点管理员角色类型

        try {
            if ("1".equals((String) ibatisDAO.getSingleRecord("RoleInfo.getRoleType", roleId))) {// 判断角色类型是否为中心管理员
                flag = 1;
            }

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("utf-8"); // 设置编码
            PrintWriter pw = response.getWriter();
            pw.write(flag.toString()); // 转换json数据格式 传递前台
            pw.flush();
            pw.close();

        } catch (Exception e) {
            logger.debug("获取角色类型异常", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
