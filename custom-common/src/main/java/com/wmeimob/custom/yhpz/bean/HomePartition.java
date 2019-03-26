/*
* HomePartition.java
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
import java.util.List;

@Table(name = "home_partition")
public class HomePartition implements Serializable {
    public static final Integer PARTITION_GOODS = 20009;
    public static final Integer PARTITION_TYPE = 20010;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 加盟店ID
     */
    @Column(name = "lite_id")
    private Integer liteId;

    /**
     * 版块名称
     */
    @Column(name = "partition_name")
    private String partitionName;

    /**
     * 版块主图
     */
    private String img;

    /**
     * 20009 链接类型-商品 20010 链接类型-分类
     */
    @Column(name = "partition_type")
    private Integer partitionType;

    /**
     * 排序号
     */
    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 数据ID
     */
    @Column(name = "data_id")
    private String dataId;

    /**
     * 父类ID 0为顶级
     */
    @Column(name = "parent_id")
    private Integer parentId;

    @Transient
    private List<HomePartition> homePartitions;

    @Transient
    private Integer typeId;

    @Transient
    private String goodsNo;


    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public List<HomePartition> getHomePartitions() {
        return homePartitions;
    }

    public void setHomePartitions(List<HomePartition> homePartitions) {
        this.homePartitions = homePartitions;
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
     * 获取版块名称
     *
     * @return partition_name - 版块名称
     */
    public String getPartitionName() {
        return partitionName;
    }

    /**
     * 设置版块名称
     *
     * @param partitionName 版块名称
     */
    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    /**
     * 获取版块主图
     *
     * @return img - 版块主图
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置版块主图
     *
     * @param img 版块主图
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取20009 链接类型-商品 20010 链接类型-分类
     *
     * @return partition_type - 20009 链接类型-商品 20010 链接类型-分类
     */
    public Integer getPartitionType() {
        return partitionType;
    }

    /**
     * 设置20009 链接类型-商品 20010 链接类型-分类
     *
     * @param partitionType 20009 链接类型-商品 20010 链接类型-分类
     */
    public void setPartitionType(Integer partitionType) {
        this.partitionType = partitionType;
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
     * 获取父类ID 0为顶级
     *
     * @return parent_id - 父类ID 0为顶级
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父类ID 0为顶级
     *
     * @param parentId 父类ID 0为顶级
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public static String validAdd(HomePartition partition) {
        if (partition.getPartitionType() == null)
            return "未指明链接类型";
        if (partition.getHomePartitions().size() > 0) {
            partition.getHomePartitions().removeIf(p -> p.getPartitionType() == null);
        }
        return null;
    }

}