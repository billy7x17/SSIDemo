package com.cloudmaster.cmp.performance.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.system.systemparameter.dao.SystemParameterDomain;
import com.greenpineyu.fel.common.StringUtils;

/**
 * 展示性能采集数据的起始时间.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng</a>
 * @version 1.0.0 2014-9-9
 */
public class PerformaceDateUtil extends OperationLogAction{
    
    /**
     * 标识.
     */
    private static final long serialVersionUID = -7783026187109378345L;
    
    private IbatisDAO ibatisDAO;
    /**
     * 00:00表达式.
     */
    private static final String REGEX = "^[0-2]?[0-9]{1}:[0-5]{1}[0-9]{1}$";
    
    private static final String INTEVALTIME = "^[1-9]{1}[0-9]{0,}$";
    
    /**
     * @param ibatisDAO
     */
    public PerformaceDateUtil(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }
    public PerformaceDateUtil() {
   
    }
    
    /**
     * 计算性能展示数据起始时间.
     * @return
     */
    public  Date getStartTime(){
        Calendar cal = Calendar.getInstance();
        SystemParameterDomain param = getParamByKey();
        if(null != param){
            String startTime = param.getParamValue();
            if(!StringUtils.isEmpty(startTime)){
                if(startTime.matches(REGEX)){
                    String[] hour = startTime.split(":");
                    cal.set(Calendar.HOUR, Integer.parseInt(hour[0]));
                    cal.set(Calendar.MINUTE, Integer.parseInt(hour[1]));
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.AM_PM, 0);
                    return cal.getTime();
                }
            }
        }
        cal.set(Calendar.AM_PM, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
    
   
    /**
     * 获得性能系统参数数据.
     * @return
     */
    private  SystemParameterDomain getParamByKey(){
        SystemParameterDomain parameter = new SystemParameterDomain();
        try {
            parameter.setParamKey("collectStartTime");   
            parameter =(SystemParameterDomain)ibatisDAO.getSingleRecord("systemparameter.getServiceDomain", parameter);
        } catch (Exception e) {
              e.printStackTrace();
        }
        return parameter ;
    }

    /**
     * 获取采集间隔时间.默认10分钟.
     * @return
     */
    public int getCollectIntevalTime(){
        SystemParameterDomain parameter = new SystemParameterDomain();
        int intevalTime = 600;//默认10分钟
        try {
            parameter.setParamKey("intervalTime"); 
            parameter = (SystemParameterDomain)ibatisDAO.getSingleRecord("systemparameter.getServiceDomain", parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(parameter.getParamValue().matches(INTEVALTIME)){
            intevalTime = Integer.valueOf(parameter.getParamValue())*60;
        }
        return intevalTime;
    }
    
    /**
     * 格式化时间.
     * @param date
     * @return
     */
    public static String formaterDateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH0000");
        return sdf.format(date);
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

    
}
