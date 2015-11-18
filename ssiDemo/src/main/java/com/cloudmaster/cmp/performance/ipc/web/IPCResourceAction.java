package com.cloudmaster.cmp.performance.ipc.web;

import java.io.PrintWriter;
import java.util.ArrayList;
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
 * IPC设备性能.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-6-30
 */
public class IPCResourceAction extends OperationLogAction implements IAuthIdGetter {

    /**
     * 标识.
     */
    private static final long serialVersionUID = 5416898780496277708L;

    /**
     * logger.
     */
    private static final LogService logger = LogService.getLogger(IPCResourceAction.class);

    /**
     * IBATIC命名空间.
     */
    private static final String IBATIC_NAMESPACE = "IpcInfo";

    /**
     * 设备IP.
     */
    private String deviceIp;
    
    /**
     * 设备类型.
     */
    private String deviceType;
    
    /**
     * 性能集合.
     */
    private List<PerfInfoDomain> perList = new ArrayList<PerfInfoDomain>();
    
    /**
     * 定时刷新时间,单位：毫秒
     */
    private int freshTime;

    /**
     * 页面跳转.
     */
    public String init() {
        PerformaceDateUtil time = new PerformaceDateUtil(ibatisDAO);
        freshTime = time.getCollectIntevalTime()*1000;
        return SUCCESS;
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public void getAlreadyConfTabIpc() {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String typeId = request.getParameter("typeId");
            if (!StringUtils.isEmpty(typeId)) {
                List<String> mibList = ibatisDAO.getData(IBATIC_NAMESPACE + ".findMibInfo",
                        typeId);
                // 判断查询的tab不为空，则将tab返回到页面
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
                logger.info(getText("function.title")
                        + getText("ipc.resource.getAlreadyConfTab.success"));
                operationInfo =  getText("ipc.resource.getAlreadyConfTab.success");
            }
        } catch (Exception e) {
            logger.error(getText("function.title")
                    + getText("ipc.resource.getAlreadyConfTab.exception"), e);
            operationInfo =  getText("ipc.resource.getAlreadyConfTab.exception");
        }
    }
    
    /**
     * 查询 IPC tabIndex
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    public void getAlreadyConfTabIndexIPC(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            String typeId = request.getParameter("typeId");
            if(!StringUtils.isEmpty(typeId)){
                List<MibinfoDomain> mibList =ibatisDAO.getData(IBATIC_NAMESPACE+".getAlreadyConfTabIndexIpc", typeId);
                if(!mibList.isEmpty()){
                    JSONArray jsonArray = new JSONArray();
                    for(int i=0;i<mibList.size();i++){
                        jsonArray.add(mibList.get(i));
                    }
                    pw.write(jsonArray.toString());
                }
            }
            pw.flush();
            pw.close();
           operationInfo= getText("ipc.resource.getAlreadyConfTabIndex.success");
           
        } catch (Exception e) {
            logger.error(getText("function.title")
                    + getText("ipc.resource.getAlreadyConfTabIndex.exception"), e);
            operationInfo =  getText("ipc.resource.getAlreadyConfTabIndex.exception");
        }
    }

    /**
     * 查询IPC性能数据.
     */
    @SuppressWarnings({"unchecked", "deprecation" })
    public void searchIPCResource(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String typeId = request.getParameter("typeId");
                
            PerfInfoDomain perf = new PerfInfoDomain();
            perf.setAgentIp(deviceIp);
            PerformaceDateUtil perfD = new PerformaceDateUtil(ibatisDAO);
            perf.setShowTime(perfD.getStartTime());
            perf.setMibType(typeId);
            perList = ibatisDAO.getData(IBATIC_NAMESPACE+".searchIPCResource", perf);
            setStartAndEndTime();
            //解析集合，拼为JSONArray数组
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < perList.size(); i++) {
                jsonArray.add(perList.get(i));
            }
            out.write(jsonArray.toString());
            out.flush();
            out.close();  
            operationInfo = getText("ipc.resource.getSearchIPCData.success");
        } catch (Exception e) {
            operationInfo = getText("ipc.resource.getSearchIPCData.success");
            logger.error(operationInfo, e);
        }
    }
    
    /**
     * 设置起始和终止时间.
     */
    private void setStartAndEndTime() {
        Calendar now = Calendar.getInstance();
        PerfInfoDomain prn = new PerfInfoDomain();
        if (!perList.isEmpty()) {
            if (!PerformaceDateUtil.formaterDateToString(perList.get(0).getShowTime()).equals(
                    PerformaceDateUtil.formaterDateToString(getStartTime()))) {
                prn.setShowTime(getStartTime());
                perList.add(0, prn);
            }
        } else {
            prn.setShowTime(getStartTime());
            perList.add(0, prn);
            PerfInfoDomain prn1 = new PerfInfoDomain();
            prn1.setShowTime(now.getTime());
            perList.add(prn1);
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
     * @return the perList
     */
    public List<PerfInfoDomain> getPerList() {
        return perList;
    }

    /**
     * @param perList the perList to set
     */
    public void setPerList(List<PerfInfoDomain> perList) {
        this.perList = perList;
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
