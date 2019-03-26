/*
* Lite.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-23 16:38 Created
*/ 
package com.wmeimob.custom.yhpz.bean;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 *  加盟店
 */
@Table(name = "lite")
public class Lite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 加盟店编号
     */
    @Column(name = "lite_no")
    private String liteNo;

    /**
     * 加盟店名称
     */
    @Column(name = "lite_name")
    private String liteName;


    /**
     * 分公司ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    @Column(name = "user_count")
    private Integer userCount;

    @Column(name = "free_post")
    private BigDecimal freePost;

    /**
     * 是否是自营
     */
    @Column(name = "is_self")
    private Boolean isSelf;

    /**
     * 可否允许自提
     */
    @Column(name = "self_delivery_support")
    private Boolean selfDeliverySupport;

    /**
     * 可否自主上架
     */
    @Column(name = "allowed_self_shelves")
    private Boolean allowedSelfShelves;

    /**
     * 电话
     */
    private String tel;


    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 地区
     */
    private Integer area;

    /**
     * 地址
     */
    private String address;

    /**
     * 积分抵扣设置
     */
    @Column(name = "points_offset_rate")
    private  BigDecimal pointsOffsetRate;

    /**
     * 积分获取比例
     */
    @Column(name = "points_get_rate")
    private BigDecimal pointsGetRate;

    /**
     * 积分与人民币兑换比例
     */
    @Column(name = "points_rmb_rate")
    private BigDecimal pointsRmbRate;



    @Column(name = "sys_user_id")
    private Integer sysUserId;

    @Column(name = "sys_uname")
    private String sysUname;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 是否上线
     */
    @Column(name = "is_enabled")
    private Boolean isEnabled;

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
     * 获取加盟店编号
     *
     * @return lite_no - 加盟店编号
     */
    public String getLiteNo() {
        return liteNo;
    }

    /**
     * 设置加盟店编号
     *
     * @param liteNo 加盟店编号
     */
    public void setLiteNo(String liteNo) {
        this.liteNo = liteNo;
    }

    /**
     * 获取加盟店名称
     *
     * @return lite_name - 加盟店名称
     */
    public String getLiteName() {
        return liteName;
    }

    /**
     * 设置加盟店名称
     *
     * @param liteName 加盟店名称
     */
    public void setLiteName(String liteName) {
        this.liteName = liteName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPointsOffsetRate() {
        return pointsOffsetRate;
    }

    public void setPointsOffsetRate(BigDecimal pointsOffsetRate) {
        this.pointsOffsetRate = pointsOffsetRate;
    }

    public BigDecimal getPointsGetRate() {
        return pointsGetRate;
    }

    public void setPointsGetRate(BigDecimal pointsGetRate) {
        this.pointsGetRate = pointsGetRate;
    }

    public BigDecimal getPointsRmbRate() {
        return pointsRmbRate;
    }

    public void setPointsRmbRate(BigDecimal pointsRmbRate) {
        this.pointsRmbRate = pointsRmbRate;
    }

    public BigDecimal getFreePost() {
        return freePost;
    }

    public void setFreePost(BigDecimal freePost) {
        this.freePost = freePost;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    /**
     * 获取是否是自营
     *
     * @return is_self - 是否是自营
     */
    public Boolean getIsSelf() {
        return isSelf;
    }

    /**
     * 设置是否是自营
     *
     * @param isSelf 是否是自营
     */
    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    /**
     * 获取可否允许自提
     *
     * @return self_delivery_support - 可否允许自提
     */
    public Boolean getSelfDeliverySupport() {
        return selfDeliverySupport;
    }

    /**
     * 设置可否允许自提
     *
     * @param selfDeliverySupport 可否允许自提
     */
    public void setSelfDeliverySupport(Boolean selfDeliverySupport) {
        this.selfDeliverySupport = selfDeliverySupport;
    }

    /**
     * 获取可否自主上架
     *
     * @return allowed_self_shelves - 可否自主上架
     */
    public Boolean getAllowedSelfShelves() {
        return allowedSelfShelves;
    }

    /**
     * 设置可否自主上架
     *
     * @param allowedSelfShelves 可否自主上架
     */
    public void setAllowedSelfShelves(Boolean allowedSelfShelves) {
        this.allowedSelfShelves = allowedSelfShelves;
    }

    /**
     * @return sys_user_id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * @param sysUserId
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * @return sys_uname
     */
    public String getSysUname() {
        return sysUname;
    }

    /**
     * @param sysUname
     */
    public void setSysUname(String sysUname) {
        this.sysUname = sysUname;
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
     * 获取是否上线
     *
     * @return is_enabled - 是否上线
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否上线
     *
     * @param isEnabled 是否上线
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public static String validAdd(Lite lite) {
        Assert.notNull(lite.getLiteName(),"加盟店名称不能为空");
        lite.setIsSelf(lite.getIsSelf() != null);
        lite.setSelfDeliverySupport(lite.getSelfDeliverySupport() != null);
        lite.setAllowedSelfShelves(lite.getAllowedSelfShelves() != null);
        return null;
    }
}