/*
* WechatMpsMapper.java
* EnTropyShiNe
* Copyright © 2016 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-06 15:12 Created
*/ 
package com.wmeimob.custom.system.dao;

import com.wmeimob.custom.system.bean.WechatMps;
import com.wmeimob.tool.db.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface WechatMpsMapper extends Mapper<WechatMps>,DataRoleQueryDefineMapper<WechatMps> {

    @Override
    @Select("SELECT id,nick_name FROM wechat_mps")
    @ResultMap("BaseResultMap")
    List<WechatMps> selectForDataRole();

}