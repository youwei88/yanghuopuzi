package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.yhpz.bean.*;
import com.wmeimob.custom.yhpz.service.*;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shinez on 2017/8/14.
 */
@RestController
@RequestMapping("home")
@Logging
@Slf4j
public class HomeController {

    @Resource
    private AdminBannerService adminBannerService;
    @Resource
    private BannerService bannerService;

    @Resource
    private AdminHomeTypeSetService adminHomeTypeSetService;

    @Resource
    private HomeTypeSetService homeTypeSetService;

    @Resource
    private AdminHomePatitionService adminHomePatitionService;

    @Resource
    private HomePatitionService homePatitionService;


    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private AdminThemeService adminThemeService;

    @Resource
    private ThemeService themeService;


    /**
     * * * * * * * * * * * * * * * * * * * banner设置 * * * * * * * * * * * * * * * * *
     */

    /**
     * 获取banner
     *
     * @return
     */
    @GetMapping("banner")
    public JSONObject getBanners() {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "无法获取当前登陆的加盟店");
        Banner banner = new Banner();
        banner.setLiteId(Integer.valueOf(liteId));
        PageInfo pageInfo = new PageInfo<>(bannerService.getBanners(banner));
        Map<String, Object> map = new HashMap<>();
        map.put("page", pageInfo);

        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId, "无法获取当前登陆的加盟店权限");
        GoodsType goodsType = new GoodsType();
        goodsType.setBranchId(Integer.valueOf(branchId));
        map.put("types", goodsTypeService.findAllForTree(goodsType));
        return RestResult.success(map);
    }

    /**
     * 添加轮播图
     *
     * @param banner
     * @return
     */
    @PostMapping("banner")
    public JSONObject addBanners(Banner banner) {
        String validAdd = Banner.validAdd(banner);
        if (validAdd != null) return RestResult.fail(validAdd);
        Banner result = adminBannerService.add(banner);
        return result != null ? RestResult.success() : RestResult.fail();
    }

    /**
     * 修改banner
     *
     * @param banner
     * @return
     */
    @PutMapping("banner/{id}")
    public JSONObject updateBanner(Banner banner, @PathVariable Integer id) {
        String validAdd = Banner.validAdd(banner);
        if (validAdd != null) return RestResult.fail(validAdd);
        int result = adminBannerService.update(banner);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * 删除banner
     *
     * @return
     */
    @DeleteMapping("banner/{id}")
    public JSONObject delBanner(@PathVariable Integer id) {
        int result = adminBannerService.del(id);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }


/**
 * * * * * * * * * * * * * * * * * * * 分类设置 * * * * * * * * * * * * * * * * *
 */


    /**
     * 获取分类设置
     *
     * @return
     */
    @GetMapping("type")
    public JSONObject getType() {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "无法获取当前登陆加盟店");
        HomeTypeSet banner = new HomeTypeSet();
        banner.setLiteId(Integer.valueOf(liteId));
        Map<String, Object> map = new HashMap<>();
        map.put("types", homeTypeSetService.getHomeTypes(banner));
        return RestResult.success(map);
    }

    /**
     * 添加分类设置
     *
     * @param homeTypeSet
     * @return
     */
    @PostMapping("type")
    public JSONObject add(HomeTypeSet homeTypeSet) {
        String valid = HomeTypeSet.validAdd(homeTypeSet);
        if (valid != null) return RestResult.fail(valid);
        homeTypeSet = adminHomeTypeSetService.add(homeTypeSet);
        return homeTypeSet == null ? RestResult.fail() : RestResult.success(homeTypeSet);
    }


    /**
     * 修改分类设置
     *
     * @param homeTypeSet
     * @return
     */
    @PutMapping("type/{id}")
    public JSONObject update(HomeTypeSet homeTypeSet, @PathVariable Integer id) {
        String validAdd = HomeTypeSet.validAdd(homeTypeSet);
        if (validAdd != null) return RestResult.fail(validAdd);
        int result = adminHomeTypeSetService.update(homeTypeSet);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * 删除banner
     *
     * @return
     */
    @DeleteMapping("type/{id}")
    public JSONObject del(@PathVariable Integer id) {
        int result = adminHomeTypeSetService.del(id);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * * * * * * * * * * * * * * * * * * * 颜色设置 * * * * * * * * * * * * * * * * *
     */

    /**
     * 获取主题配置信息
     *
     * @return
     */
    @GetMapping("theme")
    public JSONObject getColor() {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "无法获取当前登陆加盟店");
        Theme theme = new Theme();
        theme.setLiteId(Integer.valueOf(liteId));
        theme = themeService.getThemeConfig(theme);
        Map<String, Object> map = new HashMap<>();
        map.put("theme", theme);
        return RestResult.success(map);
    }

    /**
     * 修改主题
     *
     * @return
     */
    @PutMapping("theme")
    public JSONObject updateColor(Theme theme) {
        int result = adminThemeService.update(theme);
        return result > 0 ? RestResult.success(theme) : RestResult.fail();
    }


    /**
     * * * * * * * * * * * * * * * * * * * 区块设置 * * * * * * * * * * * * * * * * *
     */


    /**
     * 获取首页区块
     *
     * @return
     */
    @GetMapping("partition")
    public JSONObject getPartition() {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "无法获取当前登陆加盟店");
        HomePartition homePartition = new HomePartition();
        homePartition.setLiteId(Integer.valueOf(liteId));
        List<HomePartition> partitionList = homePatitionService.findAll(homePartition);
        Map<String, Object> map = new HashMap<>();
        map.put("partitionList", partitionList);
        return RestResult.success(map);
    }

    /**
     * 保存版块
     *
     * @param partition
     * @return
     */
    @PostMapping("partition")
    public JSONObject savePartition(@RequestBody HomePartition partition) {
        String validResult = HomePartition.validAdd(partition);
        if (validResult != null) return RestResult.fail(validResult);
        adminHomePatitionService.save(partition);
        return RestResult.success();
    }

//    /**
//     * 修改版块
//     * @param partitions
//     * @return
//     */
//    @PutMapping("partition")
//    public JSONObject updatePartition(@RequestBody HomePartition[] partitions){
//        String validResult = HomePartition.validAdd(partitions);
//        if (validResult != null) return RestResult.fail(validResult);
//        adminHomePatitionService.update(partitions);
//        return RestResult.success();
//    }

    /**
     * 删除子版块
     *
     * @return
     */
    @DeleteMapping("partition")
    public JSONObject delChildrenPartition(HttpServletRequest request) {
        String id = request.getParameter("ids");
        id = id.substring(1, id.length() - 1);
        String[] ids = id.split(",");
        Integer[] idArray = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            if (!"null".equals(ids[i]) && !"undefined".equals(ids[i]))
                idArray[i] = Integer.valueOf(ids[i]);
        }
        int result = adminHomePatitionService.delChildren(idArray);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * 删除父板块
     *
     * @param id
     * @return
     */
    @DeleteMapping("partition/{id}")
    public JSONObject delParentPartition(@PathVariable Integer id) {
        if (id == null) return RestResult.fail();
        int result = adminHomePatitionService.delParentPartition(id);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

}
