package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.yhpz.bean.UserAddress;
import com.wmeimob.custom.yhpz.dao.UserAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinez on 2017/8/12.
 */
@Transactional
@Service
public class UserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;

    /**
     * 个人最大收货地址数量
     */
    private static final Integer USER_ADDRESS_MAX_COUNT = 10;


    /**
     * 根据微信用户ID查询收货地址
     *
     * @return
     */
    public List<UserAddress> findAll(UserAddress userAddress) {
        List<UserAddress> userAddressList = new ArrayList<>();
        if (userAddress.getWechatUserId() == null) return userAddressList;
        userAddressList = userAddressMapper.select(userAddress);
        return userAddressList;
    }

    /**
     * 新增收货地址
     *
     * @param userAddress
     */
    public UserAddress add(UserAddress userAddress) {
        String synchStr = "userAddressCheck" + userAddress.getWechatUserId();
        synchronized (synchStr.intern()) {
            //检查收货地址数量
            int hasCount = getUserAddressCount(userAddress.getWechatUserId());
            if (hasCount >= USER_ADDRESS_MAX_COUNT) {
                throw new CustomException("收货地址数量不能超过" + USER_ADDRESS_MAX_COUNT + "个");
            }
            userAddress.setIsDefault(false);
            userAddressMapper.insertSelective(userAddress);
            return userAddress;
        }
    }

    /**
     * 获取收货地址数量
     *
     * @param wechatUserId
     * @return
     */
    public int getUserAddressCount(int wechatUserId) {
        UserAddress checkUserAddress = new UserAddress();
        checkUserAddress.setWechatUserId(wechatUserId);
        int count = userAddressMapper.selectCount(checkUserAddress);
        return count;
    }

    /**
     * 修改收货地址
     *
     * @param userAddress
     * @return
     */
    public int update(UserAddress userAddress) {
        Assert.notNull(userAddress.getId(), "地址不存在");
        Assert.notNull(userAddress.getWechatUserId(), "用户不存在");
        int result = userAddressMapper.updateByPrimaryKeySelective(userAddress);
        if (result > 0) {
            //默认地址设置
            if (userAddress.getIsDefault()!=null&&userAddress.getIsDefault()) {
                //取消其余的默认设置
                Example example = new Example(UserAddress.class);
                example.createCriteria()
                        .andEqualTo("wechatUserId", userAddress.getWechatUserId())
                        .andNotEqualTo("id", userAddress.getId());
                UserAddress userAddressUpdateDefault = new UserAddress();
                userAddressUpdateDefault.setIsDefault(false);
                userAddressMapper.updateByExampleSelective(userAddressUpdateDefault,example);
            }
        }
        return result;
    }

    /**
     * 条件删除收货地址
     * @param userAddress
     * @return
     */
    public int del(UserAddress userAddress) {
        Assert.notNull(userAddress.getId(), "地址不存在");
        Assert.notNull(userAddress.getWechatUserId(), "用户不存在");
        int result = userAddressMapper.delete(userAddress);
        return result;
    }
}
