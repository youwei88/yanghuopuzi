/*
* UserAddress.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-12 15:56 Created
*/
package com.wmeimob.custom.yhpz.bean;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "user_address")
public class UserAddress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信用户ID
     */
    @Column(name = "wechat_user_id")
    private Integer wechatUserId;

    /**
     * 收货人姓名
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 联系电话
     */
    private String tel;

    private Integer province;

    private Integer city;

    private Integer area;

    private String address;

    /**
     * 是否默认
     */
    @Column(name = "is_default")
    private Boolean isDefault;

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
     * 获取微信用户ID
     *
     * @return wechat_user_id - 微信用户ID
     */
    public Integer getWechatUserId() {
        return wechatUserId;
    }

    /**
     * 设置微信用户ID
     *
     * @param wechatUserId 微信用户ID
     */
    public void setWechatUserId(Integer wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    /**
     * 获取收货人姓名
     *
     * @return full_name - 收货人姓名
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置收货人姓名
     *
     * @param fullName 收货人姓名
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取联系电话
     *
     * @return tel - 联系电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置联系电话
     *
     * @param tel 联系电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return province
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * @return city
     */
    public Integer getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * @return area
     */
    public Integer getArea() {
        return area;
    }

    /**
     * @param area
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取是否默认
     *
     * @return isDefault - 是否默认
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认
     *
     * @param isdefault 是否默认
     */
    public void setIsDefault(Boolean isdefault) {
        this.isDefault = isdefault;
    }

    public static String validAdd(UserAddress userAddress) {
        if (StringUtils.isEmpty(userAddress.getTel()))
            return "电话号码不能为空";
        if (StringUtils.isEmpty(userAddress.getFullName()))
            return "联系人名称不能为空";
        if (StringUtils.isEmpty(userAddress.getProvince())
                || StringUtils.isEmpty(userAddress.getCity())
                || StringUtils.isEmpty(userAddress.getArea())
                || StringUtils.isEmpty(userAddress.getAddress()))
        return "收货地址不能为空";
        return null;
    }
}