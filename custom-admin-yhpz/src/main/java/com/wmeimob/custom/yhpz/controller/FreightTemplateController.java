package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.annotation.Page;
import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.yhpz.bean.FreightTemplate;
import com.wmeimob.custom.yhpz.service.DataAuthService;
import com.wmeimob.custom.yhpz.service.FreightTemplateService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Shinez on 2017/8/9.
 */

@RestController
@Logging
@Slf4j
@RequestMapping("freight")
public class FreightTemplateController {


    @Resource
    FreightTemplateService freightTemplateService;


    /**
     *  查询全部运费模板
     * @param request
     * @param freightTemplate
     * @return
     */
    @GetMapping
    @Page
    public JSONObject getFreightTemplates(HttpServletRequest request, FreightTemplate freightTemplate) {
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String liteId = DataAuthService.getLiteId(userDetails);
        String branchId = DataAuthService.getBranchId(userDetails);
        Assert.notNull(branchId, "分公司信息未找到");
        freightTemplate.setBranchId(Integer.valueOf(branchId));
        freightTemplate.setLiteId(liteId==null?null:Integer.valueOf(liteId));
        List<FreightTemplate> freightTemplateList = freightTemplateService.findAll(freightTemplate);
        PageInfo<FreightTemplate> pageInfo = new PageInfo<>(freightTemplateList);
        return RestResult.success(pageInfo);
    }

    /**
     * 添加运费模板
     * @param freightTemplate
     * @return
     */
    @PostMapping
    public JSONObject addFreightTemplates(@RequestBody FreightTemplate[] freightTemplate){
        String validAddInfo = FreightTemplate.validAdd(freightTemplate);
        if(validAddInfo!=null)return RestResult.fail(validAddInfo);
        freightTemplateService.save(freightTemplate);
        return RestResult.success();
    }

    @PutMapping
    public JSONObject updateFreightTemplates(@RequestBody FreightTemplate[] freightTemplate){
        String validAddInfo = FreightTemplate.validAdd(freightTemplate);
        if(validAddInfo!=null)return RestResult.fail(validAddInfo);
        freightTemplateService.save(freightTemplate);
        return RestResult.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("id")
    public JSONObject delFreightTemplates(@PathVariable Integer id){
        int result = freightTemplateService.del(id);
        return result>0?RestResult.success():RestResult.fail();
    }


}
