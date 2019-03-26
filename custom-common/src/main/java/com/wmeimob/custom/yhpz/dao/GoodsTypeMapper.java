/*
* GoodsTypeMapper.java
* EnTropyShiNe
* Copyright © 2017 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-07-20 17:29 Created
*/ 
package com.wmeimob.custom.yhpz.dao;

import com.wmeimob.custom.yhpz.bean.GoodsType;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsTypeMapper extends Mapper<GoodsType> {


    /**
     * 批量更新
     * @param goodsTypeList
     * @return
     */
    int updateList(List<GoodsType> goodsTypeList);



    /**
     * 根据parentid和branchID查询
     * @return
     */
    @Select("SELECT * FROM goods_type WHERE  parent_id = #{parentId} AND branch_id = #{branchId}  ORDER BY order_no")
    @ResultMap("TreeMap")
    List<GoodsType> selectForTree(GoodsType goodsType);

    /**
     * 根据parentid递归查询
     * @return
     */
    @Select("SELECT * FROM goods_type WHERE  parent_id = #{parentId}  ORDER BY order_no")
    @ResultMap("TreeMap")
    List<GoodsType> selectChildren(Integer parentId);


}