/*
* WechatMchMapper.java
* EnTropyShiNe
* Copyright © 2016 ShiNez All Rights Reserved
* 作者：ShiNez
* QQ：136266602
* 2017-06-06 15:12 Created
*/ 
package com.wmeimob.custom.system.dao;

import com.wmeimob.custom.system.bean.WechatMch;
import com.wmeimob.tool.db.Mapper;

public interface WechatMchMapper extends Mapper<WechatMch> {

    String selectMchKeyByAppid(String appid);
}