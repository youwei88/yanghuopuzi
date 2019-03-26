/*
* GoodsBrand.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-07-20 17:29 Created
*/ 
package com.wmeimob.custom.yhpz.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "goods_brand")
public class GoodsBrand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 分公司ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 分类名称
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 分类logo
     */
    private String img;

    /**
     * 父级分类（0为顶级）
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 排序号
     */
    @Column(name = "order_no")
    private Integer orderNo;


    @Transient
    private List<GoodsBrand> goodsBrands;


    public List<GoodsBrand> getGoodsBrands() {
        return goodsBrands;
    }

    public void setGoodsBrands(List<GoodsBrand> goodsBrands) {
        this.goodsBrands = goodsBrands;
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

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * 获取分类名称
     *
     * @return brand_name - 分类名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置分类名称
     *
     * @param brandName 分类名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取分类logo
     *
     * @return img - 分类logo
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置分类logo
     *
     * @param img 分类logo
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取父级分类（0为顶级）
     *
     * @return parent_id - 父级分类（0为顶级）
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级分类（0为顶级）
     *
     * @param parentId 父级分类（0为顶级）
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}