/*
* Goods.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-07-31 17:41 Created
*/
package com.wmeimob.custom.yhpz.bean;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品表
 */
@Table(name = "goods")
public class Goods implements Serializable {

    //普通商品
    public static final Integer GOODS_PROP_NORMAL = 20001;

    //直邮商品
    public static final Integer GOODS_PROP_TAX = 20002;


    //保税商品
    public static final Integer GOODS_PROP_POST = 20003;
    @Transient
    private Integer liteId;

    @Transient
    private String liteName;

    @Transient
    private String queryKey;

    public String getQueryKey() {
        return queryKey;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }

    public String getLiteName() {
        return liteName;
    }

    public void setLiteName(String liteName) {
        this.liteName = liteName;
    }

    /**
     * 分公司ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    public Integer getLiteId() {
        return liteId;
    }

    public void setLiteId(Integer liteId) {
        this.liteId = liteId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品编号
     */
    @Column(name = "goods_no")
    private String goodsNo;

    /**
     * 商品类型ID
     */
    @Column(name = "type_id")
    private Integer typeId;

    /**
     * 商品品牌ID
     */
    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 类型名称
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * 品牌名称
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 完整的类型ID
     */
    @Column(name = "full_type_id")
    private String fullTypeId;

    /**
     * 完整的品牌ID
     */
    @Column(name = "full_brand_id")
    private String fullBrandId;

    /**
     * 商品属性（20001普通，20002保税，20003直邮）
     */
    @Column(name = "goods_prop")
    private Integer goodsProp;

    /**
     * 商品属性名称
     */
    @Column(name = "goods_prop_name")
    private String goodsPropName;


    /**
     * 保税仓ID
     */
    @Column(name = "tax_id")
    private Integer taxId;


    /**
     * 保税仓名称
     */
    @Column(name = "tax_name")
    private String taxName;


    /**
     * 直邮地id
     */
    @Column(name = "post_id")
    private Integer postId;


    /**
     * 直邮地名称
     */
    @Column(name = "post_name")
    private String postName;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * sn码
     */
    @Column(name = "serial_no")
    private String serialNo;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 售卖属性（20004热卖 20005促销 20006爆款）
     */
    @Column(name = "sale_prop")
    private Integer saleProp;

    /**
     * 保质期
     */
    @Column(name = "sheif_life")
    private String sheifLife;

    /**
     * 产地
     */
    @Column(name = "made_in")
    private String madeIn;

    /**
     * 原产地
     */
    private String orign;

    /**
     * 重量（最大支持9999.999kg）
     */
    private BigDecimal weight;

    /**
     * 指导价
     */
    @Column(name = "guide_price")
    private BigDecimal guidePrice;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 成本价
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 出售价（若为空取指导价）
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 最低销售价
     */
    @Column(name = "sale_price_min")
    private BigDecimal salePriceMin;

    /**
     * 最高销售价
     */
    @Column(name = "sale_price_max")
    private BigDecimal salePriceMax;

    /**
     * 排序值（顺序）
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
     * 真实销量
     */
    @Column(name = "really_sale_count")
    private Integer reallySaleCount;

    /**
     * 是否包邮
     */
    @Column(name = "free_shipping")
    private Boolean freeShipping;

    /**
     * 商品logo
     */
    private String logo;

    /**
     * 商品轮播图
     */
    private String banners;

    /**
     * 是否上架
     */
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 富文本详情
     */
    private String details;

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
     * 获取商品编号
     *
     * @return goods_no - 商品编号
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * 设置商品编号
     *
     * @param goodsNo 商品编号
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    /**
     * 获取商品类型ID
     *
     * @return type_id - 商品类型ID
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置商品类型ID
     *
     * @param typeId 商品类型ID
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取商品品牌ID
     *
     * @return brand_id - 商品品牌ID
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * 设置商品品牌ID
     *
     * @param brandId 商品品牌ID
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取类型名称
     *
     * @return type_name - 类型名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置类型名称
     *
     * @param typeName 类型名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取品牌名称
     *
     * @return brand_name - 品牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brandName 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取完整的类型ID
     *
     * @return full_type_id - 完整的类型ID
     */
    public String getFullTypeId() {
        return fullTypeId;
    }

    /**
     * 设置完整的类型ID
     *
     * @param fullTypeId 完整的类型ID
     */
    public void setFullTypeId(String fullTypeId) {
        this.fullTypeId = fullTypeId;
    }

    /**
     * 获取完整的品牌ID
     *
     * @return full_brand_id - 完整的品牌ID
     */
    public String getFullBrandId() {
        return fullBrandId;
    }

    /**
     * 设置完整的品牌ID
     *
     * @param fullBrandId 完整的品牌ID
     */
    public void setFullBrandId(String fullBrandId) {
        this.fullBrandId = fullBrandId;
    }

    /**
     * 获取商品属性（20001普通，20002保税，20003直邮）
     *
     * @return goods_prop - 商品属性（20001普通，20002保税，20003直邮）
     */
    public Integer getGoodsProp() {
        return goodsProp;
    }

    /**
     * 设置商品属性（20001普通，20002保税，20003直邮）
     *
     * @param goodsProp 商品属性（20001普通，20002保税，20003直邮）
     */
    public void setGoodsProp(Integer goodsProp) {
        this.goodsProp = goodsProp;
    }

    /**
     * 获取商品属性名称
     *
     * @return goods_prop_name - 商品属性名称
     */
    public String getGoodsPropName() {
        return goodsPropName;
    }

    /**
     * 设置商品属性名称
     *
     * @param goodsPropName 商品属性名称
     */
    public void setGoodsPropName(String goodsPropName) {
        this.goodsPropName = goodsPropName;
    }

    public Integer gettaxId() {
        return taxId;
    }

    public void settaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String gettaxName() {
        return taxName;
    }

    public void settaxName(String taxName) {
        this.taxName = taxName;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    /**
     * 获取商品名称
     *
     * @return goods_name - 商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名称
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取sn码
     *
     * @return serial_no - sn码
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * 设置sn码
     *
     * @param serialNo sn码
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 获取规格
     *
     * @return spec - 规格
     */
    public String getSpec() {
        return spec;
    }

    /**
     * 设置规格
     *
     * @param spec 规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取售卖属性（20004热卖 20005促销 20006爆款）
     *
     * @return sale_prop - 售卖属性（20004热卖 20005促销 20006爆款）
     */
    public Integer getSaleProp() {
        return saleProp;
    }

    /**
     * 设置售卖属性（20004热卖 20005促销 20006爆款）
     *
     * @param saleProp 售卖属性（20004热卖 20005促销 20006爆款）
     */
    public void setSaleProp(Integer saleProp) {
        this.saleProp = saleProp;
    }

    /**
     * 获取保质期
     *
     * @return sheif_life - 保质期
     */
    public String getSheifLife() {
        return sheifLife;
    }

    /**
     * 设置保质期
     *
     * @param sheifLife 保质期
     */
    public void setSheifLife(String sheifLife) {
        this.sheifLife = sheifLife;
    }

    /**
     * 获取产地
     *
     * @return made_in - 产地
     */
    public String getMadeIn() {
        return madeIn;
    }

    /**
     * 设置产地
     *
     * @param madeIn 产地
     */
    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    /**
     * 获取原产地
     *
     * @return orign - 原产地
     */
    public String getOrign() {
        return orign;
    }

    /**
     * 设置原产地
     *
     * @param orign 原产地
     */
    public void setOrign(String orign) {
        this.orign = orign;
    }

    /**
     * 获取重量（最大支持9999.999kg）
     *
     * @return weight - 重量（最大支持9999.999kg）
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置重量（最大支持9999.999kg）
     *
     * @param weight 重量（最大支持9999.999kg）
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取指导价
     *
     * @return guide_price - 指导价
     */
    public BigDecimal getGuidePrice() {
        return guidePrice;
    }

    /**
     * 设置指导价
     *
     * @param guidePrice 指导价
     */
    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价
     *
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取成本价
     *
     * @return cost_price - 成本价
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * 设置成本价
     *
     * @param costPrice 成本价
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取出售价（若为空取指导价）
     *
     * @return sale_price - 出售价（若为空取指导价）
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 设置出售价（若为空取指导价）
     *
     * @param salePrice 出售价（若为空取指导价）
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 获取最低销售价
     *
     * @return sale_price_min - 最低销售价
     */
    public BigDecimal getSalePriceMin() {
        return salePriceMin;
    }

    /**
     * 设置最低销售价
     *
     * @param salePriceMin 最低销售价
     */
    public void setSalePriceMin(BigDecimal salePriceMin) {
        this.salePriceMin = salePriceMin;
    }

    /**
     * 获取最高销售价
     *
     * @return sale_price_max - 最高销售价
     */
    public BigDecimal getSalePriceMax() {
        return salePriceMax;
    }

    /**
     * 设置最高销售价
     *
     * @param salePriceMax 最高销售价
     */
    public void setSalePriceMax(BigDecimal salePriceMax) {
        this.salePriceMax = salePriceMax;
    }

    /**
     * 获取排序值（顺序）
     *
     * @return order_no - 排序值（顺序）
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 设置排序值（顺序）
     *
     * @param orderNo 排序值（顺序）
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
     * 获取真实销量
     *
     * @return really_sale_count - 真实销量
     */
    public Integer getReallySaleCount() {
        return reallySaleCount;
    }

    /**
     * 设置真实销量
     *
     * @param reallySaleCount 真实销量
     */
    public void setReallySaleCount(Integer reallySaleCount) {
        this.reallySaleCount = reallySaleCount;
    }

    /**
     * 获取是否包邮
     *
     * @return free_shipping - 是否包邮
     */
    public Boolean getFreeShipping() {
        return freeShipping;
    }

    /**
     * 设置是否包邮
     *
     * @param freeShipping 是否包邮
     */
    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    /**
     * 获取商品logo
     *
     * @return logo - 商品logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置商品logo
     *
     * @param logo 商品logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取商品轮播图
     *
     * @return banners - 商品轮播图
     */
    public String getBanners() {
        return banners;
    }

    /**
     * 设置商品轮播图
     *
     * @param banners 商品轮播图
     */
    public void setBanners(String banners) {
        this.banners = banners;
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
     * 获取富文本详情
     *
     * @return details - 富文本详情
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置富文本详情
     *
     * @param details 富文本详情
     */
    public void setDetails(String details) {
        this.details = details;
    }

    public static String validAdd(Goods goods) {
        if (goods.getTypeId() == null || StringUtils.isEmpty(goods.getTypeName()))
            return "商品分类不能为空";
        if (goods.getGoodsProp() == null)
            return "商品属性不能为空";
        if (goods.getBrandId() == null || StringUtils.isEmpty(goods.getBrandName()))
            return "商品品牌不能为空";

        if (StringUtils.isEmpty(goods.getGoodsName()))
            return "商品名称不能为空";

        if (goods.getSaleProp() == null)
            return "商品售卖属性不能为空";

        if (goods.getWeight() == null || goods.getWeight().doubleValue() < 0)
            return "重量不能为空且不能为负数";
        if (goods.getGuidePrice() == null || goods.getGuidePrice().doubleValue() < 0)
            return "指导价必填，且不能为负数";

        if (goods.getSalePriceMin() == null || goods.getSalePriceMin().doubleValue() < 0)
            return "最小价格范围不能为空且不能为负数";

        if (goods.getSalePriceMax() == null || goods.getSalePriceMax().doubleValue() < 0)
            return "最大价格范围不能为空且不能为负数";

        if (goods.getSalePriceMin().compareTo(goods.getSalePriceMax()) > -1)
            return "价格范围设置有误";

        if (!GOODS_PROP_NORMAL.equals(goods.getGoodsProp()) && goods.getFreeShipping() == null)
            return "非普通商品必须设置包邮策略";

        if (goods.getIsEnabled() == null && !GOODS_PROP_NORMAL.equals(goods.getGoodsProp()))
            return "上架状态不能为空";
        return null;
    }
}