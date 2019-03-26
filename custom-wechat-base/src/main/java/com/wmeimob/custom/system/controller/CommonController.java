package com.wmeimob.custom.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmeimob.custom.annotation.Logging;
import com.wmeimob.custom.annotation.Token;
import com.wmeimob.custom.system.bean.DataDictionary;
import com.wmeimob.custom.system.dao.DataDictionaryMapper;
import com.wmeimob.tool.message.RestResult;
import com.wmeimob.tool.qiniu.QiniuUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shinez on 2017/2/6.
 */
@RestController
@Logging
@CrossOrigin
public class CommonController {

    /**
     * 系统数据字典
     */
    private static Map<Integer, DataDictionary> dataDictionaryMap;

    @Resource
    private DataDictionaryMapper dataDictionaryMapper;


    /**
     * 获取七牛上传token
     * @param request
     * @return
     */
    @GetMapping("/getQiniuToken")
    public JSONObject getQiniuToken(HttpServletRequest request) {
        Map<String,Object> map = RestResult.getDataMap();
        map.put("uploadToken", QiniuUtil.getToken());
        map.put("domain", QiniuUtil.getDomain());
        return RestResult.success(map);
    }

    /**
     * 获取数据字典
     * @param request
     * @return
     */
    @GetMapping("data-dictionary")
    public JSONObject getDataDict(HttpServletRequest request){
        if(dataDictionaryMap==null) {
            dataDictionaryMap = new HashMap<>();
            List<DataDictionary> dataDictionaryList = dataDictionaryMapper.selectAll();
            dataDictionaryList.forEach(dd -> dataDictionaryMap.put(dd.getId(), dd));
        }
        Map<String,Object> map=new HashMap<>();
        map.put("dataDicts",dataDictionaryMap);
        return RestResult.success(map);
    }



}
