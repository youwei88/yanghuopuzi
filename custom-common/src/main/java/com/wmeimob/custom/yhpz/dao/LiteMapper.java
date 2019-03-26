/*
* LiteMapper.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-23 16:38 Created
*/ 
package com.wmeimob.custom.yhpz.dao;

import com.wmeimob.custom.system.dao.DataRoleQueryDefineMapper;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LiteMapper extends Mapper<Lite>,DataRoleQueryDefineMapper<Lite> {

    @Override
    @Select("SELECT id,lite_name FROM lite")
    @ResultMap("BaseResultMap")
    List<Lite> selectForDataRole();


    /**
     * 会员数量递增递减
     * @param liteId
     * @param count
     * @return
     */
    @Update("UPDATE lite SET user_count = user_count + #{count} WHERE  id = #{liteId} AND user_count  + #{count}>= 0")
    int updateIncrement(@Param("liteId") Integer liteId, @Param("count") Integer count);
}