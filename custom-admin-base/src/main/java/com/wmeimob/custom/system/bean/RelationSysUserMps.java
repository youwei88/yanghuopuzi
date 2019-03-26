/*
* RelationSysUserMps.java
* EnTropyShiNe
* Copyright © 2016 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-01-10 11:36 Created
*/ 
package com.wmeimob.custom.system.bean;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "relation_sys_user_mps")
public class RelationSysUserMps implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统用户id
     */
    @Column(name = "sys_user_id")
    private Integer sysUserId;

    /**
     * 公众号id
     */
    private Integer mpid;

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
     * 获取系统用户id
     *
     * @return sys_user_id - 系统用户id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * 设置系统用户id
     *
     * @param sysUserId 系统用户id
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * 获取公众号id
     *
     * @return mpid - 公众号id
     */
    public Integer getMpid() {
        return mpid;
    }

    /**
     * 设置公众号id
     *
     * @param mpid 公众号id
     */
    public void setMpid(Integer mpid) {
        this.mpid = mpid;
    }
}