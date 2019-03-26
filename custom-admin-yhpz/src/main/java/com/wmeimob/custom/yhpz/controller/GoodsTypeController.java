package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.yhpz.bean.GoodsType;
import com.wmeimob.custom.yhpz.service.DataAuthService;
import com.wmeimob.custom.yhpz.service.AdminGoodsTypeService;
import com.wmeimob.custom.yhpz.service.GoodsTypeService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/7/20.
 */
@RestController
@RequestMapping("goods-type")
@Slf4j
@Logging
public class GoodsTypeController {

    @Resource
    private AdminGoodsTypeService adminGoodsTypeService;

    @Resource
    private GoodsTypeService goodsTypeService;


    /**
     * 查询全部类型
     * @return
     */
    @GetMapping
    public JSONObject getTypes(){
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId,"分公司信息丢失");
        GoodsType goodsType=new GoodsType();
        goodsType.setBranchId(Integer.valueOf(branchId));
        List<GoodsType> goodsTypes = goodsTypeService.findAll(goodsType);
//        goodsBrands = goodsBrandService.generateClassify(goodsBrands);
        return RestResult.success(goodsTypes);
    }

    /**
     * 更新
     * @param goodsType
     * @return
     */
    @PutMapping("{id}")
    public JSONObject updateType(GoodsType goodsType, @PathVariable Integer id){
        int result  = adminGoodsTypeService.update(goodsType);
        return result>0?RestResult.success():RestResult.fail();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public JSONObject delBrand(@PathVariable Integer id){
        int result = adminGoodsTypeService.del(id);
        return result>0?RestResult.success():RestResult.fail();
    }

    /**
     * 新增
     * @param goodsType
     * @return
     */
    @PostMapping
    public JSONObject insert(GoodsType goodsType){
        int result = adminGoodsTypeService.add(goodsType);
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
        int result = adminGoodsTypeService.update(data);
        return result>0?RestResult.success():RestResult.fail();
    }
}
