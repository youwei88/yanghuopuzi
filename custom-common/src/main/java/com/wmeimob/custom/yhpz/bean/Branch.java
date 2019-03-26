/*
* Branch.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-20 14:11 Created
*/ 
package com.wmeimob.custom.yhpz.bean;


import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *  分公司
 */
@Table(name = "branch")
public class Branch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "branch_no")
    private String branchNo;

    /**
     * 分公司名称
     */
    @Column(name = "branch_name")
    private String branchName;

    /**
     * 加盟店数量
     */
    @Column(name = "lite_count")
    private Integer liteCount;

    /**
     * 后台系统账号id
     */
    @Column(name = "sys_user_id")
    private Integer sysUserId;

    /**
     * 后台登陆账号
     */
    @Column(name = "sys_uname")
    private String sysUname;

    /**
     * 是否启用（上线）
     */
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return branch_no
     */
    public String getBranchNo() {
        return branchNo;
    }

    /**
     * @param branchNo
     */
    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    /**
     * 获取分公司名称
     *
     * @return branch_name - 分公司名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分公司名称
     *
     * @param branchName 分公司名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * 获取加盟店数量
     *
     * @return lite_count - 加盟店数量
     */
    public Integer getLiteCount() {
        return liteCount;
    }

    /**
     * 设置加盟店数量
     *
     * @param liteCount 加盟店数量
     */
    public void setLiteCount(Integer liteCount) {
        this.liteCount = liteCount;
    }

    /**
     * 获取后台系统账号id
     *
     * @return sys_user_id - 后台系统账号id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * 设置后台系统账号id
     *
     * @param sysUserId 后台系统账号id
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * 获取后台登陆账号
     *
     * @return sys_uname - 后台登陆账号
     */
    public String getSysUname() {
        return sysUname;
    }

    /**
     * 设置后台登陆账号
     *
     * @param sysUname 后台登陆账号
     */
    public void setSysUname(String sysUname) {
        this.sysUname = sysUname;
    }

    /**
     * 获取是否启用（上线）
     *
     * @return is_enabled - 是否启用（上线）
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否启用（上线）
     *
     * @param isEnabled 是否启用（上线）
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static void main(String[] args) {
    }

    public static String validAdd(Branch branch) {
        if(StringUtils.isEmpty(branch.getBranchName())){
            return "分公司名称不能为空";
        }
        return null;
    }
}