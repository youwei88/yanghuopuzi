/*
* HomeTypeSet.java
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

@Table(name = "home_type_set")
public class HomeTypeSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 加盟店ID
     */
    @Column(name = "lite_id")
    private Integer liteId;

    /**
     * 分类LOGO
     */
    private String logo;

    /**
     * 分类ID
     */
    @Column(name = "type_id")
    private Integer typeId;

    /**
     * 分类名称
     */
    @Column(name = "type_name")
    private String typeName;

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
     * 获取分类LOGO
     *
     * @return logo - 分类LOGO
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置分类LOGO
     *
     * @param logo 分类LOGO
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取分类ID
     *
     * @return type_id - 分类ID
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置分类ID
     *
     * @param typeId 分类ID
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取分类名称
     *
     * @return type_name - 分类名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置分类名称
     *
     * @param typeName 分类名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public static String validAdd(HomeTypeSet homeTypeSet) {
        if(StringUtils.isEmpty(homeTypeSet.getLogo()))
            return "分类LOGO未上传";
        if(homeTypeSet.getTypeId()==null)
            return "未指定分类";
        if(homeTypeSet.getTypeName()==null)
            return "未指定分类名称";
        return null;
    }
}