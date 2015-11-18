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

import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cloudmaster.cmp.authority.auth.Authenticater;
import com.cloudmaster.cmp.authority.auth.SessionKeys;
import com.cloudmaster.cmp.operationlog.IOperationLogDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.util.DES3;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

//import com.opensymphony.xwork2.ActionContext;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class LoginAction extends OperationLogAction {

    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(LoginAction.class);

    private static final String RET_FAILURE = "FAILURE";

    private static final String RET_INVALID = "INVALID";

    private String userId;

    private String password;

    private String invalid;

    private DES3 des3;

    // license，限制使用的最后时间
    private String licenseEndTime;

    // license，获取当前时间的URL
    private String netTimeURL;

    // 注入插入操作日志的DAO
    private transient IOperationLogDAO operationLogDAO;

    public void setOperationLogDAO(IOperationLogDAO operationLogDAO) {
        this.operationLogDAO = operationLogDAO;
    }

    /**
     * 用户登录时,首先判断用户是否可用，然后判断id和password是否正确；如果不正确，则提示错误并返回登录页面，反之，
     * 从数据库中读取用户的所有的权限点，uri与权限点对应关系，以及报表ID与权限点对应关系，组成鉴权对象，放入session中。
     * @return String
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String execute() {

        if (!licenseValidator()) {
            invalid = "您的证书已到期，请联系管理员更换证书！";
            return RET_INVALID;
        }

        if (null == getUserId().trim() || null == getPassword()) {
            return RET_FAILURE;
        }
        UserInfo userInfo = new UserInfo();
        String id = getUserId().trim();
        userInfo.setUserId(id);

        // if (userInfo == null) {
        // return RET_FAILURE;
        // }

        // 从数据库中读取与用户输入的用户ID对应的用户信息
        try {
            userInfo = (UserInfo) ibatisDAO.getSingleRecord("userInfo.getSingleUser", id);

        } catch (Exception e) {
            String errmsg = MessageFormat.format(getText("read.error"), id);
            this.addActionError(errmsg);
            invalid = errmsg;
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errmsg + e.getMessage(), e);
            return RET_FAILURE;
        }

        // 若当前用户已被禁用，或者用户输入的用户ID或密码错误，则在登录页面进行提示
        if (null != userInfo) {
            // logger.info("解密："+(des3.decrypt(userInfo.getPassword())));
            if (!userInfo.getStatus().equals("0")) {
                invalid = getText("user.cannot.login");
                operationInfo = MessageFormat.format(getText("user.login.fail"), id,
                        userInfo.getUserName())
                        + getText("user.invalid");
                return RET_INVALID;
            } else if (!getPassword().equals(des3.decrypt(userInfo.getPassword()))) {
                // des3算法解密数据库保存的密码
                // 用户输入的密码，与数据库中存储的用户密码进行对比
                invalid = getText("user.name.psw.error");
                operationInfo = MessageFormat.format(getText("user.login.fail"), id,
                        userInfo.getUserName())
                        + getText("user.error");
                return RET_INVALID;
            } else {
                // // 获得用户岗位信息
                // List<String> uerPositionList;
                // try {
                // uerPositionList = ibatisDAO.getData("userInfo.getUserPositionInfo", id);
                // userInfo.setPositionsInfo(uerPositionList);
                // String temp = (String) ibatisDAO
                // .getSingleRecord("userInfo.getVirtualLevel", id);
                // if (null == temp || "".equalsIgnoreCase(temp)) {
                // temp = (String) ibatisDAO.getSingleRecord("userInfo.getTitlesJustFornAME",
                // null);
                // }
                // userInfo.setNextVirtualLevel(temp);
                // } catch (Exception ex) {
                // String errmsg = MessageFormat.format(getText("user.getVirtualLevel.error"), id);
                // logger.error(LoginStatusCode.LOGIN_EXCEPTION, errmsg + ex.getMessage(), ex);
                // return RET_INVALID;
                // }

                // 用户ID和密码都正确时，将当前用户存入session中，以便页面显示用
                ActionContext.getContext().getSession()
                        .put(SessionKeys.userInfo.toString(), userInfo);
                operationInfo = MessageFormat.format(getText("user.login.success"), id,
                        userInfo.getUserName());

            }
        } else {
            invalid = getText("user.not.exist");
            operationInfo = MessageFormat.format(getText("user.login.fail"), id, id)
                    + getText("currentuser.not.exist");
            return RET_INVALID;
        }

        // 获取设计平台的所有报表
        // List<String> authReportList;
        // try {
        // authReportList = ibatisDAO.getData("userInfo.getAuthReport", null);
        // } catch (Exception e) {
        // String msg = getText("read.all.authority.exception");
        // logger.error(LoginStatusCode.LOGIN_EXCEPTION, msg + e.getMessage(), e);
        // return RET_INVALID;
        // }

        // 报表ID与权限点对应关系的map
        // Map<String, String> authReportMap = new HashMap<String, String>();
        // for (AuthReport authReport : authReportList) {
        // authReportMap.put(authReport.getReportId(), authReport.getAuthId());
        // }

        // 获取用户对应的权限点
        List<String> authIdList;
        try {
            authIdList = ibatisDAO.getData("userInfo.getUserAuth", id);

        } catch (Exception ex) {
            String errmsg = MessageFormat.format(getText("read.user.auth.error"), id);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errmsg + ex.getMessage(), ex);
            return RET_INVALID;
        }
        // 判断用户是否有可用角色
        if (authIdList.size() == 0) {
            invalid = getText("user.auth.not.exist");
            return RET_INVALID;
        } else {
            // 将对报表进行鉴权的对象放入session中
            Authenticater authenticater = new Authenticater();
            // authenticater.setAuthReportMap(authReportMap);
            authenticater.setAuthIdList(authIdList);

            ActionContext.getContext().getSession()
                    .put(SessionKeys.authenticater.toString(), authenticater);

            if (logger.isInfoEnable()) {
                logger.info(
                        LoginStatusCode.LOGIN_SUCCESS,
                        MessageFormat.format(getText("user.login.success"), userId,
                                userInfo.getUserName()));
            }

            // 将收集操作日志的DAO放入session中，以便在FILTER中可以对操作日志入库
            ActionContext.getContext().getSession()
                    .put(SessionKeys.operationLogDAO.toString(), operationLogDAO);

            return "SUCCESS";
        }
    }

    private boolean licenseValidator() {
        boolean isOk = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        String sysDate = formatter.format(new Date());

        String netDate = null;
        try {
            URL url = new URL(netTimeURL);// 取得资源对象，http://www.bjtime.cn
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.setConnectTimeout(1000 * 2);
            uc.setReadTimeout(1000 * 2);
            uc.connect(); // 发出连接
            long ld = uc.getDate(); // 取得网站日期时间
            Date date = new Date(ld); // 转换为标准时间对象
            netDate = formatter.format(date);
        } catch (Exception e) {
            logger.info("不能通过网络获取时间");
        }

        if (null != netDate && netDate.compareTo(sysDate) > 0) {
            sysDate = netDate;
        }

        String licenseDate = des3.decrypt(licenseEndTime);
        if (sysDate.compareTo(licenseDate) < 0) {
            isOk = true;
        }
        return isOk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public DES3 getDes3() {
        return des3;
    }

    public void setDes3(DES3 des3) {
        this.des3 = des3;
    }

    public String getLicenseEndTime() {
        return licenseEndTime;
    }

    public void setLicenseEndTime(String licenseEndTime) {
        this.licenseEndTime = licenseEndTime;
    }

    public String getNetTimeURL() {
        return netTimeURL;
    }

    public void setNetTimeURL(String netTimeURL) {
        this.netTimeURL = netTimeURL;
    }

}
