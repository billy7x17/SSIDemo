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
package com.cloudmaster.cmp.web.authority.user;

import java.util.List;

import com.cloudmaster.cmp.core.kmApi.object.UserBase;

/**
 * 用户信息属性的JavaBean
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version 1.0.0 18 Mar 2012
 */
public class UserInfo extends UserBase {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 办公电话
     */
    /* private String phone; */

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型
     */
    /* private String userType; */

    /**
     * 地址
     */
    /* private String address; */

    /**
     * 邮政编码
     */
    /* private String zipcode; */

    /**
     * 组织结构ID
     */
    /* private String organizationId; */

    /**
     * 组织结构名称
     */
    /* private String organizationName; */

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private String creatTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 员工号
     */
    private String personnelId;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 是否KM系统用户 0:是 1:否
     */
    /* private String systemUser; */

    /**
     * 用户模板类型 0:普通用户,1:管理人员
     */
    /* private String userTemplet; */

    /**
     * 用户岗位信息(可以是岗位名称或者是岗位ID)
     */
    /* private List<String> positionsInfo; */

    /**
     * 下一级虚拟岗位
     */
    /* private String nextVirtualLevel; */

    // 用户角色
    private List<String> selectvalue; 

    /**
     * 2012-07-17 zhaoc 添加 排序名称
     * @return
     */
    private String sortName;

    /**
     * 2012-07-17 zhaoc 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;

    /**
     * 用户所在站点ID
     */
    private Integer siteId;
    
    /**
     * 用户所在站点名称
     */
    private String siteName;
    
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 用户角色类型： 用于过滤信息
     */
    private String roleType;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * flexigrid查询字段名
     */
    private String qtype;

    /**
     * flexigrid查询字段值
     */
    private String query;

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(",userName:" + userName);
        sb.append(",status:" + status);
        sb.append(",creatTime:" + creatTime);
        sb.append(",updateTime:" + updateTime);
        return sb.toString();

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
    }

    /**
     * @return the selectvalue
     */
    public List<String> getSelectvalue() {
        return selectvalue;
    }

    /**
     * @param selectvalue the selectvalue to set
     */
    public void setSelectvalue(List<String> selectvalue) {
        this.selectvalue = selectvalue;
    }

    /**
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param roleType the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return the siteId
     */
    public Integer getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * @return Returns the roleName.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName The roleName to set.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
