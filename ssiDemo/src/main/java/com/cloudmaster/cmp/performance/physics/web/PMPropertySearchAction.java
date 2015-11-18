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
package com.cloudmaster.cmp.performance.physics.web;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.physics.dao.PMResourceInfoNew;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 物理机性能Tab页
 * @author <a href="mailto:wang-haibin@neusoft.com"> wang-haibin </a>
 * @version 1.0.0 18 Mar 2012
 */
public class PMPropertySearchAction extends OperationLogAction implements IAuthIdGetter {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -986344722813126856L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(PMPropertySearchAction.class);

    /**
     * 物理机性能数据集合
     */
    private List < PMResourceInfoNew > pmResInfos;
    
    /**
     * 下拉菜单发生变化后 传递过来的 数字  最近一周-0；最近一个月-1；最近三个月-2；
     */
    private String optionTime = "";

    @SuppressWarnings({ "unchecked", "deprecation" })
    public String searchResource() {
        logger.info(getText("function.title") + getText("pm.resource.history.in"));
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            String ip = request.getParameter("deviceIp");
            pw = response.getWriter();
            
            //当前时间7天前至今时间
            Calendar beginTime = Calendar.getInstance();
            String ibatisStr = "";
            PMResourceInfoNew pmrin = new PMResourceInfoNew();
            if ("0".equals(optionTime)) {
                //设置起始时间
                beginTime.add(Calendar.DATE, -7);
                pmrin.setPer_hid(ip);
                pmrin.setPer_time(beginTime.getTime());
                //设置执行不同的sql语句
                ibatisStr = "PMRes.getWeekResource";
               
            } else if ("1".equals(optionTime)){
                beginTime.add(Calendar.MONTH, -1);
                pmrin.setPer_hid(ip);
                pmrin.setPer_time(beginTime.getTime());
                ibatisStr = "PMRes.getMouthResource";
            } else {
                beginTime.add(Calendar.MONTH, -3);
                pmrin.setPer_hid(ip);
                pmrin.setPer_time(beginTime.getTime());
                ibatisStr = "PMRes.getMouthResource";
            }
            pmResInfos = (List < PMResourceInfoNew >)ibatisDAO.getData (
                    ibatisStr, pmrin);
            
            
            //为了调整有无数据，以及不同机器之间数据时间段情况的不同，将时间段修改为统一时间段
            voidDataHandle();
            
            //解析集合，拼为JSONArray数组
            JSONArray jsonArray = new JSONArray();
            for (int z = 0; z < pmResInfos.size(); z++) {
                jsonArray.add(pmResInfos.get(z));
            }
            pw.write(jsonArray.toString());
            logger.info(getText("function.title") + getText("pm.resource.history.out"));
            operationInfo =  getText("pm.resource.history.success");
         } catch (Exception e) {
                pw.write("noResult");
                logger.error(getText("function.title")
                        + getText("pm.resource.history.error"), e);
                operationInfo =  getText("pm.resource.history.error");
        } finally {
            try {
                pw.close();
            } catch (Exception e) {
            }
        }
        return null;
    }
    
    private void voidDataHandle() {
        Calendar now = Calendar.getInstance();
        Calendar weekAgo = Calendar.getInstance();
        weekAgo.add(Calendar.DATE, -7);
        Calendar monthAgo = Calendar.getInstance();
        monthAgo.add(Calendar.MONTH, -1);
        Calendar threeMonthAgo = Calendar.getInstance();
        threeMonthAgo.add(Calendar.MONTH, -3);
        Calendar beginTime;
        if ("0".equals(optionTime)) {
            beginTime = weekAgo;
        } else if ("1".equals(optionTime)){
            beginTime = monthAgo;
        } else {
            beginTime = threeMonthAgo;
        }
        
        //设置时间段
        PMResourceInfoNew prn = new PMResourceInfoNew();
        prn.setPer_time(beginTime.getTime());
        pmResInfos.add(0,prn);
        PMResourceInfoNew prn1 = new PMResourceInfoNew();
        prn1.setPer_time(now.getTime());
        pmResInfos.add(prn1);
    }
    public String getOptionTime() {
        return optionTime;
    }
    public void setOptionTime(String optionTime) {
        this.optionTime = optionTime;
    }
}
