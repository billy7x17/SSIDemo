/**
 * Copyright 2014 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.resource.collectdevice.service;

import java.io.PrintWriter;
import java.util.List;

import org.quartz.JobExecutionContext;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.neusoft.mid.enzyme.quzrtz.BaseJob;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 自动编辑nagios配置ping设备的文件
 * @author <a href="mailto:x_wang@neusoft.com"> wangxin </a>
 * @version 1.0.0 22 Sept. 2014
 */
public class AutoEditConfigJob extends BaseJob {

    private PrintWriter out;

    private static final String DEFINE = "define";

    /**
     * 日志前缀
     */
    private String logBegin = "AUTO EDIT CONFIG,";

    /**
     * log
     */
    private static LogService logger = LogService.getLogger("AutoEditConfigJob");

    /**
     * 数据库dao
     */
    private IbatisDAO ibatisDAO;

    /**
     * ibatis命名空间
     */
    private static final String IBATIS_NAMESPACE = "DeviceInfo.";

    /**
     * 文件路径
     */
    private String file;

    /**
     * nagios服务器IP
     */
    private String ip;

    /**
     * nagios服务器用户名
     */
    private String user;

    /**
     * nagios服务器密码
     */
    private String pwd;

    public AutoEditConfigJob() {

    }

    /**
     * 入口，自动编辑配置文件
     */
    @Override
    public void invoke(JobExecutionContext context) throws Exception {
        logger.info(logBegin + "start");
        String jobresult = "success";
        String joblog = "";

        autoEdit();
        
        context.setResult(jobresult);// context.setResult("success")表示任务执行成功；其他则失败
        if (joblog.equals("")) {
            context.put("log", "自动编辑文件结束");
        } else {
            context.put("log", joblog);
        }
        logger.info(logBegin + "end");
    }

    /**
     * 自动编辑文件
     */
    public void autoEdit() {
        try {
            this.out = new PrintWriter(file);
            // nagios配置文件分三部分，第一部分配置ping的设备；第二部分配置设备类型；第三部分是ping服务指令
            writeComment("localhost.cfg");
            // 第一部分
            // 获取数据库中所有设备信息
            List<DeviceDomain> nagiosDeviceList = ibatisDAO.getData(
                    IBATIS_NAMESPACE + "getDevices", null);
            // 定义设备信息
            writeComment("HOST DEFINITION");
            for (DeviceDomain temp : nagiosDeviceList) {
                startDefine("host");
                writeProperty("use", "linux-server");
                writeProperty("host_name", temp.getAgentIp());
                writeProperty("alias", temp.getAgentIp());
                writeProperty("address", temp.getAgentIp());
                writeProperty("hostgroups", temp.getTypeId());
                endDefine();
            }

            // 第二部分
            // 获取所有设备类型信息
            List<DeviceDomain> nagiosDeviceTypeList = ibatisDAO.getData(IBATIS_NAMESPACE
                    + "getGroupTypeList", null);
            // 定义设备类型信息
            writeComment("HOST GROUP DEFINITION");
            for (DeviceDomain temp : nagiosDeviceTypeList) {
                startDefine("hostgroup");
                writeProperty("hostgroup_name", temp.getTypeId());
                writeProperty("alias", temp.getTypeId());
                endDefine();
            }

            // 第三部分
            writeComment("SERVICE DEFINITIONS");
            startDefine("service");
            writeProperty("use", "local-service");
            writeProperty("host_name", "*");
            writeProperty("service_description", "PING");
            writeProperty("check_command", "check_ping!100.0,20%!500.0,60%");
            endDefine();

            autoReload();
        } catch (Exception e) {
            logger.info(logBegin + " 自动编辑nagios配置文件出现异常", e);
        } finally {
            close();
        }
    }

    /**
     * 自动加载nagios服务器配置
     */
    public void autoReload() {
        
        Connection conn = null;
        
        try {
            // 修改完文件，重启nagios服务
            conn = new Connection(ip);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(user, pwd);
            if(isAuthenticated){
                Session session = conn.openSession();
                session.execCommand("service nagios reload");
            }else{
                logger.info(logBegin + " 自动加载nagios服务器配置鉴权失败");
            }
        } catch (Exception e) {
            logger.info(logBegin + " 自动加载nagios服务器配置出现异常", e);
        } finally {
            if(null != conn)
                conn.close();
        }
    }

    /**
     * 描述
     */
    private void writeComment(String comment) {
        out.print('#');
        out.println(comment);
    }

    /**
     * 定义
     */
    private void startDefine(String name) {
        out.print(DEFINE);
        out.print(' ');
        out.print(name);
        out.println('{');
    }

    /**
     * 属性
     */
    private void writeProperty(String key, String value) {
        out.print(' ');
        out.print(key);
        out.print('\t');
        out.println(value);
    }

    /**
     * 结束符
     */
    private void endDefine() {
        out.println('}');
    }

    /**
     * 关闭
     */
    private void close() {
        out.close();
    }

    /**
     * @return Returns the ibatisDAO.
     */
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    /**
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    /**
     * @return Returns the file.
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file The file to set.
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return Returns the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return Returns the pwd.
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd The pwd to set.
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
