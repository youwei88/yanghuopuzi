/*
* FreightTemplateRelation.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-09 14:26 Created
*/ 
package com.wmeimob.custom.yhpz.bean;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "freight_template_relation")
public class FreightTemplateRelation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 运费模板ID
     */
    @Column(name = "freight_template_id")
    private Integer freightTemplateId;

    /**
     * 省份城市区/县ID
     */
    @Column(name = "city_info_id")
    private Integer cityInfoId;


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
     * 获取运费模板ID
     *
     * @return freight_template_id - 运费模板ID
     */
    public Integer getFreightTemplateId() {
        return freightTemplateId;
    }

    /**
     * 设置运费模板ID
     *
     * @param freightTemplateId 运费模板ID
     */
    public void setFreightTemplateId(Integer freightTemplateId) {
        this.freightTemplateId = freightTemplateId;
    }

    public Integer getCityInfoId() {
        return cityInfoId;
    }

    public void setCityInfoId(Integer cityInfoId) {
        this.cityInfoId = cityInfoId;
    }
}