/*
* SysUserDataRoleMapper.java
* EnTropyShiNe
* Copyright © 2016 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-06 17:34 Created
*/ 
package com.wmeimob.custom.system.dao;

import com.wmeimob.custom.system.bean.SysUserDataRole;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.Insert;

public interface SysUserDataRoleMapper extends Mapper<SysUserDataRole> {

    @Insert("INSERT IGNORE INTO sys_user_data_role(sys_user_id,table_name,column_name,column_value,data_role_code,extends_user)VALUES(#{sysUserId},#{tableName},#{columnName},#{columnValue},#{dataRoleCode},#{extendsUser})")
    void insertIgnore(SysUserDataRole sysUserDataRole);
}