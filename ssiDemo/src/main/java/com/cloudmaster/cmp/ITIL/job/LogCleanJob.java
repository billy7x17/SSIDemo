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
package com.cloudmaster.cmp.ITIL.job;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import com.cloudmaster.cmp.container.ibatis.IbatisDAO;
import com.cloudmaster.cmp.util.FileUtil;
import com.neusoft.mid.enzyme.quzrtz.BaseJob;
import com.neusoft.mid.enzyme.script.ScriptUtils;
import com.neusoft.mid.iamp.logger.LogService;

/** 
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class LogCleanJob extends BaseJob {

    private static LogService logger = LogService.getLogger(LogCleanJob.class);

    private IbatisDAO ibatisDAO;

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    private String log = "";

    String jobresult = "success";

    @Override
    public void invoke(JobExecutionContext context) throws Exception {
        JobDataMap paramap = context.getMergedJobDataMap();
        String uri = paramap.getString("uri");// 获取任务参数中json数据里配置的"uri"的值
        Map<String, Object> parameter = new HashMap<String, Object>(paramap.getWrappedMap());
        String cleanError = "";
        String pathError = "";
        // 获得要删除的日志路径及日志名称前后缀
        List<Map> logList = ibatisDAO.getData("jobSql.getLogCleanConfig", null);
        if (null != logList || logList.size() > 0) {
            for (Map map : logList) {
                String path = (String) map.get("logPath");
                String logFirstName = (String) map.get("logFirstName");
                String suffix = (String) map.get("suffix");
                int days = Integer.parseInt((String) map.get("days"));
                Calendar calender = Calendar.getInstance();
                calender.add(Calendar.DAY_OF_YEAR, days * -1);
                int lastDay = calender.get(Calendar.YEAR) * 10000
                        + (calender.get(Calendar.MONTH) + 1) * 100
                        + calender.get(Calendar.DAY_OF_MONTH);
                String[] logNames = getLogsName(path, logFirstName, suffix, lastDay);
                if (null == logNames) {
                    pathError = pathError + path + ",";
                    logger.info("路径[" + path + "]无效！");
                } else {
                    for (String logName : logNames) {
                        String logPath = path + "/" + logName;
                        try {
                            FileUtil.removeFile(logPath);
                            logger.info("日志" + logPath + "删除成功！");
                        } catch (Exception ex) {
                            cleanError = cleanError + logPath + ",";
                            logger.info("日志" + logPath + "删除失败！");
                            Map<String, String> alarmInfo = new HashMap<String, String>();
                            alarmInfo.put("ALARM_OID", "logClean");
                            alarmInfo.put("ALARM_LEVEL", "4");
                            alarmInfo.put("ALARM_TYPE", "日志归档");
                            alarmInfo.put("ALARM_TITLE", "日志删除失败");
                            alarmInfo.put("ALARM_CONTENT", "日志" + logPath + "删除失败");
                            alarmInfo.put("AGENT_TYPE", "100");
                            ibatisDAO.insertData("resuorceUsed.insertAlarm", alarmInfo);
                        }
                    }
                }
            }
        } else {
            logger.info("数据库中无日志归档配置信息");
        }
        context.setResult(jobresult);// context.setResult("success")表示任务执行成功；其他则失败
        // 如果任务参数中配置了所需执行的script的uri，则执行script；否则，不执行
        if (uri != null && !"".equals(uri)) {
            Object log = ScriptUtils.executeScript(new URI(uri), parameter);
            context.put("log", "脚本执行结果：" + log.toString());
        } else {
            if (jobresult.equals("success")){
                if (!"".equals(cleanError)) log = log + "删除下列日志失败：[" + cleanError + "]";
                if (!"".equals(pathError)) log = log + "路径无效：[" + pathError + "]";
                context.put("log", log);
            }
            else {
                if (!"".equals(cleanError)) log = log + "删除下列日志失败：[" + cleanError + "]";
                if (!"".equals(pathError)) log = log + "路径无效：[" + pathError + "]";
                context.put("log", pathError + cleanError);
            }
        }
    }

    Pattern pattern = Pattern.compile("\\d{8}");

    private String getDate(String logName) {
        if (null != logName) {
            Matcher ma = pattern.matcher(logName);
            while (ma.find()) {
                return ma.group();
            }
            return null;
        } else {
            return null;
        }
    }

    private String[] getLogsName(String path, String logFirstName, String suffix, int lastDay) {
        File dir = new File(path);
        FilenameFilter filter = new LogNameFilter(logFirstName, suffix, lastDay);
        String[] logNames = dir.list(filter);
        return logNames;
    }

    /**
     * 日志文件名过滤器
     */
    private class LogNameFilter implements FilenameFilter {
        private String filename;

        private int lastDay;

        private String suffix;

        public LogNameFilter(String name, String suffix, int lastDay) {
            this.filename = name;
            this.lastDay = lastDay;
            this.suffix = suffix;
        }

        @Override
        public boolean accept(File dir, String name) {
            // 日志文件名name规则：[日志文件名]_[日期].[后缀名](例：njcmp_20120801.log)
            boolean nameMatch = name.split("_")[0].equals(this.filename);// 文件名为指定日志文件名前缀
            boolean nameSuffix = name.endsWith("." + this.suffix);// 文件格式为指定日志文件名后缀
            if (nameMatch && nameSuffix) {
                String logNameDate = getDate(name);
                boolean dateMatch = false;
                if (null != logNameDate)
                    dateMatch = this.lastDay - Integer.parseInt(logNameDate) > 0;// 文件名中日期信息小于最小需保留日期
                return dateMatch;
            } else {
                return false;
            }
        }
    }
}
