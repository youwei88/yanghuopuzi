/*
* FreightTemplate.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-09 14:26 Created
*/
package com.wmeimob.custom.yhpz.bean;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "freight_template")
public class FreightTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 运费模板编号
     */
    @Column(name = "freight_no")
    private String freightNo;

    /**
     * 仓库类型 20007保税仓 20008直邮地',
     */
    @Column(name = "house_type")
    private Integer houseType;

    /**
     * 仓库ID
     */
    @Column(name = "house_id")
    private Integer houseId;

    /**
     * 仓库名称
     */
    @Column(name = "house_name")
    private String houseName;

    /**
     * 加盟店ID
     */
    @Column(name = "lite_id")
    private Integer liteId;

    /**
     * 分公司ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 首重
     */
    @Column(name = "first_weight")
    private BigDecimal firstWeight;

    /**
     * 首费
     */
    @Column(name = "first_fee")
    private BigDecimal firstFee;

    /**
     * 续重
     */
    @Column(name = "renew_weight")
    private BigDecimal renewWeight;

    /**
     * 续费
     */
    @Column(name = "renew_fee")
    private BigDecimal renewFee;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 配送地区json信息
     */
    @Column(name = "address_json")
    private String addressJson;


    /**
     * 配送地址文字描述
     */
    @Column(name = "address_desc")
    private String addressDesc;

    @Transient
    private String cityIds;

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

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
     * 获取运费模板编号
     *
     * @return freight_no - 运费模板编号
     */
    public String getFreightNo() {
        return freightNo;
    }

    /**
     * 设置运费模板编号
     *
     * @param freightNo 运费模板编号
     */
    public void setFreightNo(String freightNo) {
        this.freightNo = freightNo;
    }

    /**
     * 获取仓库类型 20007保税仓 20008直邮地',
     *
     * @return house_type - 仓库类型 20007保税仓 20008直邮地',
     */
    public Integer getHouseType() {
        return houseType;
    }

    /**
     * 设置仓库类型 20007保税仓 20008直邮地',
     *
     * @param houseType 仓库类型 20007保税仓 20008直邮地',
     */
    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    /**
     * 获取仓库ID
     *
     * @return house_id - 仓库ID
     */
    public Integer getHouseId() {
        return houseId;
    }

    /**
     * 设置仓库ID
     *
     * @param houseId 仓库ID
     */
    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
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
     * 获取加盟店ID
     *
     * @return lite_id - 加盟店ID
     */
    public Integer getLiteId() {
        return liteId;
    }

    /**
     * 设置加盟店ID
     *
     * @param liteId 加盟店ID
     */
    public void setLiteId(Integer liteId) {
        this.liteId = liteId;
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
     * 获取首重
     *
     * @return first_weight - 首重
     */
    public BigDecimal getFirstWeight() {
        return firstWeight;
    }

    /**
     * 设置首重
     *
     * @param firstWeight 首重
     */
    public void setFirstWeight(BigDecimal firstWeight) {
        this.firstWeight = firstWeight;
    }

    /**
     * 获取首费
     *
     * @return first_fee - 首费
     */
    public BigDecimal getFirstFee() {
        return firstFee;
    }

    /**
     * 设置首费
     *
     * @param firstFee 首费
     */
    public void setFirstFee(BigDecimal firstFee) {
        this.firstFee = firstFee;
    }

    /**
     * 获取续重
     *
     * @return renew_weight - 续重
     */
    public BigDecimal getRenewWeight() {
        return renewWeight;
    }

    /**
     * 设置续重
     *
     * @param renewWeight 续重
     */
    public void setRenewWeight(BigDecimal renewWeight) {
        this.renewWeight = renewWeight;
    }

    /**
     * 获取续费
     *
     * @return renew_fee - 续费
     */
    public BigDecimal getRenewFee() {
        return renewFee;
    }

    /**
     * 设置续费
     *
     * @param renewFee 续费
     */
    public void setRenewFee(BigDecimal renewFee) {
        this.renewFee = renewFee;
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

    /**
     * 获取配送地区json信息
     *
     * @return address_json - 配送地区json信息
     */
    public String getAddressJson() {
        return addressJson;
    }

    /**
     * 设置配送地区json信息
     *
     * @param addressJson 配送地区json信息
     */
    public void setAddressJson(String addressJson) {
        this.addressJson = addressJson;
    }

    /**
     * 获取配送地址文字描述
     *
     * @return address_desc - 配送地址文字描述
     */
    public String getAddressDesc() {
        return addressDesc;
    }

    /**
     * 设置配送地址文字描述
     *
     * @param addressDesc 配送地址文字描述
     */
    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public static String validAdd(FreightTemplate[] freightTemplateArray) {
        for (FreightTemplate freightTemplate : freightTemplateArray) {
            if (freightTemplate.getHouseType() == null)
                return "发货地点类型未选择";
            if (freightTemplate.getHouseId() == null || StringUtils.isEmpty(freightTemplate.getHouseName()))
                return "发货地点名称不能为空";
//        if (StringUtils.isEmpty(freightTemplate.getAddressJson()))
            if (StringUtils.isEmpty(freightTemplate.getCityIds()))
                return "配送地址信息不能为空";
            if (StringUtils.isEmpty(freightTemplate.getAddressDesc()))
                return "配送地区不能为空";
            if (freightTemplate.getFirstWeight() == null
                    || freightTemplate.getRenewWeight() == null
                    || freightTemplate.getFirstFee() == null
                    || freightTemplate.getRenewFee() == null
                    )
                return "信息填写不完整";
        }
        return null;
    }
}