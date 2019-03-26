package com.wmeimob.custom.system.service;

import com.wmeimob.custom.yhpz.bean.CityInfo;
import com.wmeimob.custom.yhpz.dao.CityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Shinez on 2017/8/8.
 */
@Service
public class CityService {

    private static CityInfoMapper mapper;

    private static List<CityInfo> CITY_INFO_LIST;

    @Autowired
    public CityService(CityInfoMapper cityInfoMapper) {
        mapper = cityInfoMapper;
    }

    private static List<CityInfo> getCityInfoList() {
        if (CITY_INFO_LIST == null) {
            CITY_INFO_LIST = mapper.selectByType(0);
        }
        return CITY_INFO_LIST;
    }

    public List<CityInfo> getCities() {
        return getCityInfoList();
    }
}
