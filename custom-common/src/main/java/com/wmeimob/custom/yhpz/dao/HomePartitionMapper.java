/*
* HomePartitionMapper.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-08-14 13:44 Created
*/ 
package com.wmeimob.custom.yhpz.dao;

import com.wmeimob.custom.yhpz.bean.HomePartition;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HomePartitionMapper extends Mapper<HomePartition> {

    @Select("SELECT * FROM home_partition WHERE  parent_id = #{parentId} AND lite_id = #{liteId}  ORDER BY order_no")
    @ResultMap("TreeMap")
    List<HomePartition> selectHomePatition(HomePartition homePartition);

    /**
     * 根据parentid递归查询
     * @return
     */
    @Select("SELECT * FROM home_partition WHERE  parent_id = #{parentId}  ORDER BY order_no")
    @ResultMap("TreeMap")
    List<HomePartition> selectChildren(Integer parentId);

    int updateList(List<HomePartition> list);

    int deleteList(List<HomePartition> list);
}