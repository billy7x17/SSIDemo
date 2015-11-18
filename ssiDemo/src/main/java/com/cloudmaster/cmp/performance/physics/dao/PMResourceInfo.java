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
package com.cloudmaster.cmp.performance.physics.dao;

import java.io.Serializable;
import java.util.Date;

/** 
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class PMResourceInfo implements Serializable {
    private static final long serialVersionUID = -5184178155136975387L;
    /**
     * 主机或虚拟IP
     */
    private String perHID;
    /**
     * 启动的空闲CPU百分比
     */
    private String cpuAidle;
    /**
     * 空闲CPU百分比
     */
    private String cpuIdle;
    /**
     * 用户进程空间内改变过优先级的进程占用CPU百分比
     */
    private String cpuNice;
    /**
     * CPU线程总数
     */
    private String cpuNum;
    /**
     * CPU速度（MHz）
     */
    private String cpuSpeed;
    /**
     * 内核空间占用CPU百分比
     */
    private String cpuSystem;
    /**
     * 用户空间占用CPU百分比
     */
    private String cpuUser;
    /**
     * cpu空闲时的最大I/O请求
     */
    private String cpuWio;
    /**
     * 剩余磁盘空间
     */
    private String diskFree;
    /**
     * 磁盘总大小
     */
    private String diskTotal;
    /**
     * 磁盘的每秒读字节数
     */
    private String diskRead;
    /**
     * 磁盘的每秒写字节数
     */
    private String diskWrite;
    /**
     * 磁盘IO利用率
     */
    private String diskIO;
    /**
     * 系统磁盘读取速率
     */
    private String diskReadSDA;
    /**
     * 系统磁盘写入速率
     */
    private String diskWriteSDA;
    /**
     * 磁盘IO利用率
     */
    private String diskIOSDA;
    /**
     * 磁盘使用率
     */
    private String diskPercent;
    /**
     * Maximum percent used for all partitions
     */
    private String partMaxUsed;
    /**
     * 每15分钟的系统平均负载
     */
    private String loadFifteen;
    /**
     * 每5分钟的系统平均负载
     */
    private String loadFive;
    /**
     * 每分钟的系统平均负载
     */
    private String loadOne;
    /**
     * 内存使用百分比
     */
    private String memPercent;
    /**
     * 内核缓存的内存总量
     */
    private String memBuffers;
    /**
     * 缓存内存大小
     */
    private String memCached;
    /**
     * 空闲内存大小
     */
    private String memFree;
    /**
     * 共享内存大小
     */
    private String memShared;
    /**
     * 物理内存总量
     */
    private String memTotal;
    /**
     * 空闲交换分区大小
     */
    private String swapFree;
    /**
     * 交换分区总量
     */
    private String swapTotal;
    /**
     * 交换分区使用率
     */
    private String swapPercent;
    /**
     * 每秒进来字节数
     */
    private String bytesIn;
    /**
     * 每秒出去字节数
     */
    private String bytesOut;
    /**
     * 每秒进来的包
     */
    private String pktsIn;
    /**
     * 每秒出去的包
     */
    private String pktsOut;
    /**
     * 运行的进程总数
     */
    private String procRun;
    /**
     * 进程总数
     */
    private String procTotal;
    /**
     * 系统启动时间
     */
    private String bootTime;
    /**
     * 系统版本（X86或64）
     */
    private String machineType;
    /**
     * 操作系统名字
     */
    private String osName;
    /**
     * 操作系统版本
     */
    private String osRelease;
    /**
     * Gexec
     */
    private String gexec;
    public String getDiskIO() {
        return diskIO;
    }
    public void setDiskIO(String diskIO) {
        this.diskIO = diskIO;
    }
    public String getDiskIOSDA() {
        return diskIOSDA;
    }
    public void setDiskIOSDA(String diskIOSDA) {
        this.diskIOSDA = diskIOSDA;
    }
    /**
     * FLAG
     */
    private String flag;
    /**
     *
     */
    private String diskFreeRootfs;
    /**
     * 采集时间
     */
    private Date perTime;
    //get和set方法
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getGexec() {
        return gexec;
    }
    public void setGexec(String gexec) {
        this.gexec = gexec;
    }
    public String getDiskFreeRootfs() {
        return diskFreeRootfs;
    }
    public void setDiskFreeRootfs(String diskFreeRootfs) {
        this.diskFreeRootfs = diskFreeRootfs;
    }
    public String getPerHID() {
        return perHID;
    }
    public String getSwapPercent() {
        return swapPercent;
    }
    public void setSwapPercent(String swapPercent) {
        this.swapPercent = swapPercent;
    }
    public void setPerHID(String perHID) {
        this.perHID = perHID;
    }
    public String getCpuAidle() {
        return cpuAidle;
    }
    public void setCpuAidle(String cpuAidle) {
        this.cpuAidle = cpuAidle;
    }
    public String getCpuIdle() {
        return cpuIdle;
    }
    public void setCpuIdle(String cpuIdle) {
        this.cpuIdle = cpuIdle;
    }
    public String getCpuNice() {
        return cpuNice;
    }
    public String getMemPercent() {
        return memPercent;
    }
    public void setMemPercent(String memPercent) {
        this.memPercent = memPercent;
    }
    public String getDiskReadSDA() {
        return diskReadSDA;
    }
    public void setDiskReadSDA(String diskReadSDA) {
        this.diskReadSDA = diskReadSDA;
    }
    public String getDiskWriteSDA() {
        return diskWriteSDA;
    }
    public void setDiskWriteSDA(String diskWriteSDA) {
        this.diskWriteSDA = diskWriteSDA;
    }
    public void setCpuNice(String cpuNice) {
        this.cpuNice = cpuNice;
    }
    public String getCpuNum() {
        return cpuNum;
    }
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }
    public String getCpuSpeed() {
        return cpuSpeed;
    }
    public void setCpuSpeed(String cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }
    public String getCpuSystem() {
        return cpuSystem;
    }
    public void setCpuSystem(String cpuSystem) {
        this.cpuSystem = cpuSystem;
    }
    public String getCpuUser() {
        return cpuUser;
    }
    public void setCpuUser(String cpuUser) {
        this.cpuUser = cpuUser;
    }
    public String getCpuWio() {
        return cpuWio;
    }
    public void setCpuWio(String cpuWio) {
        this.cpuWio = cpuWio;
    }
    public String getDiskFree() {
        return diskFree;
    }
    public void setDiskFree(String diskFree) {
        this.diskFree = diskFree;
    }
    public String getDiskTotal() {
        return diskTotal;
    }
    public void setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
    }
    public String getPartMaxUsed() {
        return partMaxUsed;
    }
    public void setPartMaxUsed(String partMaxUsed) {
        this.partMaxUsed = partMaxUsed;
    }
    public String getLoadFifteen() {
        return loadFifteen;
    }
    public void setLoadFifteen(String loadFifteen) {
        this.loadFifteen = loadFifteen;
    }
    public String getLoadFive() {
        return loadFive;
    }
    public void setLoadFive(String loadFive) {
        this.loadFive = loadFive;
    }
    public String getLoadOne() {
        return loadOne;
    }
    public String getDiskPercent() {
        return diskPercent;
    }
    public void setDiskPercent(String diskPercent) {
        this.diskPercent = diskPercent;
    }
    public void setLoadOne(String loadOne) {
        this.loadOne = loadOne;
    }
    public String getMemBuffers() {
        return memBuffers;
    }
    public void setMemBuffers(String memBuffers) {
        this.memBuffers = memBuffers;
    }
    public String getMemCached() {
        return memCached;
    }
    public void setMemCached(String memCached) {
        this.memCached = memCached;
    }
    public String getMemFree() {
        return memFree;
    }
    public void setMemFree(String memFree) {
        this.memFree = memFree;
    }
    public String getMemShared() {
        return memShared;
    }
    public void setMemShared(String memShared) {
        this.memShared = memShared;
    }
    public String getMemTotal() {
        return memTotal;
    }
    public void setMemTotal(String memTotal) {
        this.memTotal = memTotal;
    }
    public String getSwapFree() {
        return swapFree;
    }
    public void setSwapFree(String swapFree) {
        this.swapFree = swapFree;
    }
    public String getSwapTotal() {
        return swapTotal;
    }
    public String getDiskRead() {
        return diskRead;
    }
    public void setDiskRead(String diskRead) {
        this.diskRead = diskRead;
    }
    public String getDiskWrite() {
        return diskWrite;
    }
    public void setDiskWrite(String diskWrite) {
        this.diskWrite = diskWrite;
    }
    public void setSwapTotal(String swapTotal) {
        this.swapTotal = swapTotal;
    }
    public String getBytesIn() {
        return bytesIn;
    }
    public void setBytesIn(String bytesIn) {
        this.bytesIn = bytesIn;
    }
    public String getBytesOut() {
        return bytesOut;
    }
    public void setBytesOut(String bytesOut) {
        this.bytesOut = bytesOut;
    }
    public String getPktsIn() {
        return pktsIn;
    }
    public void setPktsIn(String pktsIn) {
        this.pktsIn = pktsIn;
    }
    public String getPktsOut() {
        return pktsOut;
    }
    public void setPktsOut(String pktsOut) {
        this.pktsOut = pktsOut;
    }
    public String getProcRun() {
        return procRun;
    }
    public void setProcRun(String procRun) {
        this.procRun = procRun;
    }
    public String getProcTotal() {
        return procTotal;
    }
    public void setProcTotal(String procTotal) {
        this.procTotal = procTotal;
    }
    public String getBootTime() {
        return bootTime;
    }
    public void setBootTime(String bootTime) {
        this.bootTime = bootTime;
    }
    public String getMachineType() {
        return machineType;
    }
    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }
    public String getOsName() {
        return osName;
    }
    public void setOsName(String osName) {
        this.osName = osName;
    }
    public String getOsRelease() {
        return osRelease;
    }
    public void setOsRelease(String osRelease) {
        this.osRelease = osRelease;
    }
    public Date getPerTime() {
        return perTime;
    }
    public void setPerTime(Date perTime) {
        this.perTime = perTime;
    }
}
