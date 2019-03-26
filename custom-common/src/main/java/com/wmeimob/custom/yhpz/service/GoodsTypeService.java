package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.GoodsType;
import com.wmeimob.custom.yhpz.dao.GoodsTypeMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/8/12.
 */
@Service
public class GoodsTypeService {


    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    private static final Integer ROOT_NODE = 0;

    /**
     * 查询全部商品类型
     *
     * @return
     * @param goodsType
     */
    public List<GoodsType> findAll(GoodsType goodsType) {
        Example example = new Example(GoodsType.class);
        example.orderBy("orderNo");
        example.createCriteria().andEqualTo("branchId",goodsType.getBranchId());
        return goodsTypeMapper.selectByExample(example);
    }

    /**
     * 查询全部树形商品类型
     * @return
     * @param goodsType
     */
    public List<GoodsType> findAllForTree(GoodsType goodsType){
        goodsType.setParentId(ROOT_NODE);
        return goodsTypeMapper.selectForTree(goodsType);
    }
}
