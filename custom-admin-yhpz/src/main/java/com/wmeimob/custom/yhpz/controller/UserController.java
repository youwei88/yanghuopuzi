package com.wmeimob.custom.yhpz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.annotation.Page;
import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.yhpz.service.LiteService;
import com.wmeimob.custom.yhpz.bean.Branch;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.bean.User;
import com.wmeimob.custom.yhpz.service.BranchService;
import com.wmeimob.custom.yhpz.service.DataAuthService;
import com.wmeimob.custom.yhpz.service.UserService;
import com.wmeimob.tool.message.RestResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shinez on 2017/6/29.
 */
@RestController
@RequestMapping("user")
@Logging
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private BranchService branchService;

    @Resource
    private LiteService liteService;


    /**
     * 查询会员
     *
     * @param request
     * @param user
     * @return
     */
    @GetMapping
    @Page
    public JSONObject getUsers(HttpServletRequest request, User user) {
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentBranchId = DataAuthService.getBranchId(userDetails);
        String currentLiteId = DataAuthService.getLiteId(userDetails);

        Map<String, Object> map = new HashMap<>();
        Branch branch = new Branch();
        Lite lite = new Lite();
        if (currentBranchId != null) {
            //分公司数据权限
            user.setBranchId(Integer.valueOf(currentBranchId));
            map.put("branchAuth", true);
            branch.setId(Integer.valueOf(currentBranchId));
            lite.setBranchId(Integer.valueOf(currentBranchId));
        }

        if (currentLiteId != null) {
            //加盟店数据权限
            user.setLiteId(Integer.valueOf(currentLiteId));
            map.put("liteAuth", true);
            lite.setId(Integer.valueOf(currentLiteId));
        }

        List<User> users = userService.findAll(user);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        map.put("page", pageInfo);
        List<Branch> branches = branchService.findAll(branch);

        List<Lite> lites = liteService.findAll(lite);
        map.put("branches", branches);
        map.put("lites", lites);
        return RestResult.success(map);
    }


    /***
     * 修改用户
     * @param request
     * @param user
     * @return
     */
    @PutMapping("{id}")
    public JSONObject updateUser(HttpServletRequest request,User user){
        if(user.getId()==null)return null;
        int updateResult = userService.update(user);
        return updateResult>0?RestResult.success():RestResult.fail();
    }


}
