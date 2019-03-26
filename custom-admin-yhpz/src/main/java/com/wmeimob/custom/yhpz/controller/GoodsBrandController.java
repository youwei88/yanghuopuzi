package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.yhpz.bean.GoodsBrand;
import com.wmeimob.custom.yhpz.service.DataAuthService;
import com.wmeimob.custom.yhpz.service.AdminGoodsBrandService;
import com.wmeimob.custom.yhpz.service.GoodsBrandService;
import com.wmeimob.tool.message.RestResult;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/7/20.
 */
@RestController
@RequestMapping("goods-brand")
@Logging
public class GoodsBrandController {

    @Resource
    private AdminGoodsBrandService adminGoodsBrandService;

    @Resource
    private GoodsBrandService goodsBrandService;


    /**
     * 查询全部品牌
     * @return
     */
    @GetMapping
    public JSONObject getBrands(){
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId,"分公司信息丢失");
        GoodsBrand goodsBrand=new GoodsBrand();
        goodsBrand.setBranchId(Integer.valueOf(branchId));
        List<GoodsBrand>  goodsBrands = goodsBrandService.findAll(goodsBrand);
        return RestResult.success(goodsBrands);
    }

    /**
     * 更新
     * @param goodsBrand
     * @return
     */
    @PutMapping("{id}")
    public JSONObject updateBrand(GoodsBrand goodsBrand,@PathVariable Integer id){
        int result  = adminGoodsBrandService.update(goodsBrand);
        return result>0?RestResult.success():RestResult.fail();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public JSONObject delBrand(@PathVariable Integer id){
        int result = adminGoodsBrandService.del(id);
        return result>0?RestResult.success():RestResult.fail();
    }

    /**
     * 新增
     * @param goodsBrand
     * @return
     */
    @PostMapping
    public JSONObject insert(GoodsBrand goodsBrand){
        int result = adminGoodsBrandService.add(goodsBrand);
        return result>0?RestResult.success():RestResult.fail();
    }


    /**
     * 批量更新
     * @param data
     * @return
     */
    @PostMapping("list")
    public JSONObject updateList(@RequestBody JSONArray data){
        if(data==null||data.size()==0)return RestResult.fail("没有更新任何数据");
        int result = adminGoodsBrandService.update(data);
        return result>0?RestResult.success():RestResult.fail();
    }

}
