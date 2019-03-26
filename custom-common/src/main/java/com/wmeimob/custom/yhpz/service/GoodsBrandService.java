package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.GoodsBrand;
import com.wmeimob.custom.yhpz.dao.GoodsBrandMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/12.
 */
@Service
public class GoodsBrandService {

    @Resource
    private GoodsBrandMapper goodsBrandMapper;

    private static final Integer ROOT_NODE = 0;

    /**
     * 查询全部商品品牌
     *
     * @return
     * @param goodsBrand
     */
    public List<GoodsBrand> findAll(GoodsBrand goodsBrand) {
        Example example = new Example(GoodsBrand.class);
        example.orderBy("orderNo");
        example.createCriteria().andEqualTo("branchId",goodsBrand.getBranchId());
        return goodsBrandMapper.selectByExample(example);
    }


    /**
     * 查询全部树形商品品牌
     * @return
     * @param goodsBrand
     */
    public List<GoodsBrand> findAllForTree(GoodsBrand goodsBrand){
        goodsBrand.setParentId(ROOT_NODE);
        return goodsBrandMapper.selectForTree(goodsBrand);
    }
}
