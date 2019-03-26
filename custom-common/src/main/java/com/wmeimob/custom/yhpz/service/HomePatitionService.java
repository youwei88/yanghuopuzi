package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.HomePartition;
import com.wmeimob.custom.yhpz.dao.HomePartitionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/14.
 */
@Service
public class HomePatitionService {

    @Resource
    private HomePartitionMapper homePartitionMapper;

    /**
     * 查询
     * @param homePartition
     * @return
     */
    public List<HomePartition> findAll(HomePartition homePartition) {
        homePartition.setParentId(0);
        List<HomePartition> queryList = homePartitionMapper.selectHomePatition(homePartition);
        return queryList;
    }
}
