/*
* GoodsType.java
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

@Table(name = "goods_type")
public class GoodsType implements Serializable {
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
    @Column(name = "type_name")
    private String typeName;

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
    private List<GoodsType> goodsTypes;

    public List<GoodsType> getGoodsTypes() {
        return goodsTypes;
    }

    public void setGoodsTypes(List<GoodsType> goodsTypes) {
        this.goodsTypes = goodsTypes;
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