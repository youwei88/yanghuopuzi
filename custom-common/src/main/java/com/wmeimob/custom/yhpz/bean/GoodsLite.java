/*
* GoodsLite.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-01 15:40 Created
*/ 
package com.wmeimob.custom.yhpz.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 加盟店商品
 */
@Table(name = "goods_lite")
public class GoodsLite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 加盟店ID
     */
    @Column(name = "lite_id")
    private Integer liteId;

    @Column(name = "lite_name")
    private String liteName;

    /**
     * 商品ID
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 销售价
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 成本
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 排序值
     */
    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 可获积分
     */
    private Integer points;

    /**
     * 销量
     */
    @Column(name = "sale_count")
    private Integer saleCount;

    /**
     * 实际销量
     */
    @Column(name = "really_sale_count")
    private Integer reallySaleCount;

    /**
     * 是否上架
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


    public String getLiteName() {
        return liteName;
    }

    public void setLiteName(String liteName) {
        this.liteName = liteName;
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
     * 获取商品ID
     *
     * @return goods_id - 商品ID
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品ID
     *
     * @param goodsId 商品ID
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取销售价
     *
     * @return sale_price - 销售价
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 设置销售价
     *
     * @param salePrice 销售价
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 获取成本
     *
     * @return cost_price - 成本
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * 设置成本
     *
     * @param costPrice 成本
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取排序值
     *
     * @return order_no - 排序值
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 设置排序值
     *
     * @param orderNo 排序值
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取可获积分
     *
     * @return points - 可获积分
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * 设置可获积分
     *
     * @param points 可获积分
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * 获取销量
     *
     * @return sale_count - 销量
     */
    public Integer getSaleCount() {
        return saleCount;
    }

    /**
     * 设置销量
     *
     * @param saleCount 销量
     */
    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * 获取实际销量
     *
     * @return really_sale_count - 实际销量
     */
    public Integer getReallySaleCount() {
        return reallySaleCount;
    }

    /**
     * 设置实际销量
     *
     * @param reallySaleCount 实际销量
     */
    public void setReallySaleCount(Integer reallySaleCount) {
        this.reallySaleCount = reallySaleCount;
    }

    /**
     * 获取是否上架
     *
     * @return is_enabled - 是否上架
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否上架
     *
     * @param isEnabled 是否上架
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}