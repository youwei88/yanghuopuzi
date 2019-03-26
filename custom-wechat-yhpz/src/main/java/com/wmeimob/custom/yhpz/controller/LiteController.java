package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.service.GoodsTypeService;
import com.wmeimob.custom.yhpz.service.LiteService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/11.
 */
@RestController
@RequestMapping("lite")
@Logging
@Slf4j
@CrossOrigin
public class LiteController {

    @Resource
    private LiteService liteService;

    /**
     * 条件获取加盟店
     *
     * @param lite
     * @return
     */
    @GetMapping
    public JSONObject getLites(Lite lite) {
        if (lite.getProvince() == null || lite.getCity() == null || lite.getArea() == null)
            return RestResult.fail("未选中省市区");
        lite.setIsEnabled(true);
        return RestResult.success(liteService.findAll(lite,
                "id","liteName","userCount","isSelf","selfDeliverySupport","isEnabled","freePost","tel","province","city","area","address"));
    }

    /**
     * 获取加盟店基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public JSONObject getLiteNameById(@PathVariable Integer id) {

        if(id==null)return RestResult.fail("加盟店ID不允许为空");
        Lite lite=new Lite();
        lite.setId(id);
        lite.setIsEnabled(true);
        List<Lite> liteList = liteService.findAll(lite,
                "id","liteName","userCount","isSelf","selfDeliverySupport","isEnabled","freePost","tel","province","city","area","address");
        if(liteList.size()==0)return RestResult.fail("未找到对应的加盟店信息");
        lite = liteList.get(0);
        return RestResult.success(lite);
    }
}
