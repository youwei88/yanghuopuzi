/*
* User.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-29 19:50 Created
*/ 
package com.wmeimob.custom.yhpz.bean;

import com.wmeimob.tool.InputValidator;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户表
 */
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 密码(暂未使用)
     */
    private String pwd;

    /**
     * 微信用户id
     */
    @Column(name = "wechat_user_id")
    private Integer wechatUserId;

    /**
     * 微信用户昵称
     */
    private String nickname;

    /**
     * 微信头像
     */
    private String headimgurl;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 总积分
     */
    @Column(name = "total_integral")
    private Integer totalIntegral;

    /**
     * 支付密码
     */
    @Column(name = "pay_pwd")
    private String payPwd;

    /**
     * 分公司ID
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 加盟店ID
     */
    @Column(name = "lite_id")
    private Integer liteId;

    /**
     * 分公司名称
     */
    @Column(name = "branch_name")
    private String branchName;

    /**
     * 加盟店名称
     */
    @Column(name = "lite_name")
    private String liteName;

    /**
     * 当前可用佣金
     */
    private BigDecimal commission;

    /**
     * 历史佣金
     */
    @Column(name = "total_commission")
    private BigDecimal totalCommission;

    /**
     * 已使用佣金
     */
    @Column(name = "used_commission")
    private BigDecimal usedCommission;

    /**
     * 今日佣金
     */
    @Transient
    private BigDecimal todayCommission;

    /**
     * 上级ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 微客数量
     */
    @Column(name = "v_user_count")
    private Integer vUserCount;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
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
     * 获取手机号码
     *
     * @return tel - 手机号码
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置手机号码
     *
     * @param tel 手机号码
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取密码(暂未使用)
     *
     * @return pwd - 密码(暂未使用)
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 设置密码(暂未使用)
     *
     * @param pwd 密码(暂未使用)
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * 获取微信用户id
     *
     * @return wechat_user_id - 微信用户id
     */
    public Integer getWechatUserId() {
        return wechatUserId;
    }

    /**
     * 设置微信用户id
     *
     * @param wechatUserId 微信用户id
     */
    public void setWechatUserId(Integer wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    /**
     * 获取微信用户昵称
     *
     * @return nickname - 微信用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置微信用户昵称
     *
     * @param nickname 微信用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取余额
     *
     * @return balance - 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额
     *
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取积分
     *
     * @return integral - 积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置积分
     *
     * @param integral 积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取总积分
     *
     * @return total_integral - 总积分
     */
    public Integer getTotalIntegral() {
        return totalIntegral;
    }

    /**
     * 设置总积分
     *
     * @param totalIntegral 总积分
     */
    public void setTotalIntegral(Integer totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    /**
     * 获取支付密码
     *
     * @return pay_pwd - 支付密码
     */
    public String getPayPwd() {
        return payPwd;
    }

    /**
     * 设置支付密码
     *
     * @param payPwd 支付密码
     */
    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    /**
     * 获取分公司ID
     *
     * @return branch_id - 分公司ID
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * 设置分公司ID
     *
     * @param branchId 分公司ID
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
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
     * 获取分公司名称
     *
     * @return branch_name - 分公司名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分公司名称
     *
     * @param branchName 分公司名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * 获取加盟店名称
     *
     * @return lite_name - 加盟店名称
     */
    public String getLiteName() {
        return liteName;
    }

    /**
     * 设置加盟店名称
     *
     * @param liteName 加盟店名称
     */
    public void setLiteName(String liteName) {
        this.liteName = liteName;
    }

    /**
     * 获取当前可用佣金
     *
     * @return commission - 当前可用佣金
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * 设置当前可用佣金
     *
     * @param commission 当前可用佣金
     */
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**
     * 获取历史佣金
     *
     * @return total_commission - 历史佣金
     */
    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    /**
     * 设置历史佣金
     *
     * @param totalCommission 历史佣金
     */
    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    /**
     * 获取已使用佣金
     *
     * @return used_commission - 已使用佣金
     */
    public BigDecimal getUsedCommission() {
        return usedCommission;
    }

    /**
     * 设置已使用佣金
     *
     * @param usedCommission 已使用佣金
     */
    public void setUsedCommission(BigDecimal usedCommission) {
        this.usedCommission = usedCommission;
    }

    public BigDecimal getTodayCommission() {
        return todayCommission;
    }

    public void setTodayCommission(BigDecimal todayCommission) {
        this.todayCommission = todayCommission;
    }

    /**
     * 获取上级ID
     *
     * @return parent_id - 上级ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级ID
     *
     * @param parentId 上级ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取微客数量
     *
     * @return v_user_count - 微客数量
     */
    public Integer getvUserCount() {
        return vUserCount;
    }

    /**
     * 设置微客数量
     *
     * @param vUserCount 微客数量
     */
    public void setvUserCount(Integer vUserCount) {
        this.vUserCount = vUserCount;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static String validAdd(User user) {
        if(!InputValidator.isMobile(user.getTel()))
            return "不正确的手机号码";
        if(user.getLiteId()==null)
            return "未选中加盟店";
        return null;
    }
}