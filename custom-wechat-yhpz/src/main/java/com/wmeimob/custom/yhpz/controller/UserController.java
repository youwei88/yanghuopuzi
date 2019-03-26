package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.yhpz.bean.User;
import com.wmeimob.custom.yhpz.bean.UserAddress;
import com.wmeimob.custom.yhpz.service.UserAddressService;
import com.wmeimob.custom.yhpz.service.WxUserService;
import com.wmeimob.tool.InputValidator;
import com.wmeimob.tool.RandomCodeUtil;
import com.wmeimob.tool.SpringRedisUtil;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shinez on 2017/8/10.
 */

@RestController
@RequestMapping("user")
@Logging
@Slf4j
@CrossOrigin
public class UserController {


    @Resource
    private WxUserService wxUserService;

    @Resource
    private UserAddressService userAddressService;

    /**
     * 个人中心
     *
     * @return
     */
    @GetMapping("mine")
    public JSONObject getMineInfo() {
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = wxUserService.findByWechatUserId(wechatUser.getId());
        if (user == null)
            return RestResult.msg(HttpStatus.SC_FORBIDDEN, "用户未注册");
        user.setPayPwd(null);//过滤支付密码
        //查询今日佣金
        String toDayCommission = SpringRedisUtil.get("wechatUser:" + wechatUser.getId() + ":todayCommission");
        user.setTodayCommission(toDayCommission != null ? new BigDecimal(toDayCommission) : new BigDecimal(0));
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        Map<String, Integer> orderCounts = new HashMap<>();
        orderCounts.put("unPay", 0);
        orderCounts.put("unSend", 0);
        orderCounts.put("unReceive", 0);
        orderCounts.put("unComment", 0);
        orderCounts.put("refund", 0);
        map.put("orderCountInfo", orderCounts);
        return RestResult.success(map);
    }

    /**
     * 获取收货地址
     *
     * @return
     */
    @GetMapping("mine/address")
    public JSONObject getMineAddress(Boolean isDefault) {
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAddress userAddress=new UserAddress();
        userAddress.setWechatUserId(wechatUser.getId());
        userAddress.setIsDefault(isDefault);
        List<UserAddress> userAddressList = userAddressService.findAll(userAddress);
        Map<String, Object> map = new HashMap<>();
        map.put("address", userAddressList);
        return RestResult.success(map);
    }

    /**
     * 新增收货地址
     *
     * @return
     */
    @PostMapping("mine/address")
    public JSONObject postMineAddress(UserAddress userAddress) {
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String validAddStr = UserAddress.validAdd(userAddress);
        if (validAddStr != null) return RestResult.fail(validAddStr);
        userAddress.setWechatUserId(wechatUser.getId());
        userAddress = userAddressService.add(userAddress);
        return RestResult.success(userAddress);
    }

    /**
     * 修改收货地址
     * @param userAddress
     * @return
     */
    @PutMapping("mine/address/{id}")
    public JSONObject updateAddress(UserAddress userAddress,@PathVariable Integer id){
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String validAddStr = UserAddress.validAdd(userAddress);
        if (validAddStr != null) return RestResult.fail(validAddStr);
        userAddress.setWechatUserId(wechatUser.getId());
        userAddress.setId(id);
        int result  = userAddressService.update(userAddress);
        return result>0?RestResult.success():RestResult.fail();
    }

    /**
     * 删除收货地址
     * @return
     */
    @DeleteMapping("mine/address/{id}")
    public JSONObject updateAddress(@PathVariable Integer id){
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAddress userAddress = new UserAddress();
        userAddress.setWechatUserId(wechatUser.getId());
        userAddress.setId(id);
        int result  = userAddressService.del(userAddress);
        return result>0?RestResult.success():RestResult.fail();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @GetMapping("code")
    public JSONObject getValidCode(String tel) {
        if (!InputValidator.isMobile(tel))
            return RestResult.fail("不正确的手机号码");
        String code = RandomCodeUtil.randCodeNum(6);
        //TODO 发送短信
        int min = 15;
        SpringRedisUtil.save("tel:" + tel + ":code", code, min * 60 * 1000L);//15分钟
        Map<String, Object> map = new HashMap<>();
        map.put("validMin", min);
        map.put("testAuth", code);
        return RestResult.success(map);
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @PostMapping
    public JSONObject register(HttpServletRequest request, User user) {
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code))
            return RestResult.fail("验证码不能为空");

        String redisCode = SpringRedisUtil.get("tel:" + user.getTel() + ":code");
        if (!code.equals(redisCode))
            return RestResult.fail("验证码不正确");
        String validResult = User.validAdd(user);
        if (validResult != null) return RestResult.fail(validResult);
        user = wxUserService.add(user);
        return RestResult.success(user);
    }
}
