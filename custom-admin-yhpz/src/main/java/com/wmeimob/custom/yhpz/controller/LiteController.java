package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.annotation.Page;
import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.yhpz.service.LiteService;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.service.DataAuthService;
import com.wmeimob.custom.yhpz.service.AdminLiteService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/6/30.
 */

@RestController
@RequestMapping("lite")
@Logging
@Slf4j
public class LiteController {

    @Resource
    private AdminLiteService adminLiteService;

    @Resource
    private LiteService liteService;


    /**
     * 获取所有加盟店
     *
     * @param lite
     * @return
     */
    @GetMapping
    @Page
    public JSONObject getAll(Lite lite) {
        String branchId = DataAuthService.getBranchId();
        lite.setBranchId(Integer.valueOf(branchId));
        List<Lite> liteList = liteService.findAll(lite);
        PageInfo<Lite> litePageInfo = new PageInfo<>(liteList);
        return RestResult.success(litePageInfo);
    }


    /**
     * 添加加盟店
     *
     * @param lite
     * @param sysUser
     * @return
     */
    @PostMapping
    public JSONObject add(Lite lite, SysUser sysUser) {
        String result = Lite.validAdd(lite);
        if (result != null)
            return RestResult.fail(result);
        sysUser.setUsername(lite.getSysUname());
        if (StringUtils.isEmpty(sysUser.getUsername())
                || StringUtils.isEmpty(sysUser.getPwd())
                || sysUser.getPwd().length() < 6) {
            return RestResult.fail("账号或密码不能为空且密码不能小于6位");
        }
        int addResult = adminLiteService.add(lite, sysUser);
        return RestResult.success();
    }


    /**
     * 修改加盟店
     *
     * @param lite
     * @param sysUser
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public JSONObject update(Lite lite, SysUser sysUser, @PathVariable Integer id) {
        if (id == null)
            return RestResult.fail("未找到该加盟店");
        String result = Lite.validAdd(lite);
        if (result != null)
            return RestResult.fail(result);
        if (lite.getSysUserId() == null)
            return RestResult.fail("加盟店未绑定管理员账号");
        if (StringUtils.isEmpty(sysUser.getPwd()) || sysUser.getPwd().length() < 6)
            return RestResult.fail("密码长度不能小于6位");
        int updateResult = adminLiteService.update(lite, sysUser);
        return updateResult > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * 删除分公司
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public JSONObject del(@PathVariable Integer id) {
        if (id == null) {
            return RestResult.fail();
        }
        int result = adminLiteService.delete(id);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * 加盟店上下线
     *
     * @param lite
     * @param id
     * @return
     */
    @PutMapping("{id}/enabled")
    public JSONObject updateEnabledStatus(Lite lite, @PathVariable Integer id) {
        if (id == null || lite.getIsEnabled() == null)
            return RestResult.fail();
        int result = adminLiteService.updateEnabled(lite);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }


    /**
     * 获取我的加盟店详情
     *
     * @return
     */
    @GetMapping("mine")
    public JSONObject getMine() {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前加盟店信息");
        Lite lite=new Lite();
        lite.setId(Integer.valueOf(liteId));
        Lite result = liteService.findAll(lite).get(0);
        return RestResult.success(result);
    }

    /**
     * 修改我的加盟店信息
     *
     * @param lite
     * @return
     */
    @PutMapping("mine")
    public JSONObject updateMine(Lite lite) {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前加盟店信息");
        lite.setId(Integer.valueOf(liteId));
        int result = adminLiteService.update(lite, null);
        return result > 0 ? RestResult.success(lite) : RestResult.fail();
    }

//    @Resource
//    private CityInfoMapper cityInfoMapper;
//
//    @PostMapping("test")
//    public JSONObject test(@RequestBody JSONArray jsonArray) {
//        CityInfo cityInfo;
//        int start = 10;
//        for (int i = 0; i < jsonArray.size(); i++) {
//            cityInfo = new CityInfo();
//            cityInfo.setId(start);
//            cityInfo.setName(jsonArray.getJSONObject(i).getString("p"));
//            cityInfo.setPid(0);
//            cityInfo.setType(0);
//            cityInfoMapper.insertSelective(cityInfo);
//            JSONObject cityObj = jsonArray.getJSONObject(i);
//            if(cityObj==null)continue;
//            JSONArray cities =cityObj.getJSONArray("c");
//            int cStart = 0;
//
//            if (cities != null) {
//                for (int j = 0; j < cities.size(); ++j) {
//                    cityInfo = new CityInfo();
//                    String cStartStr = cStart < 10 ? ("0" + cStart) : String.valueOf(cStart);
//                    cityInfo.setId(Integer.valueOf(start + cStartStr));
//                    cityInfo.setPid(start);
//                    cityInfo.setName(cities.getJSONObject(j).getString("n"));
//                    cityInfo.setType(1);
//                    cityInfoMapper.insertSelective(cityInfo);
//                    JSONObject areaObj = cities.getJSONObject(j);
//                    if(areaObj==null)continue;
//                    JSONArray areas = areaObj.getJSONArray("a");
//                    int aStart = 0;
//                    if (areas != null) {
//                        for (int k = 0; k < areas.size(); ++k) {
//                            cityInfo = new CityInfo();
//                            String aStartStr = aStart < 10 ? ("0" + aStart) : String.valueOf(aStart);
//                            cityInfo.setId(Integer.valueOf(start + cStartStr + aStartStr));
//                            cityInfo.setPid(Integer.valueOf(start + cStartStr));
//                            cityInfo.setName(areas.getJSONObject(k).getString("s"));
//                            cityInfo.setType(2);
//                            cityInfoMapper.insertSelective(cityInfo);
//                            ++aStart;
//                        }
//                    }
//                    ++cStart;
//                }
//            }
//
//
//            ++start;
//        }
//        return RestResult.fail();
//    }

}
