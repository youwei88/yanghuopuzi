package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.dao.LiteMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/11.
 */
@Service("liteService")
public class LiteService {

    @Resource
    private LiteMapper liteMapper;

    /**
     * 条件查询全部
     *
     * @param lite
     * @return
     */
    public List<Lite> findAll(Lite lite,String...props) {
        Example example = new Example(Lite.class);
        if(props!=null&&props.length>0){
            example.selectProperties(props);
        }
        example.createCriteria()
                .andLike("liteName", "%" + (lite.getLiteName() == null ? "" : lite.getLiteName()) + "%")
                .andEqualTo("id", lite.getId())
                .andEqualTo("branchId", lite.getBranchId())
                .andEqualTo("allowedSelfShelves", lite.getAllowedSelfShelves())
                .andEqualTo("province", lite.getProvince())
                .andEqualTo("city", lite.getCity())
                .andEqualTo("area", lite.getArea())
                .andEqualTo("isEnabled", lite.getIsEnabled());
        List<Lite> list = liteMapper.selectByExample(example);
        return list;
    }

}
