package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.annotation.Page;
import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.yhpz.bean.Goods;
import com.wmeimob.custom.yhpz.bean.User;
import com.wmeimob.custom.yhpz.service.GoodsService;
import com.wmeimob.custom.yhpz.service.WxUserService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shinez on 2017/8/12.
 */
@RestController
@RequestMapping("goods")
@Logging
@Slf4j
@CrossOrigin
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private WxUserService wxUserService;


    /**
     * 查询商品
     *
     * @param goods
     * @return
     */
    @GetMapping
    @Page
    public JSONObject getGoods(Goods goods) {
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = wxUserService.findByWechatUserId(wechatUser.getId());
        if (user == null)
            return RestResult.msg(HttpStatus.SC_FORBIDDEN, "用户未注册");
        goods.setBranchId(user.getBranchId());
        goods.setLiteId(user.getLiteId());
        List<Goods> goodsList = goodsService.findAll(goods);
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        return RestResult.success(pageInfo);
    }



    /**
     * 根据ID查询商品
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public JSONObject getGoodsById(@PathVariable String id) {
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = wxUserService.findByWechatUserId(wechatUser.getId());
        if (user == null)
            return RestResult.msg(HttpStatus.SC_FORBIDDEN, "用户未注册");
        if (StringUtils.isEmpty(id)) {
            return RestResult.fail("商品不存在");
        }
        Goods goods = new Goods();
        goods.setBranchId(user.getBranchId());
        goods.setLiteId(user.getLiteId());
        goods.setId(Integer.valueOf(id));
        goods.setGoodsNo(id);
        List<Goods> goodsList = goodsService.findAll(goods);
        if (goodsList.size() != 1)
            return RestResult.fail("商品信息不正确");
        goods = goodsList.get(0);
        return RestResult.success(goods);
    }

    /**
     * 获取商品详情
     * @param id
     * @return
     */
    @GetMapping("{id}/details")
    public JSONObject getGoodsDetails(@PathVariable Integer id){
        String details = goodsService.findGoodsDetailsById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("details",details);
        return RestResult.success(map);
    }

}
