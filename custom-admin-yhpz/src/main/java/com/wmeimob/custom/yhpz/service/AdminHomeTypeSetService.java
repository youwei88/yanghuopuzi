package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.HomeTypeSet;
import com.wmeimob.custom.yhpz.dao.HomeTypeSetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * Created by Shinez on 2017/8/14.
 */
@Service
@Transactional
public class AdminHomeTypeSetService {

    @Resource
    private HomeTypeSetMapper homeTypeSetMapper;


    /**
     * 添加分类设置
     * @param homeTypeSet
     * @return
     */
    public HomeTypeSet add(HomeTypeSet homeTypeSet) {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆加盟店");
        homeTypeSet.setLiteId(Integer.valueOf(liteId));
        homeTypeSetMapper.insertSelective(homeTypeSet);
        return homeTypeSet;
    }

    /**
     * 修改banner
     *
     * @param homeTypeSet
     * @return
     */
    public int update(HomeTypeSet homeTypeSet) {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店");
        if (homeTypeSet.getId() == null) return 0;
        Example example = new Example(HomeTypeSet.class);
        example.createCriteria()
                .andEqualTo("id", homeTypeSet.getId())
                .andEqualTo("liteId", liteId);
        int result = homeTypeSetMapper.updateByExampleSelective(homeTypeSet, example);
        return result;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int del(Integer id) {
        if (id == null) return 0;
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店");
        Example example = new Example(HomeTypeSet.class);
        example.createCriteria()
                .andEqualTo("liteId", liteId)
                .andEqualTo("id", id);
        int result = homeTypeSetMapper.deleteByExample(example);
        return result;
    }
}
