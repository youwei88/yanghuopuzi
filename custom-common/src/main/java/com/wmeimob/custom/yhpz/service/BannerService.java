package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.Banner;
import com.wmeimob.custom.yhpz.dao.BannerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/14.
 */
@Service
public class BannerService {

    @Resource
    private BannerMapper bannerMapper;


    /**
     * 条件查询banner
     * @param banner
     * @return
     */
    public List<Banner> getBanners(Banner banner){
        return bannerMapper.select(banner);
    }
}
