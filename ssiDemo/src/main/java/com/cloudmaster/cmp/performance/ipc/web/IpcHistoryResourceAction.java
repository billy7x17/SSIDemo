package com.cloudmaster.cmp.performance.ipc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.performance.ipsan.dao.PerfInfoDomain;
import com.cloudmaster.cmp.performance.util.PerformaceDateUtil;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 折线图历史性能数据.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-8-21
 */
@SuppressWarnings({"deprecation","unchecked"})
public class IpcHistoryResourceAction extends OperationLogAction implements IAuthIdGetter{

    /**
     * 标识.
     */
    private static final long serialVersionUID = -11751744850855833L;
    
    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(IpcHistoryResourceAction.class);
    
    /**
     * 命名空间.
     */
    private static final String IBATIS_NAME = "IpcInfo.";
    
    /**
     * 时间间隔值.
     */
    private String optionTime;
    
    /**
     * 设备Ip.
     */
    private String deviceIp;
    
    /**
     * 设备类型.
     */
    private String typeId;
    
    private List<PerfInfoDomain> perfList;
    
    public void searchHistoryResource(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            Calendar beginTime = Calendar.getInstance();
            PerfInfoDomain perf = new PerfInfoDomain();
            perf.setMibType(typeId);
            perf.setAgentIp(deviceIp);
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
            operationInfo = getText("ipc.resource.historyData.success");
            pw.flush();
            pw.close();
        } catch (Exception e) {
            operationInfo = getText("ipc.resource.historyData.error");
            logger.error(getText("ipc.resource.historyData.error"), e);
        }
    }

    /**
     * 增量.
     */
    public void searchIPCResourceIncrease(){
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
            operationInfo = getText("ipc.resource.searchIncrease.success");
        } catch (Exception e) {
            logger.error(getText("ipc.resource.searchIncrease.error"), e);
            operationInfo = getText("ipc.resource.searchIncrease.error");
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
     * 读取ganglia采集频率
     */
    public String getFrequency() {
        Properties prop = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        String agentFrequency = "";
        try {
            prop.load(classLoader.getResourceAsStream("conf/other/System.properties"));
            agentFrequency = prop.getProperty("agentFrequency");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agentFrequency;
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
     * @return the deviceIp
     */
    public String getDeviceIp() {
        return deviceIp;
    }

    /**
     * @param deviceIp the deviceIp to set
     */
    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    /**
     * @return the typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}
