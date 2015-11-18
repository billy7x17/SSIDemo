package com.cloudmaster.cmp.resource.collectdevice.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;

import com.cloudmaster.cmp.authority.auth.IAuthIdGetter;
import com.cloudmaster.cmp.operationlog.OperationLogAction;
import com.cloudmaster.cmp.resource.collectdevice.dao.DeviceDomain;
import com.cloudmaster.cmp.web.authority.user.UserInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * <p>
 * Description:设备批量导入
 * </p>
 * @author <a href="mailto:x_wang@neusoft.com">王欣 </a>
 * @version $Revision 1.0 $ 2014-8-12 下午01:01:21
 */

public class DeviceBatchInto extends OperationLogAction implements IAuthIdGetter {

    private static final long serialVersionUID = 1L;

    /**
     * log
     */
    private static LogService logger = LogService.getLogger(DeviceBatchInto.class);

    private int blankNum;

    private DeviceDomain deviceDomain = new DeviceDomain();

    private File batchfile; // 导入文件

    private List<DeviceDomain> deviceList = null;

    private Set<DeviceDomain> tempSet = new HashSet<DeviceDomain>();

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误行数
     */
    private int errorRow;

    private int totalCount;

    private final int maxSize = 1048576;

    private final int thousand = 1000;

    private final int three = 3;

    private final int four = 4;

    private final int five = 5;

    private final int six = 6;

    private final int seven = 7;

    private final int eight = 8;

    private final int nine = 9;

    private final int ten = 10;

    private final int eleven = 11;

    private final int twelve = 12;

    private final int fifteen = 15;

    private final int thirtyTwo = 32;

    private final int twoHundred = 200;

    private final int kb = 1024;

    /**
     * 设备类型
     */
    private List<DeviceDomain> typeList;

    /**
     * 站点列表
     */
    private List<DeviceDomain> siteList;

    /**
     * <p>
     * Description:页面上导出模板的action
     * </p>
     */
    public String exportTemplate() {
        try {
            // 模板文件路径
            String url = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            int last = url.toString().indexOf("WEB-INF");
            url = url.substring(five, last - 1) + File.separator + "excel" + File.separator;
            String path = URLDecoder.decode(url, "UTF-8");
            // 导出模板，中心管理员和站点管理员模板不同，站点管理员不需要站点ID列
            UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");
            String fileName = "template.xls";
            if ("2".equals(user.getRoleType())) {
                fileName = "template_site.xls";
            }
            export(path, fileName, user);
        } catch (Exception e) {
            logger.info(getText("info.export_fail"), e);
            errorMsg = getText("info.export_fail");
            return "error";
        }
        return null;
    }

    /**
     * <p>
     * Description:导出模板
     * </p>
     */
    public void export(String filePath, String fileName, UserInfo user) throws IOException,
            Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        File file = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            file = new File(filePath + fileName);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            response.setContentType("application/msexcel");// 定义输出类型

            out = new BufferedOutputStream(response.getOutputStream());
            in = new BufferedInputStream(new FileInputStream(file));

            // 导出文件内容
            downloadExcel(filePath + fileName, out, user);

            byte[] b = new byte[kb];
            int len;
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * <p>
     * Description:导出模板内Excel内容
     * </p>
     */
    @SuppressWarnings("unchecked")
    private void downloadExcel(String path, BufferedOutputStream out, UserInfo user)
            throws Exception {
        try {
            // 根据path将新的sheet页建在已有模板上
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));

            // 设备类型
            getGroupList();
            HSSFSheet sheetType = workbook.getSheetAt(1);
            // 将设备类型列表放在第二个sheet页中
            for (int i = 0; i < typeList.size(); i++) {
                deviceDomain = (DeviceDomain) typeList.get(i);
                this.setTypeData(workbook, sheetType.createRow(i + 1), deviceDomain);
            }

            // 系统管理员查看站点列表，站点管理员不需要，直接入该站点值
            if ("1".equals(user.getRoleType())) {
                // 站点列表
                getSiteInfoList();
                HSSFSheet sheetSite = workbook.getSheetAt(2);
                // 将站点列表放在第三个sheet页中
                for (int j = 0; j < siteList.size(); j++) {
                    deviceDomain = (DeviceDomain) siteList.get(j);
                    this.setSiteData(workbook, sheetSite.createRow(j + 1), deviceDomain);
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            throw new Exception(getText("info.export_fail"));
        }
    }

    /**
     * <p>
     * Description:删除sheet页中某列,暂时无用
     * </p>
     */
    public void removeColumn(HSSFSheet sheet, int removeColumnNum, int removeColumnTotal) {
        if (sheet == null) {
            return;
        }
        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext();) {
            HSSFRow row = (HSSFRow) rowIterator.next();
            HSSFCell cell = row.getCell(removeColumnNum);
            if (cell == null) {
                continue;
            }
            row.removeCell(cell);

            for (int n = removeColumnNum; n < (removeColumnTotal + removeColumnNum); n++) {
                int columnWidth = sheet.getColumnWidth(n + 1);

                HSSFCell cell2 = row.getCell(n + 1);

                if (cell2 == null) {
                    break;
                }
                sheet.setColumnWidth(n, columnWidth);
                row.moveCell(cell2, (short) n);
            }
        }
    }

    /**
     * 获取设备类型
     */
    private void getGroupList() throws SQLException {
        typeList = ibatisDAO.getData("DeviceInfo.getGroupTypeList", null);
        for (DeviceDomain temp : typeList) {
            if (("NVR").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.NVR"));
            } else if (("IP-SAN").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.IP-SAN"));
            } else if (("Encoder").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.Encoder"));
            } else if (("IPC").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.IPC"));
            } else if (("Switch").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.Switch"));
            } else if (("VMS").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.VMS"));
            } else if (("D4").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.D4"));
            } else if (("Keyboard").equals(temp.getAgentGroup())) {
                temp.setGroupName(getText("device.group.Keyboard"));
            }
        }
    }

    /**
     * 获取站点
     */
    private void getSiteInfoList() throws SQLException {
        deviceDomain.setLineNum("2");
        siteList = this.getIbatisDAO().getData("DeviceInfo.getSites", deviceDomain);
        for (DeviceDomain temp : siteList) {
            if (("1").equals(temp.getSiteType())) {
                temp.setSiteTypeName(getText("resource.site.typeName1"));
            } else if (("2").equals(temp.getSiteType())) {
                temp.setSiteTypeName(getText("resource.site.typeName2"));
            } else if (("3").equals(temp.getSiteType())) {
                temp.setSiteTypeName(getText("resource.site.typeName3"));
            } else if (("4").equals(temp.getSiteType())) {
                temp.setSiteTypeName(getText("resource.site.typeName4"));
            }
        }
    }

    /**
     * <p>
     * Description:设置第二个sheet页数据：设备类型对照表
     * </p>
     */
    private void setTypeData(HSSFWorkbook workbook, HSSFRow row, DeviceDomain deviceDomain) {
        HSSFCell cell = null;
        HSSFCellStyle textFormatStyle = workbook.createCellStyle();
        textFormatStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));

        /* 类型ID */
        cell = row.createCell(0);
        cell.setCellStyle(textFormatStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(deviceDomain.getTypeId()));

        /* 分组名称 */
        cell = row.createCell(1);
        cell.setCellStyle(textFormatStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(deviceDomain.getGroupName()));

        /* 类型名称 */
        cell = row.createCell(2);
        cell.setCellStyle(textFormatStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(deviceDomain.getTypeName()));
    }

    /**
     * <p>
     * Description:设置第三个sheet页数据：站点对照表
     * </p>
     */
    private void setSiteData(HSSFWorkbook workbook, HSSFRow row, DeviceDomain deviceDomain) {
        HSSFCell cell = null;
        HSSFCellStyle textFormatStyle = workbook.createCellStyle();
        textFormatStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));

        /* 站点ID */
        cell = row.createCell(0);
        cell.setCellStyle(textFormatStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(deviceDomain.getSiteId()));

        /* 站点名称 */
        cell = row.createCell(1);
        cell.setCellStyle(textFormatStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(deviceDomain.getSiteName()));

        /* 站点类型 */
        cell = row.createCell(2);
        cell.setCellStyle(textFormatStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(deviceDomain.getSiteTypeName()));
    }

    /**
     * <p>
     * Description:批量信息导入
     * </p>
     */
    public String batchInto() {
        logger.info(getText("function.title") + getText("add.function"));
        long datebeg = System.currentTimeMillis();
        logger.debug("开始导入时间：" + datebeg);
        // 获取当前用户角色
        UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("userInfo");

        try {
            tempSet = getExcelData(deviceDomain.getBatchfile(), user);
            if (null != errorMsg && !"".equals(errorMsg)) {
                return "ERROR";
            }
        } catch (Exception e) {
            if (null == msg || "".equals(msg)) {
                errorMsg = getText("file.readFalse");
            }
            logger.info(getText("info.import_error"), e);
            return "ERROR";
        }

        int right = 0;
        String operInfo = "";
        try {
            right = batchIntoSet(tempSet, user);

            long dataend = System.currentTimeMillis();
            logger.debug("导入结束：" + dataend);

            if (0 == (totalCount - right)) {
                operInfo = getText("batchinto.total") + totalCount
                        + getText("batchinto.total.succ") + right + getText("batchinto.total.use")
                        + (dataend - datebeg) / thousand + getText("batchinto.total.list");
            } else {
                operInfo = getText("batchinto.total") + totalCount
                        + getText("batchinto.total.succ") + right + getText("batchinto.total.drop")
                        + (totalCount - right) + getText("batchinto.total.use")
                        + (dataend - datebeg) / thousand + getText("batchinto.total.list");
            }

            if (null == errorMsg || "".equals(errorMsg)) {
                msg = operInfo;
            }
            operationInfo = getText("oplog.add.success", "成功批量导入" + right + "条数据");
            deviceDomain.setImportDeviceFlag("1");
        } catch (Exception e) {
            logger.info(getText("info.import_fail"), e);
            return "ERROR";
        }
        return "SUCCESS";
    }

    /**
     * 获取指定excel文件的数据，并返回一个List
     */
    public Set<DeviceDomain> getExcelData(String path, UserInfo user) throws Exception {
        int sheetnum = 0;
        java.io.File file = new java.io.File(path);
        if (maxSize < file.length()) {
            errorMsg = getText("file.content.tooMany");
            logger.info(getText("file.content.tooMany"));
            throw new Exception(getText("file.content.tooMany"));
        }
        InputStream in = new FileInputStream(path);
        HSSFWorkbook wb = new HSSFWorkbook(in);
        // 只在第一个sheet页是待导数据，别的sheet页不管
        sheetnum = 1;// wb.getNumberOfSheets();
        HSSFRow row;
        HSSFCell cell = null;
        HSSFSheet sheet;
        String val;
        Boolean isTrue = true;
        Set<DeviceDomain> set = new HashSet<DeviceDomain>();
        int totalLength = 0;
        for (int i = 0; i < sheetnum; i++) {
            sheet = wb.getSheetAt(i);
            int max = sheet.getLastRowNum();

            // 解决POI的BUG：getLastRowNum统计了空行，需要去掉
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                // 控制中心管理员批量导入时包含所属站点列，站点管理员不包含
                if ("1".equals(user.getRoleType())) {
                    if ((null == row.getCell(0) || "".equals(row.getCell(0).toString().trim()))
                            && (null == row.getCell(1) || "".equals(row.getCell(1).toString()
                                    .trim()))
                            && (null == row.getCell(2) || "".equals(row.getCell(2).toString()
                                    .trim()))
                            && (null == row.getCell(three) || "".equals(row.getCell(three)
                                    .toString().trim()))
                            && (null == row.getCell(four) || "".equals(row.getCell(four).toString()
                                    .trim()))
                            && (null == row.getCell(five) || "".equals(row.getCell(five).toString()
                                    .trim()))
                            && (null == row.getCell(six) || "".equals(row.getCell(six).toString()
                                    .trim()))
                            && (null == row.getCell(seven) || "".equals(row.getCell(seven)
                                    .toString().trim()))
                            && (null == row.getCell(eight) || "".equals(row.getCell(eight)
                                    .toString().trim()))
                            && (null == row.getCell(nine) || "".equals(row.getCell(nine).toString()
                                    .trim()))
                            && (null == row.getCell(ten) || "".equals(row.getCell(ten).toString()
                                    .trim()))
                    /*
                     * 由于一个站点可以接入多台交换机，所以批量导入时无法处理交换机和端口的信息，在模板中去掉 && (null == row.getCell(eleven)
                     * || "".equals(row.getCell(eleven) .toString().trim()))
                     */) {
                        max--;
                    }
                } else if ("2".equals(user.getRoleType())) {
                    if ((null == row.getCell(0) || "".equals(row.getCell(0).toString().trim()))
                            && (null == row.getCell(1) || "".equals(row.getCell(1).toString()
                                    .trim()))
                            && (null == row.getCell(2) || "".equals(row.getCell(2).toString()
                                    .trim()))
                            && (null == row.getCell(three) || "".equals(row.getCell(three)
                                    .toString().trim()))
                            && (null == row.getCell(four) || "".equals(row.getCell(four).toString()
                                    .trim()))
                            && (null == row.getCell(five) || "".equals(row.getCell(five).toString()
                                    .trim()))
                            && (null == row.getCell(six) || "".equals(row.getCell(six).toString()
                                    .trim()))
                            && (null == row.getCell(seven) || "".equals(row.getCell(seven)
                                    .toString().trim()))
                            && (null == row.getCell(eight) || "".equals(row.getCell(eight)
                                    .toString().trim()))
                            && (null == row.getCell(nine) || "".equals(row.getCell(nine).toString()
                                    .trim()))
                    /*
                     * 由于一个站点可以接入多台交换机，所以批量导入时无法处理交换机和端口的信息，在模板中去掉 && (null == row.getCell(ten) ||
                     * "".equals(row.getCell(ten).toString() .trim()))
                     */) {
                        max--;
                    }
                }

            }

            totalCount = max;
            totalLength = totalLength + max + 1;
            // 第一行数据是否是表头，不是则按有效数据处理
            row = sheet.getRow(0);
            if (0 == max) { // 除表头外，此Sheet页中无数据。表头分有，无数据两种情况
                if (null == row) { // 表头无数据
                    --totalLength;
                    continue;
                } else {// 表头有数据，判断是否是字段描述，若不是提示错误信息
                    int flag = checkTop(cell, row, wb, i, user.getRoleType());
                    if ("1".equals(user.getRoleType()) && eleven != flag) {
                        errorMsg = getText("file.top.edited");
                        logger.info(getText("file.top.edited"));
                    } else if ("2".equals(user.getRoleType()) && ten != flag) {
                        errorMsg = getText("file.top.edited");
                        logger.info(getText("file.top.edited"));
                    } else {
                        --totalLength;
                    }
                }
            } else { // Sheet中除表头外另有存在的数据
                if (null != row) { // 表头不为空，取表头数据
                    int flag = checkTop(cell, row, wb, i, user.getRoleType());
                    if ("1".equals(user.getRoleType()) && eleven != flag) {
                        errorMsg = getText("file.top.edited");
                        logger.info(getText("file.top.edited"));
                        isTrue = false;
                    } else if ("2".equals(user.getRoleType()) && ten != flag) {
                        errorMsg = getText("file.top.edited");
                        logger.info(getText("file.top.edited"));
                        isTrue = false;
                    } else {
                        --totalLength;
                    }
                } else { // 表头为空
                    --totalLength;
                }
                // 读取表头以后的数据
                if (isTrue) {
                    labelA: for (int y = 1; y <= max; y++) {
                        row = sheet.getRow(y);
                        if (null != row) { // 此行非空
                            String localTableIDRef = "";
                            String agentName = "";
                            String typeId = "";
                            String deviceType = "";
                            String agentComp = "";
                            String diskNum = "";
                            String siteId = "";
                            // String switchPort = "";
                            String agentIp = "";
                            String clPort = "";
                            String community = "";
                            String agentDesc = "";

                            DeviceDomain tempInfo = new DeviceDomain();

                            // 设备编码
                            cell = row.getCell(0);
                            if (null != cell) {
                                val = getCellValue(cell, wb.getSheetName(i), 0, 1);
                            } else {
                                val = "";
                            }
                            String idres = checkIdFormat(val, user);
                            if ("true".equals(idres)) {
                                localTableIDRef = val;
                            } else if ("no-right".equals(idres)) {
                                errorRow = (y + 1);
                                errorMsg = getText("file.localTableIDRef.noRight");
                                logger.info(getText("file.localTableIDRef.noRight"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            } else {
                                errorRow = (y + 1);
                                errorMsg = getText("file.localTableIDRef.errorFormat");
                                logger.info(getText("file.localTableIDRef.errorFormat"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            }

                            // 设备类型ID
                            String typeIdVal = "";
                            cell = row.getCell(2);
                            if (null != cell) {
                                typeIdVal = getCellValue(cell, wb.getSheetName(i), 0, three);
                            } else {
                                typeIdVal = "";
                            }
                            String tflag = checkTypeIdFormat(typeIdVal);
                            if ("true".equals(tflag)) {
                                typeId = typeIdVal;
                            } else if ("none".equals(tflag)) {
                                errorRow = (y + 1);
                                errorMsg = getText("file.typeId.none");
                                logger.info(getText("file.typeId.none"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            } else {
                                errorRow = (y + 1);
                                errorMsg = getText("file.typeId.errorFormat");
                                logger.info(getText("file.typeId.errorFormat"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            }

                            // 设备型号
                            cell = row.getCell(three);
                            if (null != cell) {
                                val = getCellValue(cell, wb.getSheetName(i), 0, four);
                            } else {
                                val = "";
                            }
                            if ("true".equals(checkDeviceTypeFormat(val))) {
                                deviceType = val;
                            } else {
                                errorRow = (y + 1);
                                errorMsg = getText("file.deviceType.errorFormat");
                                logger.info(getText("file.deviceType.errorFormat"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            }

                            // 设备厂商
                            cell = row.getCell(four);
                            if (null != cell) {
                                val = getCellValue(cell, wb.getSheetName(i), 0, five);
                            } else {
                                val = "";
                            }
                            if ("true".equals(checkAgentCompFormat(val))) {
                                agentComp = val;
                            } else {
                                errorRow = (y + 1);
                                errorMsg = getText("file.agentComp.errorFormat");
                                logger.info(getText("file.agentComp.errorFormat"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            }

                            // 硬盘个数
                            cell = row.getCell(five);
                            if (null != cell) {
                                val = getCellValue(cell, wb.getSheetName(i), 0, six);
                            } else {
                                val = "";
                            }
                            String dflag = checkDiskNumFormat(val, typeIdVal);
                            if ("true".equals(dflag)) {
                                diskNum = val;
                            } else if ("error".equals(dflag)) {
                                errorRow = (y + 1);
                                errorMsg = getText("file.diskNum.errorType");
                                logger.info(getText("file.diskNum.errorType"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            } else {
                                errorRow = (y + 1);
                                errorMsg = getText("file.diskNum.errorFormat");
                                logger.info(getText("file.diskNum.errorFormat"));
                                set = new HashSet<DeviceDomain>();
                                break labelA;
                            }

                            // 所属站点，站点管理员没有此列
                            if ("1".equals(user.getRoleType())) {
                                String siteVal;
                                cell = row.getCell(six);
                                if (null != cell) {
                                    siteVal = getCellValue(cell, wb.getSheetName(i), 0, seven);
                                } else {
                                    siteVal = "";
                                }
                                String sflag = checkSiteIdFormat(siteVal);
                                if ("true".equals(sflag)) {
                                    siteId = siteVal;
                                    
                                    // 设备名称
                                    cell = row.getCell(1);
                                    if (null != cell) {
                                        val = getCellValue(cell, wb.getSheetName(i), 0, 2);
                                    } else {
                                        val = "";
                                    }
                                    // 校验当前站点设备名称是否重复。如果当前是系统管理员，那么取模板中站点值，如果是站点管理员，取当前站点信息。
                                    String nameflag = checkNameFormat(val,siteId);
                                    if ("true".equals(nameflag)) {
                                        agentName = val;
                                    } else if ("duplicate".equals(nameflag)) {
                                        errorRow = (y + 1);
                                        errorMsg = getText("file.deviceName.duplicate");
                                        logger.info(getText("file.deviceName.duplicate"));
                                        set = new HashSet<DeviceDomain>();
                                        break labelA;
                                    } else {
                                        errorRow = (y + 1);
                                        errorMsg = getText("file.deviceName.errorFormat");
                                        logger.info(getText("file.deviceName.errorFormat"));
                                        set = new HashSet<DeviceDomain>();
                                        break labelA;
                                    }
                                    
                                    
                                } else if ("none".equals(sflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.siteId.none");
                                    logger.info(getText("file.siteId.none"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.siteId.errorFormat");
                                    logger.info(getText("file.siteId.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }

                                /*
                                 * 交换机端口,由于一个站点可以接入多台交换机，所以批量导入时无法处理交换机和端口的信息，在模板中去掉。同时，该位置以后的字段按顺序提前
                                 * cell = row.getCell(seven); if (null != cell) { val =
                                 * getCellValue(cell, wb.getSheetName(i), 0, eight); } else { val =
                                 * ""; } String pflag = checkSwitchPortFormat(val, typeIdVal,
                                 * siteVal); if ("true".equals(pflag)) { switchPort = val; } else if
                                 * ("error".equals(pflag)) { errorRow = (y + 1); errorMsg =
                                 * getText("file.switchPort.errorType");
                                 * logger.info(getText("file.switchPort.errorType")); set = new
                                 * HashSet<DeviceDomain>(); break labelA; } else if
                                 * ("duplicate".equals(pflag)) { errorRow = (y + 1); errorMsg =
                                 * getText("file.switchPort.duplicate");
                                 * logger.info(getText("file.switchPort.duplicate")); set = new
                                 * HashSet<DeviceDomain>(); break labelA; } else { errorRow = (y +
                                 * 1); errorMsg = getText("file.switchPort.errorFormat");
                                 * logger.info(getText("file.switchPort.errorFormat")); set = new
                                 * HashSet<DeviceDomain>(); break labelA; }
                                 */

                                // 设备采集IP
                                cell = row.getCell(seven);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, eight);
                                } else {
                                    val = "";
                                }
                                String ipflag = checkAgentIpFormat(val);
                                if ("true".equals(ipflag)) {
                                    agentIp = val;
                                } else if ("duplicate".equals(ipflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.agentIp.duplicate");
                                    logger.info(getText("file.agentIp.duplicate"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.agentIp.errorFormat");
                                    logger.info(getText("file.agentIp.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }

                                // 采集端口
                                cell = row.getCell(eight);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, nine);
                                } else {
                                    val = "";
                                }
                                String cflag = checkClPortFormat(val, typeIdVal);
                                if ("true".equals(cflag)) {
                                    clPort = val;
                                } else if ("error".equals(cflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.clPort.errorType");
                                    logger.info(getText("file.clPort.errorType"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.clPort.errorFormat");
                                    logger.info(getText("file.clPort.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }

                                // 共同体
                                cell = row.getCell(nine);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, ten);
                                } else {
                                    val = "";
                                }
                                String ctflag = checkCommunityFormat(val, typeIdVal);
                                if ("true".equals(ctflag)) {
                                    community = val;
                                } else if ("error".equals(ctflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.community.errorType");
                                    logger.info(getText("file.community.errorType"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.community.errorFormat");
                                    logger.info(getText("file.community.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }

                                // 描述
                                cell = row.getCell(ten);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, eleven);
                                } else {
                                    val = "";
                                }
                                if ("true".equals(checkAgentDescFormat(val))) {
                                    agentDesc = val;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.agentDesc.errorFormat");
                                    logger.info(getText("file.agentDesc.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }
                            } else if ("2".equals(user.getRoleType())) {
                                
                                // 设备名称
                                cell = row.getCell(1);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, 2);
                                } else {
                                    val = "";
                                }
                                // 校验当前站点设备名称是否重复。如果当前是系统管理员，那么取模板中站点值，如果是站点管理员，取当前站点信息。
                                String nameflag = checkNameFormat(val,String.valueOf(user.getSiteId()));
                                if ("true".equals(nameflag)) {
                                    agentName = val;
                                } else if ("duplicate".equals(nameflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.deviceName.duplicate");
                                    logger.info(getText("file.deviceName.duplicate"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.deviceName.errorFormat");
                                    logger.info(getText("file.deviceName.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }
                                /*
                                 * 交换机端口,由于一个站点可以接入多台交换机，所以批量导入时无法处理交换机和端口的信息，在模板中去掉。同时，该位置以后的字段按顺序提前
                                 * cell = row.getCell(six); if (null != cell) { val =
                                 * getCellValue(cell, wb.getSheetName(i), 0, seven); } else { val =
                                 * ""; } String pflag = checkSwitchPortFormat(val, typeIdVal,
                                 * String.valueOf(user.getSiteId())); if ("true".equals(pflag)) {
                                 * switchPort = val; } else if ("error".equals(pflag)) { errorRow =
                                 * (y + 1); errorMsg = getText("file.switchPort.errorType");
                                 * logger.info(getText("file.switchPort.errorType")); set = new
                                 * HashSet<DeviceDomain>(); break labelA; } else if
                                 * ("duplicate".equals(pflag)) { errorRow = (y + 1); errorMsg =
                                 * getText("file.switchPort.duplicate");
                                 * logger.info(getText("file.switchPort.duplicate")); set = new
                                 * HashSet<DeviceDomain>(); break labelA; } else { errorRow = (y +
                                 * 1); errorMsg = getText("file.switchPort.errorFormat");
                                 * logger.info(getText("file.switchPort.errorFormat")); set = new
                                 * HashSet<DeviceDomain>(); break labelA; }
                                 */

                                // 设备采集IP
                                cell = row.getCell(six);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, seven);
                                } else {
                                    val = "";
                                }
                                String ipflag = checkAgentIpFormat(val);
                                if ("true".equals(ipflag)) {
                                    agentIp = val;
                                } else if ("duplicate".equals(ipflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.agentIp.duplicate");
                                    logger.info(getText("file.agentIp.duplicate"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.agentIp.errorFormat");
                                    logger.info(getText("file.agentIp.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }

                                // 采集端口
                                cell = row.getCell(seven);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, eight);
                                } else {
                                    val = "";
                                }
                                String cflag = checkClPortFormat(val, typeIdVal);
                                if ("true".equals(cflag)) {
                                    clPort = val;
                                } else if ("error".equals(cflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.clPort.errorType");
                                    logger.info(getText("file.clPort.errorType"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.clPort.errorFormat");
                                    logger.info(getText("file.clPort.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }

                                // 共同体
                                cell = row.getCell(eight);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, nine);
                                } else {
                                    val = "";
                                }
                                String ctflag = checkCommunityFormat(val, typeIdVal);
                                if ("true".equals(ctflag)) {
                                    community = val;
                                } else if ("error".equals(ctflag)) {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.community.errorType");
                                    logger.info(getText("file.community.errorType"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.community.errorFormat");
                                    logger.info(getText("file.community.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }

                                // 描述
                                cell = row.getCell(nine);
                                if (null != cell) {
                                    val = getCellValue(cell, wb.getSheetName(i), 0, ten);
                                } else {
                                    val = "";
                                }
                                if ("true".equals(checkAgentDescFormat(val))) {
                                    agentDesc = val;
                                } else {
                                    errorRow = (y + 1);
                                    errorMsg = getText("file.agentDesc.errorFormat");
                                    logger.info(getText("file.agentDesc.errorFormat"));
                                    set = new HashSet<DeviceDomain>();
                                    break labelA;
                                }
                            }

                            tempInfo.setLocalTableIDRef(localTableIDRef);
                            tempInfo.setAgentName(agentName);
                            tempInfo.setTypeId(typeId);
                            tempInfo.setDeviceType(deviceType);
                            tempInfo.setAgentComp(agentComp);
                            tempInfo.setDiskNum(diskNum);
                            tempInfo.setSiteId(siteId);
                            // tempInfo.setSwitchPort(switchPort);
                            tempInfo.setAgentIp(agentIp);
                            tempInfo.setClPort(clPort);
                            tempInfo.setCommunity(community);
                            tempInfo.setAgentDesc(agentDesc);

                            set.add(tempInfo);
                        } else {
                            --totalLength;
                            ++blankNum;
                        }
                    }
                }
            }
            // 除非表头空行外的数据条数判断，大于1000条则提示用户重新整理。
            if (totalLength > thousand) {
                errorMsg = getText("file.1000_failed");
                logger.info(getText("file.1000_failed"));
                throw new Exception(getText("file.1000_failed"));
            }
        }
        in.close();
        return set;
    }

    /**
     * 插入或更新数据处理
     */
    @SuppressWarnings("unchecked")
    public int batchIntoSet(Set<DeviceDomain> tempresultSet, UserInfo user) throws Exception {
        List importDevice = new ArrayList();
        if (tempresultSet.size() > 0) {
            Iterator<DeviceDomain> itr = tempresultSet.iterator();
            while (itr.hasNext()) {
                DeviceDomain temp = itr.next();
                // 根据设备类型设置采集方式
                if (temp.getTypeId().equals("47")) {
                    temp.setAcquisitionMode("4");
                } else if (temp.getTypeId().equals("43") || temp.getTypeId().equals("44")
                        || temp.getTypeId().equals("45")) {
                    temp.setAcquisitionMode("0");
                } else {
                    temp.setAcquisitionMode("1");
                }
                // 设置站点
                if (("2").equals(user.getRoleType())) {
                    temp.setSiteId(String.valueOf(user.getSiteId()));
                }
                // 查询该设备是否存在
                int count = ibatisDAO.getCount("DeviceInfo.getCountIshave", temp);
                if (count > 0) {
                    update(temp);
                } else {
                    insert(temp);
                }
                importDevice.add(temp.getLocalTableIDRef());
            }
            ActionContext.getContext().getSession().put("importDevice", importDevice.toString());
        }
        return importDevice.size();
    }

    /**
     * <p>
     * Description:如果数据不存在，则添加数据
     * </p>
     */
    private void insert(DeviceDomain temp) throws SQLException {
        ibatisDAO.insertData("DeviceInfo.addDeviceDomain", temp);

        String agentId = (String) ibatisDAO.getSingleRecord("DeviceInfo.getCurrPara", null);
        temp.setZciId(agentId);
        temp.setcMDBIDRef(agentId);
        // 获取资源类型
        String type = temp.getTypeId();
        String group = "";
        if ("31".equals(type) || "32".equals(type) || "42".equals(type)) {
            group = "NVR";
        } else if ("33".equals(type) || "34".equals(type)) {
            group = "IPSAN";
        } else if ("35".equals(type) || "46".equals(type)) {
            group = "Encoder";
        } else if ("36".equals(type) || "37".equals(type) || "38".equals(type) || "39".equals(type)
                || "40".equals(type)) {
            group = "IPC";
        } else if ("41".equals(type)) {
            group = "Switch";
        } else if ("43".equals(type) || "44".equals(type)) {
            group = "VMS";
        } else if ("45".equals(type)) {
            group = "D4";
        } else if ("47".equals(type)) {
            group = "Keyword";
        }
        temp.setResourceType("CIDC-RT-" + group);
        temp.setLeafName(temp.getAgentIp());
        temp.setLocalTableName(temp.getAgentName());
        ibatisDAO.insertData("DeviceInfo.insertZoneClusterInstance", temp);
    }

    /**
     * <p>
     * Description:如果数据存在，则根据设备编码更新数据
     * </p>
     */
    private void update(DeviceDomain temp) throws SQLException {
        ibatisDAO.updateData("DeviceInfo.updateBatchInto", temp);

        // 获取资源类型
        String type = temp.getTypeId();
        String group = "";
        if ("31".equals(type) || "32".equals(type) || "42".equals(type)) {
            group = "NVR";
        } else if ("33".equals(type) || "34".equals(type)) {
            group = "IPSAN";
        } else if ("35".equals(type)) {
            group = "Encoder";
        } else if ("36".equals(type) || "37".equals(type) || "38".equals(type) || "39".equals(type)
                || "40".equals(type)) {
            group = "IPC";
        } else if ("41".equals(type) || "46".equals(type)) {
            group = "Switch";
        } else if ("43".equals(type) || "44".equals(type)) {
            group = "VMS";
        } else if ("45".equals(type)) {
            group = "D4";
        } else if ("47".equals(type)) {
            group = "Keyword";
        }
        temp.setResourceType("CIDC-RT-" + group);
        temp.setLeafName(deviceDomain.getAgentIp());
        temp.setLocalTableName(deviceDomain.getAgentName());
        ibatisDAO.updateData("DeviceInfo.updateBatchIntoInstance", temp);
    }

    /**
     * <p>
     * Description:根据表头个数，判断表头是否完整
     * </p>
     */
    public int checkTop(HSSFCell cell, HSSFRow row, HSSFWorkbook wb, int i, String roleType)
            throws Exception {
        String val;
        int flag = 0; // 标识第一行数据是否是表头，不是则按有效数据处理
        // 设备编号
        cell = row.getCell(0);
        val = getCellValue(cell, wb.getSheetName(i), 0, 1);
        if (getText("file.localTableIDRef.edited").equals(val)
                || getText("device.localTableIDRef").equals(val)) {// 判断是否为字段名
            ++flag;
        }
        // 设备名称
        cell = row.getCell(1);
        val = getCellValue(cell, wb.getSheetName(i), 0, 2);
        if (getText("device.deviceName").equals(val)) {
            ++flag;
        }
        // 设备类型ID
        cell = row.getCell(2);
        val = getCellValue(cell, wb.getSheetName(i), 0, three);
        if (getText("device.typeId").equals(val)) {
            ++flag;
        }
        // 设备型号
        cell = row.getCell(three);
        val = getCellValue(cell, wb.getSheetName(i), 0, four);
        if (getText("device.deviceType").equals(val)) {
            ++flag;
        }
        // 设备厂商
        cell = row.getCell(four);
        val = getCellValue(cell, wb.getSheetName(i), 0, five);
        if (getText("device.agentComp").equals(val)) {
            ++flag;
        }
        // 硬盘个数
        cell = row.getCell(five);
        val = getCellValue(cell, wb.getSheetName(i), 0, six);
        if (getText("device.diskNum").equals(val)) {
            ++flag;
        }
        // 所属站点，站点管理员不包含该列，接下来的各列要分别处理
        if ("1".equals(roleType)) {
            cell = row.getCell(six);
            val = getCellValue(cell, wb.getSheetName(i), 0, seven);
            if (getText("device.siteId").equals(val)) {
                ++flag;
            }

            /*
             * 交换机端口,由于一个站点可以接入多台交换机，所以批量导入时无法处理交换机和端口的信息，在模板中去掉。同时，该位置以后的字段按顺序提前 cell =
             * row.getCell(seven); val = getCellValue(cell, wb.getSheetName(i), 0, eight); if
             * (getText("device.switchPort").equals(val)) { ++flag; }
             */

            // 设备采集IP
            cell = row.getCell(seven);
            val = getCellValue(cell, wb.getSheetName(i), 0, nine);
            if (getText("device.deviceIp").equals(val)) {
                ++flag;
            }

            // 设备端口
            cell = row.getCell(eight);
            val = getCellValue(cell, wb.getSheetName(i), 0, ten);
            if (getText("device.clPort").equals(val)) {
                ++flag;
            }

            // 共同体
            cell = row.getCell(nine);
            val = getCellValue(cell, wb.getSheetName(i), 0, eleven);
            if (getText("device.community").equals(val)) {
                ++flag;
            }

            // 描述
            cell = row.getCell(ten);
            val = getCellValue(cell, wb.getSheetName(i), 0, twelve);
            if (getText("device.agentDesc").equals(val)) {
                ++flag;
            }
        } else if ("2".equals(roleType)) {
            /*
             * 交换机端口,由于一个站点可以接入多台交换机，所以批量导入时无法处理交换机和端口的信息，在模板中去掉。同时，该位置以后的字段按顺序提前 cell =
             * row.getCell(six); val = getCellValue(cell, wb.getSheetName(i), 0, seven); if
             * (getText("device.switchPort").equals(val)) { ++flag; }
             */

            // 设备采集IP
            cell = row.getCell(six);
            val = getCellValue(cell, wb.getSheetName(i), 0, eight);
            if (getText("device.deviceIp").equals(val)) {
                ++flag;
            }

            // 设备端口
            cell = row.getCell(seven);
            val = getCellValue(cell, wb.getSheetName(i), 0, nine);
            if (getText("device.clPort").equals(val)) {
                ++flag;
            }

            // 共同体
            cell = row.getCell(eight);
            val = getCellValue(cell, wb.getSheetName(i), 0, ten);
            if (getText("device.community").equals(val)) {
                ++flag;
            }

            // 描述
            cell = row.getCell(nine);
            val = getCellValue(cell, wb.getSheetName(i), 0, eleven);
            if (getText("device.agentDesc").equals(val)) {
                ++flag;
            }
        }

        return flag;
    }

    /**
     * <p>
     * Description:获取文件单元值
     * </p>
     */
    private String getCellValue(HSSFCell cell, String sheetName, int row, int col) throws Exception {
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
            // 单元格类型为数字
            case HSSFCell.CELL_TYPE_NUMERIC:
                // 取数字单元格的值
                double d = cell.getNumericCellValue();
                String s = String.valueOf(d);
                int pos = s.indexOf(".");
                return s.substring(0, pos).trim();
                // 单元格类型为字符串
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue() == null ? "" : cell.getStringCellValue().trim();
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
            default:
                throw new Exception(sheetName + "中单元格格式有问题，请确认后重新导入！");
            }
        } else {
            throw new Exception(sheetName + "中单元格格式有问题，请确认后重新导入！");
        }
    }

    /**
     * <p>
     * Description:判断设备编码必填，最多32位
     * </p>
     */
    public String checkIdFormat(String data, UserInfo user) throws Exception {
        String flag = "true";
        if (data != null && 0 != data.length() && data.length() <= thirtyTwo) {// 位数正确
            int count = 1;
            DeviceDomain temp = new DeviceDomain();
            temp.setLocalTableIDRef(data);
            count = ibatisDAO.getCount("DeviceInfo.getCountIshave", temp);
            if (count > 0 && "2".equals(user.getRoleType())) {// 站点管理员
                // 如果设备存在，且是其它站点所属，设备编码不可被该用户修改
                String deviceSiteId = ((DeviceDomain) ibatisDAO.getSingleRecord(
                        "DeviceInfo.getDevice", temp)).getSiteId();
                if (deviceSiteId != null && !("").equals(deviceSiteId)) {
                    String userSiteId = String.valueOf(user.getSiteId());
                    if (!deviceSiteId.equals(userSiteId)) {
                        flag = "no-right";
                    }
                }
            }
        } else {
            flag = "false";
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断设备名称必填，最多32位
     * </p>
     */
    public String checkNameFormat(String data, String siteId) throws Exception {
        String flag = "true";
        if (data != null && 0 != data.length() && data.length() <= thirtyTwo) {// 位数正确
            // 校验当前站点设备名称是否重复,如果不重复返回正确
            DeviceDomain deviceDomain = new DeviceDomain();
            deviceDomain.setAgentName(data);
            deviceDomain.setSiteId(siteId);
            if (0 != ibatisDAO.getCount("DeviceInfo.existNameCount", deviceDomain)) {
                flag = "duplicate";
            }
        } else {
            flag = "false";
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断设备类型ID必填，是否存在
     * </p>
     */
    public String checkTypeIdFormat(String data) throws Exception {
        String flag = "true";
        if (data != null && 0 != data.length() && data.length() == 2) {
            // 判断数据库是否存在此设备类型ID,不存在报错
            DeviceDomain temp = new DeviceDomain();
            temp.setTypeId(data);
            if (null == ibatisDAO.getData("DeviceInfo.getGroupTypeList", temp)) {
                flag = "none";
            }
        } else {
            flag = "false";
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断设备型号，最多32位
     * </p>
     */
    public String checkDeviceTypeFormat(String data) throws Exception {
        String flag = "false";
        if ((data == null || 0 == data.length()) || (null != data && data.length() <= thirtyTwo)) {// 位数正确
            flag = "true";
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断设备厂商，最多32位
     * </p>
     */
    public String checkAgentCompFormat(String data) throws Exception {
        String flag = "false";
        if ((data == null || 0 == data.length()) || (null != data && data.length() <= thirtyTwo)) {// 位数正确
            flag = "true";
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断硬盘个数，NVR、IP-SAN和Encoder类型有此属性，最多2位数字
     * </p>
     */
    public String checkDiskNumFormat(String data, String typeId) throws Exception {
        String flag = "true";
        if (null != data && data != "") {
            if (("31").equals(typeId) || ("32").equals(typeId) || ("42").equals(typeId)
                    || ("33").equals(typeId) || ("34").equals(typeId) || ("35").equals(typeId)) {
                if (data.length() <= 2) {
                    for (int i = data.length(); --i >= 0;) {
                        if (!Character.isDigit(data.charAt(i))) {
                            flag = "false";
                        }
                    }
                } else {
                    flag = "false";
                }
            } else {
                flag = "error";
            }
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断所属站点ID必填，是否存在
     * </p>
     */
    public String checkSiteIdFormat(String data) throws Exception {
        String flag = "true";
        if (data != null && 0 != data.length() && data.length() <= 2) {
            // 判断数据库是否存在此设备类型ID,不存在报错
            DeviceDomain temp = new DeviceDomain();
            temp.setSiteId(data);
            if (null == ibatisDAO.getData("DeviceInfo.getSites", temp)) {
                flag = "none";
            }
        } else {
            flag = "false";
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断交换机端口，每个站点内端口唯一，交换机无此属性，最多5位数字
     * </p>
     */
    public String checkSwitchPortFormat(String data, String typeId, String siteId) throws Exception {
        String flag = "true";
        if (null != data && data != "") {
            if (!("41").equals(typeId)) {
                if (data.length() <= 5) {
                    for (int i = data.length(); --i >= 0;) {
                        if (!Character.isDigit(data.charAt(i))) {
                            flag = "false";
                        }
                    }
                    if ("true".equals(flag)) {
                        // 校验本站点交换机端口是否唯一
                        DeviceDomain temp = new DeviceDomain();
                        temp.setSwitchPort(data);
                        temp.setSiteId(siteId);
                        int count = ibatisDAO.getCount("DeviceInfo.existPortCount", temp);
                        if (count > 0) {
                            flag = "duplicate";
                        }
                    }
                } else {
                    flag = "false";
                }
            } else {
                flag = "error";
            }
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断设备采集IP必填，唯一，最多15位
     * </p>
     */
    public String checkAgentIpFormat(String data) throws Exception {
        String flag = "false";
        if (data != null && 0 != data.length() && data.length() <= fifteen) {// 位数正确
            // 校验IP格式
            String str = "([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){2}(\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5]))";
            Pattern pattern = Pattern.compile(str);
            Matcher matcher = pattern.matcher(data);
            if (matcher.matches()) {
                // 校验IP是否唯一
                DeviceDomain temp = new DeviceDomain();
                temp.setAgentIp(data);
                int count = ibatisDAO.getCount("DeviceInfo.existIPCount", temp);
                if (count == 0) {
                    flag = "true";
                } else {
                    flag = "duplicate";
                }
            }
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断采集端口，SNMP采集方式的设备有此属性，最多5位数字
     * </p>
     */
    public String checkClPortFormat(String data, String typeId) throws Exception {
        String flag = "true";
        // 如果添加采集端口，必须不是控制键盘类型设备
        if (null != data && data != "") {
            if (!("47").equals(typeId)) {
                if (data.length() <= 5) {
                    for (int i = data.length(); --i >= 0;) {
                        if (!Character.isDigit(data.charAt(i))) {
                            flag = "false";
                        }
                    }
                } else {
                    flag = "false";
                }
            } else {
                flag = "error";
            }
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断共同体，SNMP采集方式的设备有此属性，最多20位
     * </p>
     */
    public String checkCommunityFormat(String data, String typeId) throws Exception {
        String flag = "true";
        // 如果添加采集端口，必须不是控制键盘类型设备
        if (null != data && data != "") {
            if (!("47").equals(typeId)) {
                if (data.length() > 20) {
                    flag = "false";
                }
            } else {
                flag = "error";
            }
        }
        return flag;
    }

    /**
     * <p>
     * Description:判断描述，最多200位
     * </p>
     */
    public String checkAgentDescFormat(String data) throws Exception {
        String flag = "false";
        if ((data == null || 0 == data.length()) || (null != data && data.length() <= twoHundred)) {// 位数正确
            flag = "true";
        }
        return flag;
    }

    /**
     * @return Returns the blankNum.
     */
    public int getBlankNum() {
        return blankNum;
    }

    /**
     * @param blankNum The blankNum to set.
     */
    public void setBlankNum(int blankNum) {
        this.blankNum = blankNum;
    }

    /**
     * @return Returns the batchfile.
     */
    public File getBatchfile() {
        return batchfile;
    }

    /**
     * @param batchfile The batchfile to set.
     */
    public void setBatchfile(File batchfile) {
        this.batchfile = batchfile;
    }

    /**
     * @return Returns the totalCount.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount The totalCount to set.
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return Returns the deviceDomain.
     */
    public DeviceDomain getDeviceDomain() {
        return deviceDomain;
    }

    /**
     * @param deviceDomain The deviceDomain to set.
     */
    public void setDeviceDomain(DeviceDomain deviceDomain) {
        this.deviceDomain = deviceDomain;
    }

    /**
     * @return Returns the deviceList.
     */
    public List<DeviceDomain> getDeviceList() {
        return deviceList;
    }

    /**
     * @param deviceList The deviceList to set.
     */
    public void setDeviceList(List<DeviceDomain> deviceList) {
        this.deviceList = deviceList;
    }

    /**
     * @return Returns the typeList.
     */
    public List<DeviceDomain> getTypeList() {
        return typeList;
    }

    /**
     * @param typeList The typeList to set.
     */
    public void setTypeList(List<DeviceDomain> typeList) {
        this.typeList = typeList;
    }

    /**
     * @return Returns the siteList.
     */
    public List<DeviceDomain> getSiteList() {
        return siteList;
    }

    /**
     * @param siteList The siteList to set.
     */
    public void setSiteList(List<DeviceDomain> siteList) {
        this.siteList = siteList;
    }

    /**
     * @return Returns the errorMsg.
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg The errorMsg to set.
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return Returns the errorRow.
     */
    public int getErrorRow() {
        return errorRow;
    }

    /**
     * @param errorRow The errorRow to set.
     */
    public void setErrorRow(int errorRow) {
        this.errorRow = errorRow;
    }

}
