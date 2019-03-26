/*
* SysPermissionMapper.java
* EnTropyShiNe
* Copyright © 2016 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-06 17:34 Created
*/
package com.wmeimob.custom.system.dao;

import com.wmeimob.custom.system.bean.SysPermission;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysPermissionMapper extends Mapper<SysPermission> {


    /**
     * 查询角色对应的资源权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> selectByRoleId(Integer roleId);


    /**
     * 查询资源对应的权限
     *
     * @param resourceId
     * @param roleType
     * @param createdRole
     * @return
     */
    List<SysPermission> selectByResourceIdAndRoleType(
            @Param("resourceId") Integer resourceId,
            @Param("roleType") Integer roleType,
            @Param("createdRole") Integer createdRole,
            @Param("createdUser") Integer createdUser);
}