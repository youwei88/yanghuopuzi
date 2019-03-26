/*
* BranchMapper.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-20 14:11 Created
*/
package com.wmeimob.custom.yhpz.dao;

import com.wmeimob.custom.system.dao.DataRoleQueryDefineMapper;
import com.wmeimob.custom.yhpz.bean.Branch;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BranchMapper extends Mapper<Branch>,DataRoleQueryDefineMapper<Branch> {


    @Override
    @Select("SELECT id,branch_name FROM branch")
    @ResultMap("BaseResultMap")
    List<Branch>  selectForDataRole();


    /**
     * 加盟店数量自增（减）
     * @param branchId
     * @param count
     * @return
     */
    @Update("UPDATE branch SET lite_count = lite_count + #{count} WHERE  id = #{branchId} AND lite_count  + #{count}>= 0")
    int updateIncrement(@Param("branchId") Integer branchId, @Param("count") Integer count);
}