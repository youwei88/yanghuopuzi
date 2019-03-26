package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.yhpz.bean.Warehouse;
import com.wmeimob.custom.yhpz.service.DataAuthService;
import com.wmeimob.custom.yhpz.service.WarehouseService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Shinez on 2017/8/5.
 */
@RestController
@RequestMapping("warehouse")
@Logging
@Slf4j
public class WarehouseController {


    @Resource
    private WarehouseService warehouseService;


    /**
     * 新增保税仓直邮地
     *
     * @param warehouse
     * @return
     */
    @PostMapping
    public JSONObject add(Warehouse warehouse) {
        String addInfo = Warehouse.validAdd(warehouse);
        if (addInfo != null)
            return RestResult.fail(addInfo);
        warehouse = warehouseService.add(warehouse);
        return RestResult.success(warehouse);
    }

    /**
     * 删除保税仓直邮地
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public JSONObject del(@PathVariable Integer id) {
        if (id == null)
            return RestResult.fail();
        int result = warehouseService.del(id);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }


    /**
     * 修改保税仓直邮地
     *
     * @param id
     * @param warehouse
     * @return
     */
    @PutMapping("{id}")
    public JSONObject update(@PathVariable Integer id, Warehouse warehouse) {
        if (id == null)
            return RestResult.fail();
        warehouse.setId(id);
        String addInfo = Warehouse.validAdd(warehouse);
        if (addInfo != null)
            return RestResult.fail(addInfo);
        int result = warehouseService.update(warehouse);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * 查询全部保税仓直邮地
     *
     * @param request
     * @return
     */
    @GetMapping
    public JSONObject getWarehouse(HttpServletRequest request) {
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId, "未找到当前登陆的分公司信息");
        Warehouse warehouse= new Warehouse();
        warehouse.setBranchId(Integer.valueOf(branchId));
        List<Warehouse> list = warehouseService.findAll(warehouse);
        PageInfo<Warehouse> pageInfo = new PageInfo<>(list);
        return RestResult.success(pageInfo);
    }
}
