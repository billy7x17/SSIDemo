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
package com.cloudmaster.cmp.resource.view.web;
/**
 * @author <a href="mailto:zhaochuang@neusoft.com"> zhaoc </a>
 * @version 1.0.0 18 Mar 2012
 */
public abstract class Constants {
    /**
     * ibatis命名空间
     */
    public static final String DB_NAMESPACE_RESOURCEUTIL = "ResourceUtil.";
    /**
     * 子网 ibatis命名空间
     */
    public static final String DB_NAMESPACE_SUBNETINFO = "SubNetInfo.";
    /**
     * 公网ip 命名空间
     */
    public static final String DB_NAMESPACE_PIPINFO = "PIPInfo.";
     /**
      * 公网ip关联防火墙模块 命名空间
      */
    public static final String DB_NAMESPACE_IPDEVICERELATION = "IpDeviceRelation.";
    /**
     * 用户 ibatis命名空间
     */
    public static final String DB_NAMESPACE_USERINFO = "UserInfo.";
    /**
     * 网卡 ibatis命名空间
     */
    public static final String DB_NAMESPACE_NETCARDINFO = "NetCardInfo.";
    /**
     * 操作系统安装 ibatis命名空间
     */
    public static final String DB_NAMESPACE_OSINSTALLPMINFO = "OSInstallPmInfo.";
    /**
     * 小型机 ibatis命名空间
     */
    public static final String DB_NAMESPACE_MMINFO = "MmInfo.";
    /**
     * 小型机分区 ibatis命名空间
     */
    public static final String DB_NAMESPACE_MVMINFO = "MvmInfo.";
    /**
     * 交换机 ibatis命名空间
     */
    public static final String DB_NAMESPACE_STINFO = "STInfo.";
    /**
     * raid组 ibatis命名空间
     */
    public static final String DB_NAMESPACE_RAIDGROUPINFO = "RaidGroupInfo.";
    /**
     * 路由器 ibatis命名空间
     */
    public static final String DB_NAMESPACE_RTINFO = "RTInfo.";
    /**
     * 防火墙 ibatis命名空间
     */
    public static final String DB_NAMESPACE_FIREWALLINFO = "FirewallInfo.";
    /**
     * 硬件负载均衡 ibatis命名空间
     */
    public static final String DB_NAMESPACE_HLBINFO = "HLBInfo.";
    /**
     * 设备关联 ibatis命名空间
     */
    public static final String DB_NAMESPACE_RELATION = "Relation.";
    
    /**
     * 是否是KM用户 0：是 1：不是
     */
    public static final String SYSTEM_USER_KM = "1";
    
    
    /**
     * 编码
     */
    public static final String ENCODE = "UTF-8";
    /**
     * 资源池操作成功代码
     */
    public static final String SUCCESSCODE = "00000000";
    /**
     * 资源池操作未知错误代码
     */
    public static final String ERRORCODE = "99999999";
    
    /**
     * ie-config.xml 配置文件 workbook id
     */
    public static final String DEI_SHEET_ID = "pm_workbook";
    /**
     *  IP子网批量导入 的 workbook id
     */
    public static final String DEI_WORKBOOK_IP_ID = "ip_workbook";
    /**
     * 公网IP批量导入 
     */
    public static final String DEI_WORKBOOK_PUBLICIP_ID = "publicip_workbook";
    
    /**
     * 交换机 类型编码
     */
//    public static final String RESOURCE_TYPE_EXC = "CIDC-RT-SW";
    /**
     * 防火墙 类型编码
     */
//    public static final String RESOURCE_TYPE_FW = "CIDC-RT-FW";
    /**
     * 路由器 类型编码
     */
    public static final String RESOURCE_TYPE_RT = "CIDC-RT-RT";
    
    /************cloudmaster5***************/
    /**
     * 物理机型号ibatis 命名空间
     */
    public static final String DB_NAMESPACE_PMTYPEINFO = "PMTypeInfo.";
    /**
     * 配置信息历史信息变更ibatis 命名空间
     */
    public static final String DB_NAMESPACE_CIHISTORY = "CIHistory.";
    /**
     * 系统管理参数 ibatis 命名空间
     */
    public static final String DB_NAMESPACE_SYSTEMPARAMETER = "systemparameter.";
    /**
     * 关联关系导出 ibatis 命名空间
     */
    public static final String DB_NAMESPACE_CMEXPORTREF = "CmExportRef.";
    /**
     * 配置项属性映射 ibatis 命名空间
     */
    public static final String DB_NAMESPACE_CMATTRIBUTEMAP = "CmAttributeMap.";
    /**
     * 任务 ibatis命名空间
     */
    public static final String DB_NAMESPACE_PMSERIVCE = "pmSerivce.";
    /***
     * 虚拟机相关属性导出
     */
    public static final String DB_NAMESPACE_VMEXPORT = "VmExport.";
    
    public static final String SYSTEM_ORGANIZE = "autoCollect";
    public static final String SYSTEM_PARAMKEY = "maxColelctCount";
    
    public static final String CMDB_CINAME_X86 = "NJCMP_0_Pm_info_tab";
    /**
     * 全量文件上报名称头部分
     */
    public static final String CMREPORT_FILENAME_HEADER = "CMREPORT";
    
    public static final String PROPERTIESPATH = "/com/cloudmaster/cmp/resource/package.properties";
    public static final String SYSTEMPROPERTIESPATH = "/conf/other/System.properties";
    
    public static final String RESOURCE_MYSQL_FUNCTION_SERVERT_SQL_BEFORE = "CIDC-RT-ST-{0}";
    public static final boolean IS_PUBLIC_PATH = false;
    
    public static final String IP_ADD_LOCK_OBJECT = "ipaddlockobject";
}
