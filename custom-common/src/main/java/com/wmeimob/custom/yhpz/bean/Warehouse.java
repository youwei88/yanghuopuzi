/*
* Warehouse.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-05 19:42 Created
*/ 
package com.wmeimob.custom.yhpz.bean;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 保税仓直邮地
 */
@Table(name = "warehouse")
public class Warehouse implements Serializable {


    //保税仓
    public static final Integer HOUSE_TYPE_TAX = 20007;

    public static final Integer HOUSE_TYPE_POST = 20008;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分公司ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 编号
     */
    @Column(name = "house_no")
    private String houseNo;

    /**
     * 仓库类型 20007保税仓 20008直邮地
     */
    @Column(name = "house_type")
    private Integer houseType;

    /**
     * 仓库名称
     */
    @Column(name = "house_name")
    private String houseName;

    /**
     * 免邮金额
     */
    @Column(name = "free_post")
    private BigDecimal freePost;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_at")
    private Date createdAt;

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
     * 获取分公司ID
     *
     * @return branch_id - 分公司ID
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * 设置分公司ID
     *
     * @param branchId 分公司ID
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * 获取编号
     *
     * @return house_no - 编号
     */
    public String getHouseNo() {
        return houseNo;
    }

    /**
     * 设置编号
     *
     * @param houseNo 编号
     */
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    /**
     * 获取仓库类型 20007保税仓 20008直邮地
     *
     * @return house_type - 仓库类型 20007保税仓 20008直邮地
     */
    public Integer getHouseType() {
        return houseType;
    }

    /**
     * 设置仓库类型 20007保税仓 20008直邮地
     *
     * @param houseType 仓库类型 20007保税仓 20008直邮地
     */
    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    /**
     * 获取仓库名称
     *
     * @return house_name - 仓库名称
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * 设置仓库名称
     *
     * @param houseName 仓库名称
     */
    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    /**
     * 获取免邮金额
     *
     * @return free_post - 免邮金额
     */
    public BigDecimal getFreePost() {
        return freePost;
    }

    /**
     * 设置免邮金额
     *
     * @param freePost 免邮金额
     */
    public void setFreePost(BigDecimal freePost) {
        this.freePost = freePost;
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

    public static String validAdd(Warehouse warehouse) {
        if(warehouse.getHouseType()==null)
            return "未选中类型";
        if(StringUtils.isEmpty(warehouse.getHouseName()))
            return "名称不能为空";
        if(warehouse.getFreePost()==null)
            return "免运费金额不可为空，如包邮请填写0";
        return null;
    }
}