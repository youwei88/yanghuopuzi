/*
* Banner.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-14 13:44 Created
*/
package com.wmeimob.custom.yhpz.bean;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "banner")
public class Banner implements Serializable {
    public static final Integer HREF_TYPE_GOODS = 20009;
    public static final Integer HREF_TYPE = 20010;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 加盟店ID
     */
    @Column(name = "lite_id")
    private Integer liteId;

    /**
     * banner类型 （为空表示首页）
     */
    @Column(name = "banner_type")
    private Integer bannerType;

    /**
     * 图片地址
     */
    private String img;

    /**
     * 20009 链接类型-商品 20010 链接类型-分类
     */
    @Column(name = "href_type")
    private Integer hrefType;

    /**
     * 数据ID
     */
    @Column(name = "data_id")
    private String dataId;

    /**
     * 数据名称
     */
    @Column(name = "data_name")
    private String dataName;

    /**
     * 排序号
     */
    @Column(name = "order_no")
    private Integer orderNo;

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
     * 获取banner类型 （为空表示首页）
     *
     * @return banner_type - banner类型 （为空表示首页）
     */
    public Integer getBannerType() {
        return bannerType;
    }

    /**
     * 设置banner类型 （为空表示首页）
     *
     * @param bannerType banner类型 （为空表示首页）
     */
    public void setBannerType(Integer bannerType) {
        this.bannerType = bannerType;
    }

    /**
     * 获取图片地址
     *
     * @return img - 图片地址
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置图片地址
     *
     * @param img 图片地址
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取20009 链接类型-商品 20010 链接类型-分类
     *
     * @return href_type - 20009 链接类型-商品 20010 链接类型-分类
     */
    public Integer getHrefType() {
        return hrefType;
    }

    /**
     * 设置20009 链接类型-商品 20010 链接类型-分类
     *
     * @param hrefType 20009 链接类型-商品 20010 链接类型-分类
     */
    public void setHrefType(Integer hrefType) {
        this.hrefType = hrefType;
    }

    /**
     * 获取数据ID
     *
     * @return data_id - 数据ID
     */
    public String getDataId() {
        return dataId;
    }

    /**
     * 设置数据ID
     *
     * @param dataId 数据ID
     */
    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    /**
     * 获取数据名称
     *
     * @return data_name - 数据名称
     */
    public String getDataName() {
        return dataName;
    }

    /**
     * 设置数据名称
     *
     * @param dataName 数据名称
     */
    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    /**
     * 获取排序号
     *
     * @return order_no - 排序号
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 设置排序号
     *
     * @param orderNo 排序号
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public static String validAdd(Banner banner) {
        if (banner.getHrefType() == null)
            return "类型未选择";
        if (StringUtils.isEmpty(banner.getDataId()))
            return "数据ID未指定";
        if (Banner.HREF_TYPE.equals(banner.getHrefType())&&StringUtils.isEmpty(banner.getDataName()))
            return "数据名称不能为空";
        if (StringUtils.isEmpty(banner.getImg()))
            return "未上传图片";
        return null;
    }
}