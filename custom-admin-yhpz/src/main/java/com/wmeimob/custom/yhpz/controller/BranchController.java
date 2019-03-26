package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.annotation.Page;
import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.yhpz.bean.Branch;
import com.wmeimob.custom.yhpz.service.BranchService;
import com.wmeimob.tool.message.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/6/20.
 */
@RestController
@RequestMapping("branch")
@Logging
@Slf4j
public class BranchController {

    @Resource
    private BranchService branchService;

    /**
     * 获取所有分公司
     *
     * @param branch
     * @return
     */
    @GetMapping
    @Page
    public JSONObject getAll(Branch branch) {
        List<Branch> branches = branchService.findAll(branch);
        PageInfo<Branch> branchPageInfo = new PageInfo<>(branches);
        return RestResult.success(branchPageInfo);
    }


    /**
     * 添加分公司
     *
     * @param branch
     * @param sysUser
     * @return
     */
    @PostMapping
    public JSONObject add(Branch branch, SysUser sysUser) {
        String result = Branch.validAdd(branch);
        if (result != null)
            return RestResult.fail(result);
        sysUser.setUsername(branch.getSysUname());
        if (StringUtils.isEmpty(sysUser.getUsername())
                || StringUtils.isEmpty(sysUser.getPwd())
                || sysUser.getPwd().length() < 6) {
            return RestResult.fail("账号或密码不能为空且密码不能小于6位");
        }
        int addResult = branchService.add(branch, sysUser);
        return RestResult.success();
    }


    /**
     * 修改分公司
     *
     * @param branch
     * @param sysUser
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public JSONObject update(Branch branch, SysUser sysUser, @PathVariable Integer id) {
        if (id == null)
            return RestResult.fail("未找到该分公司");
        String result = Branch.validAdd(branch);
        if (result != null)
            return RestResult.fail(result);
        if (branch.getSysUserId() == null)
            return RestResult.fail("分公司未绑定管理员账号");
        if (StringUtils.isEmpty(sysUser.getPwd()) || sysUser.getPwd().length() < 6)
            return RestResult.fail("密码长度不能小于6位");
        int updateResult = branchService.update(branch, sysUser);
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
        int result = branchService.delete(id);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

    /**
     * 分公司上下线
     *
     * @param branch
     * @param id
     * @return
     */
    @PutMapping("{id}/enabled")
    public JSONObject updateEnabledStatus(Branch branch, @PathVariable Integer id) {
        if (id == null || branch.getIsEnabled() == null)
            return RestResult.fail();
        int result = branchService.updateEnabled(branch);
        return result > 0 ? RestResult.success() : RestResult.fail();
    }

}
