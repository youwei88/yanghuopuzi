package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.Banner;
import com.wmeimob.custom.yhpz.dao.BannerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * Created by Shinez on 2017/8/14.
 */
@Transactional
@Service
public class AdminBannerService {

    @Resource
    private BannerMapper bannerMapper;


    /**
     * 添加轮播图
     *
     * @param banner
     * @return
     */
    public Banner add(Banner banner) {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店");
        banner.setLiteId(Integer.valueOf(liteId));
        banner.setDataId(banner.getDataId().replace(",",""));
        if(Banner.HREF_TYPE_GOODS.equals(banner.getHrefType())){
            banner.setDataName("");
        }
        bannerMapper.insertSelective(banner);
        return banner;
    }

    /**
     * 修改banner
     *
     * @param banner
     * @return
     */
    public int update(Banner banner) {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店");
        if (banner.getId() == null) return 0;
        if(Banner.HREF_TYPE_GOODS.equals(banner.getHrefType())){
            banner.setDataName("");
        }
        Example example = new Example(Banner.class);
        example.createCriteria()
                .andEqualTo("id", banner.getId())
                .andEqualTo("liteId", liteId);
        int result = bannerMapper.updateByExampleSelective(banner, example);
        return result;
    }

    /**
     * 删除banner
     *
     * @param id
     * @return
     */
    public int del(Integer id) {
        if (id == null) return 0;
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店");
        Example example = new Example(Banner.class);
        example.createCriteria()
                .andEqualTo("liteId", liteId)
                .andEqualTo("id", id);
        int result = bannerMapper.deleteByExample(example);
        return result;
    }
}
