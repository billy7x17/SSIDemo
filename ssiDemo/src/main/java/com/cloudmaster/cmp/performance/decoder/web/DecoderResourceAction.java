package com.cloudmaster.cmp.performance.decoder.web;

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
import com.greenpineyu.fel.common.StringUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * D4
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-9-11
 */
@SuppressWarnings({"deprecation","unchecked"})
public class DecoderResourceAction extends OperationLogAction implements IAuthIdGetter{

    /**
     * 标识.
     */
    private static final long serialVersionUID = -5195649059370061997L;
    
    /**
     * logger.
     */
    private static LogService logger = LogService.getLogger(DecoderResourceAction.class);
    
    /**
     * 命名空间.
     */
    private static final String NAMESPACE = "decoder.";
    
    /**
     * 
     */
    private List<PerfInfoDomain> perfList;
    
    private List<PerfInfoDomain> perfHistoryList;
    /**
     * 历史周期.
     */
    private String optionTime;
    
    /**
     * 定时刷新页面时间，单位：毫秒.
     */
    private int freshTime;
    
    /**
     * 跳转页面.
     * @return
     */
    public String init(){
        PerformaceDateUtil time = new PerformaceDateUtil(ibatisDAO);
        freshTime = time.getCollectIntevalTime()*1000;
        return SUCCESS;
    }
    
    /**
     * tab数据项.
     */
    public void getDecoderConfTab(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            String typeId = request.getParameter("typeId");
            PrintWriter pw = response.getWriter();
            if(!StringUtils.isEmpty("typeId")){
                List<String> mibList = (List<String>)ibatisDAO.getData(NAMESPACE+"getConfTab", typeId);
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
            operationInfo = getText("decoder.resource.getAlreadyConfTab.success");
          }
        } catch (Exception e) {
            logger.error(getText("decoder.resource.getAlreadyConfTab.exception"), e);
            operationInfo = getText("decoder.resource.getAlreadyConfTab.exception");
        }
    }
    
    /**
     * 指标项值.
     */
    public void getDecoderConfTabIndex(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String typeId = request.getParameter("typeId");
            if(!StringUtils.isEmpty(typeId)){
               List<MibinfoDomain> mibList = ibatisDAO.getData(NAMESPACE+"getConfTabIndex", typeId);
               if(!mibList.isEmpty()){
                   JSONArray jsonArray = new JSONArray();
                   for(int i=0;i<mibList.size();i++){
                       jsonArray.add(mibList.get(i));
                   }
                   pw.write(jsonArray.toString());
               }
               pw.flush();
               pw.close();
               operationInfo = getText("decoder.resource.getAlreadyConfTabIndex.success");
            }
        } catch (Exception e) {
            logger.error(getText("decoder.resource.getAlreadyConfTabIndex.exception"), e);
            operationInfo = getText("decoder.resource.getAlreadyConfTabIndex.exception");
        }
    }
    
    /**
     * 性能数据.
     */
    public void searchDecoderResource(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String deviceIp = request.getParameter("deviceIp");
            String typeId = request.getParameter("typeId");
           
            PerfInfoDomain perf = new PerfInfoDomain();
            perf.setAgentIp(deviceIp);
            perf.setMibType(typeId);
            perf.setShowTime(getStartTime());
            perfList = ibatisDAO.getData(NAMESPACE+"getPerfLineResource", perf);
            setStartAndEndTime();
            List<PerfInfoDomain> tableList = ibatisDAO.getData(NAMESPACE+"getPerfResource", perf);
            perfList.addAll(tableList);
            if(!perfList.isEmpty()){
                JSONArray json = new JSONArray();
                for(int i=0;i<perfList.size();i++){
                    json.add(perfList.get(i));
                }
                pw.write(json.toString());
            }
            pw.flush();
            pw.close();
            
            operationInfo = getText("decoder.resource.getSearchData.success");
        } catch (Exception e) {
            logger.error(getText("decoder.resource.getSearchData.error"), e);
            operationInfo = getText("decoder.resource.getSearchData.error");
        }
    }
    
    
    /**
     * 历史性能.
     */
    public void searchHistoryResource(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            PrintWriter pw = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            String deviceIp = request.getParameter("deviceIp");
            String typeId = request.getParameter("typeId");
            PerfInfoDomain perf = new PerfInfoDomain();
            Calendar time = Calendar.getInstance();
            perf.setAgentIp(deviceIp);
            perf.setMibType(typeId);
            String ibatisStr ="";
            if("0".equals(optionTime)){
                time.add(Calendar.DATE, -7);
                perf.setShowTime(time.getTime());
                ibatisStr = "getWeekResouceData";
            }else if("1".equals(optionTime)){
                time.add(Calendar.MONTH, -1);
                perf.setShowTime(time.getTime());
                ibatisStr = "getMonthResouceData";
            }else if("2".equals(optionTime)){
                time.add(Calendar.MONTH, -3);
                perf.setShowTime(time.getTime());
                ibatisStr = "getMonthResouceData";
            }
            perfHistoryList = ibatisDAO.getData(NAMESPACE+ibatisStr, perf);
            voidDataHandle();
            JSONArray array = new JSONArray();
            for(PerfInfoDomain pInfo:perfHistoryList){
                array.add(pInfo);
            }
            pw.write(array.toString());
            pw.flush();
            pw.close();
            operationInfo = getText("decoder.resource.historyData.success");
        } catch (Exception e) {
            logger.error(getText("decoder.resource.historyData.error"), e);
            operationInfo = getText("decoder.resource.historyData.error");
        }
    }
    
    /**
     * 获得性能数据增量.
     */
    public void searchDecoderResourceIncrease(){
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
            List<PerfInfoDomain> perfS = ibatisDAO.getData(NAMESPACE+"getIncreaseResource", perf);
            JSONArray jsonArray = new JSONArray();
            for(PerfInfoDomain perfInfo : perfS){
                jsonArray.add(perfInfo);
            }
            pw.write(jsonArray.toString());
            pw.flush();
            pw.close();
            operationInfo = getText("decoder.resource.searchIncrease.success");
        } catch (Exception e) {
            logger.error(getText("decoder.resource.searchIncrease.error"), e);
            operationInfo = getText("decoder.resource.searchIncrease.error");
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
     * 在时间轴上增加气势和结束时间点
     */
    private void setStartAndEndTime() {
        Calendar now = Calendar.getInstance();
        PerfInfoDomain prn = new PerfInfoDomain();
        if (!perfList.isEmpty()) {
            if (!PerformaceDateUtil.formaterDateToString(perfList.get(0).getShowTime()).equals(
                    PerformaceDateUtil.formaterDateToString(getStartTime()))) {
                prn.setShowTime(getStartTime());
                perfList.add(0, prn);
            }
        } else {
            prn.setShowTime(getStartTime());
            perfList.add(0, prn);
            PerfInfoDomain prn1 = new PerfInfoDomain();
            prn1.setShowTime(now.getTime());
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
        perfHistoryList.add(0,prn);
        PerfInfoDomain prn1 = new PerfInfoDomain();
        prn1.setShowTime(now.getTime());
        perfHistoryList.add(prn1);
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
