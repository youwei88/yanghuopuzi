package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.system.bean.WechatUser;
import com.wmeimob.custom.yhpz.bean.*;
import com.wmeimob.custom.yhpz.service.*;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shinez on 2017/8/16.
 */
@Slf4j
@Logging
@RestController
@RequestMapping("home")
@CrossOrigin
public class HomeController {

    @Resource
    private HomePatitionService homePatitionService;
    @Resource
    private HomeTypeSetService homeTypeSetService;
    @Resource
    private BannerService bannerService;
    @Resource
    private ThemeService themeService;
    @Resource
    private WxUserService wxUserService;


    /**
     * 获取首页设置
     * @return
     */
    @GetMapping
    public JSONObject getHomeSet(){
        WechatUser wechatUser = (WechatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = wxUserService.findByWechatUserId(wechatUser.getId());
        if (user == null)
            return RestResult.msg(HttpStatus.SC_FORBIDDEN, "用户未注册");
        Map<String,Object> map=new HashMap<>();
        Banner banner=new Banner();
        banner.setLiteId(user.getLiteId());
        List<Banner> bannerList = bannerService.getBanners(banner);
        map.put("banners",bannerList);

        HomeTypeSet homeTypeSet=new HomeTypeSet();
        homeTypeSet.setLiteId(user.getLiteId());
        List<HomeTypeSet> homeTypeSetList = homeTypeSetService.getHomeTypes(homeTypeSet);
        map.put("homeTypes",homeTypeSetList);

        Theme theme=new Theme();
        theme.setLiteId(user.getLiteId());
        theme = themeService.getThemeConfig(theme);
        map.put("theme",theme);

        HomePartition homePartition=new HomePartition();
        homePartition.setLiteId(user.getLiteId());
        List<HomePartition> homePartitionList=homePatitionService.findAll(homePartition);
        map.put("homePartitions",homePartitionList);

        return RestResult.success(map);

    }


}
