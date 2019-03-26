/*
* Theme.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-15 10:38 Created
*/ 
package com.wmeimob.custom.yhpz.bean;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "theme")
public class Theme implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 加盟店ID
     */
    @Column(name = "lite_id")
    private Integer liteId;

    /**
     * 首页背景颜色
     */
    @Column(name = "home_bg_color")
    private String homeBgColor;

    /**
     * 导航背景颜色
     */
    @Column(name = "navigation_bg_color")
    private String navigationBgColor;

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
     * 获取首页背景颜色
     *
     * @return home_bg_color - 首页背景颜色
     */
    public String getHomeBgColor() {
        return homeBgColor;
    }

    /**
     * 设置首页背景颜色
     *
     * @param homeBgColor 首页背景颜色
     */
    public void setHomeBgColor(String homeBgColor) {
        this.homeBgColor = homeBgColor;
    }

    /**
     * 获取导航背景颜色
     *
     * @return navigation_bg_color - 导航背景颜色
     */
    public String getNavigationBgColor() {
        return navigationBgColor;
    }

    /**
     * 设置导航背景颜色
     *
     * @param navigationBgColor 导航背景颜色
     */
    public void setNavigationBgColor(String navigationBgColor) {
        this.navigationBgColor = navigationBgColor;
    }

    public Integer getLiteId() {
        return liteId;
    }

    public void setLiteId(Integer liteId) {
        this.liteId = liteId;
    }
}