package com.cloudmaster.cmp.web.authority.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.web.BaseAction;

/**
 * <b>Application describing: 用户校验邮箱Ajax</b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-9-15 下午2:33:55
 */
public class UserEmailVerify extends BaseAction {

    private static final long serialVersionUID = 5040157227389398191L;

    private final Logger logger = Logger.getLogger(UserEmailVerify.class);

    private String email;

    public void chechEmail() {

        ServletResponse resp = ServletActionContext.getResponse();

        PrintWriter pw = null;

        Integer count = new Integer(0);

        try {
            count = (Integer) ibatisDAO.getSingleRecord("UserInfo.getUserByEmail", email);
        } catch (SQLException e) {
            logger.error("校验用户邮箱时SQL查询异常", e);
            e.printStackTrace();
        }

        try {
            pw = resp.getWriter();

            pw.write(count.toString());

        } catch (IOException e) {
            logger.error("校验用户邮箱时，获取输出流异常", e);
            e.printStackTrace();
        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }

    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
