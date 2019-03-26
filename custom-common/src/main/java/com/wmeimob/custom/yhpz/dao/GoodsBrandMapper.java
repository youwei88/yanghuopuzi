/*
* GoodsBrandMapper.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-07-20 17:29 Created
*/ 
package com.wmeimob.custom.yhpz.dao;

import com.wmeimob.custom.yhpz.bean.GoodsBrand;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsBrandMapper extends Mapper<GoodsBrand> {

    /**
     * 批量更新
     * @param goodsBrandList
     * @return
     */
    int updateList(List<GoodsBrand> goodsBrandList);


    /**
     * 根据parentid和branchID查询
     * @return
     */
    @Select("SELECT * FROM goods_brand WHERE  parent_id = #{parentId} AND branch_id = #{branchId}  ORDER BY order_no")
    @ResultMap("TreeMap")
    List<GoodsBrand> selectForTree(GoodsBrand goodsBrand);

    /**
     * 根据parentid递归查询
     * @return
     */
    @Select("SELECT * FROM goods_brand WHERE  parent_id = #{parentId}  ORDER BY order_no")
    @ResultMap("TreeMap")
    List<GoodsBrand> selectChildren(Integer parentId);

}