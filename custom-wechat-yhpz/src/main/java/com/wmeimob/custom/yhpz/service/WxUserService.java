package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.yhpz.bean.Branch;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.bean.User;
import com.wmeimob.custom.yhpz.bean.UserAddress;
import com.wmeimob.custom.yhpz.dao.BranchMapper;
import com.wmeimob.custom.yhpz.dao.LiteMapper;
import com.wmeimob.custom.yhpz.dao.UserAddressMapper;
import com.wmeimob.custom.yhpz.dao.UserMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shinez on 2017/8/11.
 */
@Service
@Transactional
public class WxUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private BranchMapper branchMapper;
    @Resource
    private LiteMapper liteMapper;


    /**
     * 用户注册
     * @param user
     * @return
     */
    public User add(User user) {
        WechatUser wechatUser= (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setWechatUserId(wechatUser.getId());
        user.setHeadimgurl(wechatUser.getHeadimgurl());
        user.setNickname(wechatUser.getNickname());
        BigDecimal bigDecimal=new BigDecimal("0");
        user.setBalance(bigDecimal);
        user.setIntegral(0);
        user.setTotalIntegral(0);

        Lite lite = liteMapper.selectByPrimaryKey(user.getLiteId());
        Assert.notNull(lite,"加盟店不存在");
        user.setLiteName(lite.getLiteName());
        Branch branch = branchMapper.selectByPrimaryKey(lite.getBranchId());
        Assert.notNull(branch,"分公司不存在");
        user.setBranchId(branch.getId());
        user.setBranchName(branch.getBranchName());
        user.setCommission(bigDecimal);
        user.setTotalCommission(bigDecimal);
        user.setUsedCommission(bigDecimal);
        user.setvUserCount(0);
        Date now = new Date();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        String synchronizeStr = user.getTel()+wechatUser.getId();
        Example example=new Example(User.class);
        example.createCriteria().andEqualTo("tel",user.getTel());
        example.or().andEqualTo("wechatUserId",wechatUser.getId());
        String errorMsgDefault = "微信号或手机号已被注册";
        synchronized (synchronizeStr.intern()){
            try {
                List<User> list = userMapper.selectByExample(example);
                if(list.size()>0){
                    throw new CustomException(errorMsgDefault);
                }
            }catch (DuplicateKeyException e){
                throw new CustomException(errorMsgDefault);
            }
            userMapper.insertSelective(user);
            liteMapper.updateIncrement(user.getLiteId(),1);
        }
        return user;
    }


    /**
     * 根据微信用户ID查询用户
     * @param wechatUserId
     * @return
     */
    public User findByWechatUserId(Integer wechatUserId) {
        User user= new User();
        user.setWechatUserId(wechatUserId);
        user = userMapper.selectOne(user);
        return user;
    }

}
