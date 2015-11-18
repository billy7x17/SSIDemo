package com.cloudmaster.cmp.performance.encoder.web;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.ipsan.dao.PerfInfoDomain;
import com.cloudmaster.cmp.performance.mibinfo.dao.MibinfoDomain;
import com.cloudmaster.cmp.performance.util.PerformaceDateUtil;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.greenpineyu.fel.common.StringUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * Encoder同mib的性能管理
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-8-22
 */
@SuppressWarnings({"deprecation","unchecked"})
public class EncoderResourceAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 标识.
     */
    private static final long serialVersionUID = -3042954730412705341L;

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(EncoderResourceAction.class);

    /**
     * 命名空间.
     */
    private static final String IBATIS_NAME = "EncoderRes.";
    
    /**
     * 
     */
    private List<PerfInfoDomain> perfList;

    /**
     * 时间间隔值.
     */
    private String optionTime;
    
    /**
     * 定时刷新时间,单位：毫秒.
     */
    private int freshTime;
    
    /**
     * 页面跳转.
     * @return
     */
    public String init() {
        PerformaceDateUtil time = new PerformaceDateUtil(ibatisDAO);
        freshTime = time.getCollectIntevalTime()*1000;
        return SUCCESS;
    }
    
    /**
     * 获得设备指标项.
     */
    public void getEncoderAlreadyConfTab(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            String typeId = request.getParameter("typeId");
            PrintWriter pw = response.getWriter();
            if(!StringUtils.isEmpty("typeId")){
                List<String> mibList = (List<String>)ibatisDAO.getData(IBATIS_NAME+"getConfTab", typeId);
                StringBuffer confTab = new StringBuffer();
                if (!mibList.isEmpty()) {
                    for (int i = 0; i < mibList.size(); i++) {
                        if (i == 0) {
                            confTab.append(mibList.get(i));
                        } else {
                            confTab.append(";" + mibList.get(i));
                        }
                    }
                }
                pw.write(confTab.toString());
                pw.flush();
                pw.close();
                operationInfo = getText("performance.encoder.confTab.success");
            }
        } catch (Exception e) {
            logger.error(getText("performance.encoder.confTab.error"), e);
            operationInfo = getText("performance.encoder.confTab.error");
        }
    }
    
    /**
     * 性能指标具体指.
     */
    public void getEncoderConfTabIndex(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String typeId = request.getParameter("typeId");
            if(!StringUtils.isEmpty(typeId)){
               List<MibinfoDomain> mibList = ibatisDAO.getData(IBATIS_NAME+"getConfTabIndex", typeId);
               if(!mibList.isEmpty()){
                   JSONArray jsonArray = new JSONArray();
                   for(int i=0;i<mibList.size();i++){
                       jsonArray.add(mibList.get(i));
                   }
                   pw.write(jsonArray.toString());
               }
               pw.flush();
               pw.close();
               operationInfo = getText("performance.encoder.confTabIndex.success");
            }
        } catch (Exception e) {
            logger.error(getText("performance.encoder.confTabIndex.error"), e);
            operationInfo = getText("performance.encoder.confTabIndex.error");
        }
    }
    
    public void searchEncoderResource(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String deviceIp = request.getParameter("deviceIp");
            String typeId = request.getParameter("typeId");
            DeviceDomain device = new DeviceDomain();
            device.setAgentIp(deviceIp);
            device.setAgentFrequency(new PerformaceDateUtil().getFrequency());
              
            PerfInfoDomain perf = new PerfInfoDomain();
            perf.setAgentIp(deviceIp);
            perf.setMibType(typeId);
            perf.setShowTime(getStartTime());
            perfList = ibatisDAO.getData(IBATIS_NAME+"getLineResource", perf);
            setStartAndEndTime();
            List<PerfInfoDomain> tableList = ibatisDAO.getData(IBATIS_NAME+"getPerfResource", perf);
            perfList.addAll(tableList);
            
           if(!perfList.isEmpty()){
               JSONArray json = new JSONArray();
               for(int i=0;i<perfList.size();i++){
                   json.add(perfList.get(i));
               }
               pw.write(json.toString());
               pw.flush();
               pw.close();
           }
           operationInfo = getText("performance.encoder.performanceData.success");
        } catch (Exception e) {
            logger.error(getText("performance.encoder.performanceData.error"), e);
            operationInfo = getText("performance.encoder.performanceData.error");
        }
    }
    
    /**
     * 获得起始时间.
     * @return
     */
    private Date getStartTime(){
        PerformaceDateUtil performaceDate = new PerformaceDateUtil(ibatisDAO);
        return performaceDate.getStartTime();
    }
    
        /**
         * 历史数据.
         */
       public void searchEncoderProperty(){
           try {
               HttpServletRequest request = ServletActionContext.getRequest();
               HttpServletResponse response = ServletActionContext.getResponse();
               response.setCharacterEncoding("UTF-8");
               PrintWriter pw = response.getWriter();
               Calendar beginTime = Calendar.getInstance();
               PerfInfoDomain perf = new PerfInfoDomain();
               perf.setMibType(request.getParameter("typeId"));
               perf.setAgentIp(request.getParameter("deviceIp"));
               String ibatisStr = "";
               if ("0".equals(optionTime)) {// 一周
                   beginTime.add(Calendar.DATE,-7);
                   perf.setShowTime(beginTime.getTime());
                   ibatisStr = "getWeekResouceData";
               } else if ("1".equals(optionTime)) {//一月
                   beginTime.add(Calendar.MONTH, -1);
                   perf.setShowTime(beginTime.getTime());
                   ibatisStr = "getMonthResouceData";
               } else {//三月
                   beginTime.add(Calendar.MONTH, -3);
                   perf.setShowTime(beginTime.getTime());
                   ibatisStr = "getMonthResouceData";
               }
               perfList =(List<PerfInfoDomain>)ibatisDAO.getData(IBATIS_NAME+ibatisStr, perf);
               
               voidDataHandle();
               
               JSONArray array = new JSONArray();
               for(PerfInfoDomain pInfo:perfList){
                   array.add(pInfo);
               }
               pw.write(array.toString());
               operationInfo = getText("encoder.resource.historyData.success");
               pw.flush();
               pw.close();
           } catch (Exception e) {
               operationInfo = getText("encoder.resource.historyData.error");
               logger.error(getText("encoder.resource.historyData.error"), e);
           }
       }
    
       /**
        * 增量
        */
       public void searchEncoderResourceIncrease(){
            try {
                HttpServletRequest request = ServletActionContext.getRequest();
                HttpServletResponse response = ServletActionContext.getResponse();
                PrintWriter pw = response.getWriter();
                response.setCharacterEncoding("UTF-8");
                String typeId = request.getParameter("typeId");
                String deviceIp = request.getParameter("deviceIp");
                PerformaceDateUtil intevaltime = new PerformaceDateUtil(ibatisDAO);
                PerfInfoDomain perf = new PerfInfoDomain();
                perf.setTime(intevaltime.getCollectIntevalTime());
                perf.setAgentIp(deviceIp);
                perf.setMibType(typeId);
                List<PerfInfoDomain> perfS = ibatisDAO.getData(IBATIS_NAME+"getIncreaseResource", perf);
                JSONArray jsonArray = new JSONArray();
                for(PerfInfoDomain perfInfo : perfS){
                    jsonArray.add(perfInfo);
                }
                pw.write(jsonArray.toString());
                pw.flush();
                pw.close();
                operationInfo = getText("encoder.resource.searchIncrease.error");
            } catch (Exception e) {
                logger.error(getText("encoder.resource.searchIncrease.success"), e);
                operationInfo = getText("encoder.resource.searchIncrease.success");
            }
       }    
       
       
       
    /**
     * 在时间轴上增加气势和结束时间点
     */
    private void setStartAndEndTime() {
        Calendar now = Calendar.getInstance();
        PerfInfoDomain prn = new PerfInfoDomain();
        if (!perfList.isEmpty()) {
            if (!PerformaceDateUtil.formaterDateToString(perfList.get(0).getShowTime()).equals(
                    PerformaceDateUtil.formaterDateToString(getStartTime()))) {
                prn.setShowTime(getStartTime());
                prn.setIfShowLine("1");
                perfList.add(0, prn);
            }
        } else {
            prn.setShowTime(getStartTime());
            prn.setIfShowLine("1");
            perfList.add(0, prn);
            PerfInfoDomain prn1 = new PerfInfoDomain();
            prn1.setShowTime(now.getTime());
            prn1.setIfShowLine("1");
            perfList.add(prn1);
        }

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
        PerfInfoDomain prn = new PerfInfoDomain();
        prn.setShowTime(beginTime.getTime());
        perfList.add(0,prn);
        PerfInfoDomain prn1 = new PerfInfoDomain();
        prn1.setShowTime(now.getTime());
        perfList.add(prn1);
    }
    
    /**
     * @return the optionTime
     */
    public String getOptionTime() {
        return optionTime;
    }

    /**
     * @param optionTime the optionTime to set
     */
    public void setOptionTime(String optionTime) {
        this.optionTime = optionTime;
    }

    /**
     * @return the freshTime
     */
    public int getFreshTime() {
        return freshTime;
    }

    /**
     * @param freshTime the freshTime to set
     */
    public void setFreshTime(int freshTime) {
        this.freshTime = freshTime;
    }
 
}
