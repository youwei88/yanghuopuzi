package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.Theme;
import com.wmeimob.custom.yhpz.dao.ThemeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/15.
 */
@Service
public class ThemeService {

    @Resource
    private ThemeMapper themeMapper;

    /**
     * 获取主题设置
     * @return
     */
    public Theme getThemeConfig(Theme theme){
        List<Theme> themeConfigs = themeMapper.select(theme);
        if(themeConfigs.size()==1){
            return themeConfigs.get(0);
        }
        return null;
    }
}
