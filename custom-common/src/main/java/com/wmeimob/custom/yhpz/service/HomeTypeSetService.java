package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.HomeTypeSet;
import com.wmeimob.custom.yhpz.dao.HomeTypeSetMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/14.
 */
@Service
public class HomeTypeSetService {

    @Resource
    private HomeTypeSetMapper homeTypeSetMapper;


    /**
     * 条件查询homeTypeSet
     *
     * @param homeTypeSet
     * @return
     */
    public List<HomeTypeSet> getHomeTypes(HomeTypeSet homeTypeSet) {
        return homeTypeSetMapper.select(homeTypeSet);
    }
}
