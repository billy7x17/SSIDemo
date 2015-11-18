package com.cloudmaster.cmp.topology.assFunction;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.web.BaseAction;

/**
 * <b>Application describing: 拓扑图设备信息更改Action </b> <br>
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li </a>
 * @version 1.0.0 2014-9-3 下午2:00:58
 */
public class UpdateDetail extends BaseAction {

    private static final long serialVersionUID = 3201409391919408171L;

    private static Logger logger = Logger.getLogger("UpdateDetail".getClass());

    private DeviceDomain deviceDomain;

    public void saveDetail() {

        logger.info("(拓扑)Ajax修改设备信息开始");

        int result = 0;

        try {
            result = ibatisDAO.updateData("TopoGraph.updateDetailByAjax", deviceDomain);
        } catch (SQLException e) {
            logger.error("(拓扑)Ajax更新设备信息时出错", e);
            e.printStackTrace();
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter pw = null;

        response.setCharacterEncoding("utf-8");
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.write(String.valueOf(result)); // 转换json数据格式 传递前台
        pw.flush();
        pw.close();

        logger.info("(拓扑)Ajax修改设备信息结束");
    }

    /**
     * @return the deviceDomain
     */
    public DeviceDomain getDeviceDomain() {
        return deviceDomain;
    }

    /**
     * @param deviceDomain the deviceDomain to set
     */
    public void setDeviceDomain(DeviceDomain deviceDomain) {
        this.deviceDomain = deviceDomain;
    }
}
