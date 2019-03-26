package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.yhpz.bean.GoodsType;
import com.wmeimob.custom.yhpz.bean.User;
import com.wmeimob.custom.yhpz.service.GoodsTypeService;
import com.wmeimob.custom.yhpz.service.WxUserService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shinez on 2017/8/12.
 */
@Slf4j
@Logging
@RestController
@RequestMapping("goods-type")
@CrossOrigin
public class GoodsTypesController {
    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private WxUserService wxUserService;

    /**
     * 获取商品 类型
     * @param format
     * @return
     */
    @GetMapping
    public JSONObject get(Boolean format) {
        format = format==null?false:format;
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = wxUserService.findByWechatUserId(wechatUser.getId());
        if (user == null)
            return RestResult.msg(HttpStatus.SC_FORBIDDEN, "用户未注册");
        GoodsType goodsType = new GoodsType();
        goodsType.setBranchId(user.getBranchId());
        Map<String, Object> map = new HashMap<>();
        List<GoodsType> goodsTypeList;
        goodsTypeList = format? goodsTypeService.findAllForTree(goodsType) : goodsTypeService.findAll(goodsType);
        map.put("goodsTypes", goodsTypeList);
        return RestResult.success(map);
    }

}
