package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.annotation.Page;
import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.yhpz.service.LiteService;
import com.wmeimob.custom.yhpz.bean.*;
import com.wmeimob.custom.yhpz.service.*;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Shinez on 2017/7/31.
 */
@RestController
@RequestMapping("goods")
@Slf4j
@Logging
public class GoodsController {

    @Resource
    private AdminGoodsService adminGoodsService;

    @Resource
    private LiteService liteService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private GoodsBrandService goodsBrandService;
    @Resource
    private GoodsService goodsService;


    /**
     * 查询商品
     *
     * @param request
     * @param goods
     * @return
     */
    @GetMapping
    @Page
    public JSONObject getGoods(HttpServletRequest request, Goods goods) {
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String branchIdStr = DataAuthService.getBranchId(userDetails);
        Assert.notNull(branchIdStr,"分公司信息未找到");
        Integer branchId = Integer.valueOf(branchIdStr);
        String liteStr = DataAuthService.getLiteId(userDetails);
        Map<String, Object> map = new HashMap<>();
        map.put("liteAuth", StringUtils.isEmpty(liteStr) ? 0 : 1);
        Integer liteId = liteStr == null ? goods.getLiteId() : Integer.valueOf(liteStr);
        goods.setBranchId(branchId);
        goods.setLiteId(liteId);
        List<Goods> list = goodsService.findAll(goods);
        PageInfo<Goods> pageInfo = new PageInfo<>(list);

        map.put("page", pageInfo);
        //查询品牌
        GoodsBrand goodsBrand=new GoodsBrand();
        goodsBrand.setBranchId(branchId);
        GoodsType goodsType=new GoodsType();
        goodsType.setBranchId(branchId);
        List<GoodsBrand> goodsBrands = goodsBrandService.findAllForTree(goodsBrand);
        map.put("goodsBrands", goodsBrands);
        //查询分类
        List<GoodsType> goodsTypes = goodsTypeService.findAllForTree(goodsType);
        map.put("goodsTypes", goodsTypes);
        //查询加盟店
        Lite lite = new Lite();
        lite.setId(liteId);
        lite.setBranchId(branchId);
        List<Lite> lites = liteService.findAll(lite);
        map.put("lites", lites);
        return RestResult.success(map);
    }


    /**
     * 获取商品品牌和类型,保税仓直邮地
     *
     * @return
     */
    @GetMapping("baseInfo")
    public JSONObject getBaseInfo() {
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId, "未找到当前登陆的分公司信息");
        GoodsBrand goodsBrand =new GoodsBrand();
        GoodsType goodsType=new GoodsType();
        goodsBrand.setBranchId(Integer.valueOf(branchId));
        goodsType.setBranchId(Integer.valueOf(branchId));
        Map<String, Object> map = new HashMap<>();
        //查询品牌
        List<GoodsBrand> goodsBrands = goodsBrandService.findAllForTree(goodsBrand);
        map.put("goodsBrands", goodsBrands);
        //查询分类
        List<GoodsType> goodsTypes = goodsTypeService.findAllForTree(goodsType);
        map.put("goodsTypes", goodsTypes);


        Warehouse warehouse=new Warehouse();
        warehouse.setBranchId(Integer.valueOf(branchId));
        List<Warehouse> warehouseList = warehouseService.findAll(warehouse);
        List<Warehouse> postHouseList = new ArrayList<>();
        Iterator<Warehouse> iterator = warehouseList.iterator();
        Warehouse w;
        while (iterator.hasNext()){
            w=iterator.next();
            if(Warehouse.HOUSE_TYPE_POST.equals(w.getHouseType())){
                postHouseList.add(w);
                iterator.remove();
            }
        }

        //查询保税仓
        map.put("taxes", warehouseList);
        //查询直邮地
        map.put("posts", postHouseList);
        return RestResult.success(map);
    }


    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    @PostMapping
    public JSONObject addGoods(Goods goods) {
        String validAddInfo = Goods.validAdd(goods);
        if (validAddInfo != null)
            return RestResult.fail(validAddInfo);
        adminGoodsService.add(goods);
        return RestResult.success();
    }


    /**
     * 修改商品
     *
     * @return
     */
    @PutMapping("{id}")
    public JSONObject updateGoods(@PathVariable Integer id, Goods goods) {
        String validAddInfo = Goods.validAdd(goods);
        if (validAddInfo != null)
            return RestResult.fail(validAddInfo);
        goods.setId(id);
        int result = adminGoodsService.updateGoods(goods);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }


    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public JSONObject delGoods(@PathVariable Integer id) {
        if (id == null) return RestResult.fail();
        int result = adminGoodsService.del(id);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }


    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public JSONObject getGoodsDetails(@PathVariable Integer id) {
        String brandIdStr = DataAuthService.getBranchId();
        Assert.notNull(brandIdStr, "未找到分公司信息");
        Assert.notNull(id, "商品不存在");
        Goods goods = adminGoodsService.findById(id);
        if (!goods.getBranchId().equals(Integer.valueOf(brandIdStr))) {
            return RestResult.fail("跨权限访问");
        }
        return RestResult.success(goods);
    }


    /**
     * 商品上架下架
     *
     * @param id
     * @param isEnabled
     * @return
     */
    @PutMapping("{id}/show")
    public JSONObject show(@PathVariable Integer id, Boolean isEnabled) {
        Goods goods = new Goods();
        Assert.notNull(id, "商品有误");
        isEnabled = isEnabled == null ? false : isEnabled;
        goods.setId(id);
        goods.setIsEnabled(isEnabled);
        int result = adminGoodsService.updateGoods(goods);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

}

