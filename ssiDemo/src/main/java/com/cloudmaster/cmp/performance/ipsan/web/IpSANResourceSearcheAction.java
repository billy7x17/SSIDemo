package com.cloudmaster.cmp.performance.ipsan.web;

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
import com.cloudmaster.cmp.performance.util.PerformaceDateUtil;
import com.greenpineyu.fel.common.StringUtils;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * IPSAN性能管理.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-6-25
 */
public class IpSANResourceSearcheAction extends OperationLogAction implements IAuthIdGetter{

    /**
     * 标识.
     */
    private static final long serialVersionUID = 1665675933481645977L;
    
    /**
     * logger.
     */
    private static final LogService logger = LogService.getLogger(IpSANResourceSearcheAction.class);
    
    /**
     * 命名空间.
     */
    private static final String IBATIC_NAMESPACE = "IpSanInfo";
    
    /**
     * 设备IP.
     */
    private String deviceIp;
    
    /**
     * 设备类型.
     */
    private String deviceType;
    
    /**
     * 定时刷新页面,单位:毫秒
     */
    private int freshTime;
    
    public String searchIpSANResource(){
        PerformaceDateUtil time = new PerformaceDateUtil(ibatisDAO);
        freshTime = time.getCollectIntevalTime()*1000;
        return SUCCESS;
    }
    
    /**
     * 查询IPSAN配置页信息.
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    public void getAlreadyConfTabIPSAN(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String typeId = request.getParameter("typeId");
            if(!StringUtils.isEmpty(typeId)){
               List<String> mibList = ibatisDAO.getData(IBATIC_NAMESPACE+".findMibInfo", typeId);
              //判断查询的tab不为空，则将tab返回到页面
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
                out.write(confTab.toString());
                out.flush();
                out.close();
                logger.info(getText("function.title") + getText("ipsan.resource.getAlreadyConfTab.success"));
                operationInfo = getText("ipsan.resource.getAlreadyConfTab.success");
            }
        } catch (Exception e) {
           logger.error(getText("function.title")
                   + getText("ipsan.resource.getAlreadyConfTab.exception"), e);
           operationInfo = getText("ipsan.resource.getAlreadyConfTab.exception");
        }
    }
    
    /**
     *获取tab指标信息. 
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void getAlreadyConfTabIndexIPSAN(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
           
            PerfInfoDomain  perfInfo = new PerfInfoDomain();
            perfInfo.setAgentIp(deviceIp);
            perfInfo.setMibType(request.getParameter("typeId"));
            perfInfo.setShowTime(getStartTime());
            List<PerfInfoDomain> perfList = ibatisDAO.getData(IBATIC_NAMESPACE+".getResource", perfInfo);
            if(!perfList.isEmpty()){
                JSONArray json = new JSONArray();
                for(PerfInfoDomain p:perfList){
                    json.add(p);
                }
                out.write(json.toString());
            }
            out.flush();
            out.close();
            operationInfo = getText("ipsan.resource.getPerformanceData.success");
        } catch (Exception e) {
            operationInfo = getText("ipsan.resource.getPerformanceData.error");
            logger.error(operationInfo, e);
        }
    }  
    
    /**
     * 获得起始时间.
     * @return
     */
    private Date getStartTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.AM_PM, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
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
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
