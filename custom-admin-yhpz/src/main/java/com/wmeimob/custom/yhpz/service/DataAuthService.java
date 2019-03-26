package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.system.security.DataAuthHelper;
import com.wmeimob.custom.system.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Shinez on 2017/6/29.
 */
public class DataAuthService extends DataAuthHelper {

    public static final String DATA_AUTH_BY_BRANCH_ID="BY_BRANCH_ID";
    public static final String DATA_AUTH_BY_LITE_ID = "BY_LITE_ID";

    public DataAuthService(SysUserService userService) {
        super(userService);
    }

    /**
     * 获取分公司数据权限值
     * @param userDetails
     * @return
     */
    public static String getBranchId(UserDetails userDetails ){
        return getByUserDetails(userDetails,DATA_AUTH_BY_BRANCH_ID);
    }

    /**
     * 获取分公司数据权限值
     * @return
     */
    public static String getBranchId(){
        return getByUserDetails(DATA_AUTH_BY_BRANCH_ID);
    }

    /**
     * 获取加盟店数据权限值
     * @param userDetails
     * @return
     */
    public static String getLiteId(SysUser userDetails) {
        return getByUserDetails(userDetails,DATA_AUTH_BY_LITE_ID);
    }

    /**
     * 获取加盟店数据权限值
     * @return
     */
    public static String getLiteId() {
        return getByUserDetails(DATA_AUTH_BY_LITE_ID);
    }





}
