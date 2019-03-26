package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.Theme;
import com.wmeimob.custom.yhpz.dao.ThemeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * Created by Shinez on 2017/8/15.
 */
@Service
@Transactional
public class AdminThemeService {

    @Resource
    private ThemeMapper themeMapper;

    /**
     * 修改主题
     *
     * @param theme
     * @return
     */
    public int update(Theme theme) {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "无法获取当前登陆加盟店");
        Theme themeData = new Theme();
        themeData.setLiteId(Integer.valueOf(liteId));
        themeData = themeMapper.selectOne(themeData);
        if(themeData==null){
            //初始化
            theme.setId(null);
            theme.setLiteId(Integer.valueOf(liteId));
            themeMapper.insertSelective(theme);
            return 1;
        }
        Example example = new Example(Theme.class);
        example.createCriteria()
                .andEqualTo("liteId", liteId)
                .andEqualTo("id",theme.getId());
        int result = themeMapper.updateByExampleSelective(theme,example);
        return result;
    }
}
