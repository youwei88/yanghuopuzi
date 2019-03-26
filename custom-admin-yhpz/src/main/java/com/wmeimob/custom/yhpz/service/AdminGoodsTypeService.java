package com.wmeimob.custom.yhpz.service;

import com.alibaba.fastjson.JSONArray;
import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.yhpz.bean.Goods;
import com.wmeimob.custom.yhpz.bean.GoodsType;
import com.wmeimob.custom.yhpz.dao.GoodsMapper;
import com.wmeimob.custom.yhpz.dao.GoodsTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinez on 2017/7/20.
 */
@Service
@Transactional
public class AdminGoodsTypeService {



    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 排序起始数值
     */
    private Integer orderNo = 0;

    /**
     * 级别
     */
    private Integer deep = 3;


    /**
     * 更新
     *
     * @param goodsType
     * @return
     */
    public int update(GoodsType goodsType) {
        if (goodsType.getId() == null) return 0;
        int result = goodsTypeMapper.updateByPrimaryKeySelective(goodsType);

        if (result > 0) {
            Example example=new Example(Goods.class);
            example.createCriteria().andEqualTo("typeId");
            Goods goods=new Goods();
            goods.setTypeId(goodsType.getId());
            goods.setBrandName(goodsType.getTypeName());
            goodsMapper.updateByExampleSelective(goods,example);
        }
        return result;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int del(Integer id) {

        Goods goods=new Goods();
        goods.setTypeId(id);
        int goodsCount = goodsMapper.selectCount(goods);
        if(goodsCount>0){
            throw new CustomException("该类型下有商品，无法删除");
        }

        //TODO检查子节点
        if (id == null) return 0;
        GoodsType goodsBrand = new GoodsType();
        goodsBrand.setParentId(id);
        Integer count = goodsTypeMapper.selectCount(goodsBrand);
        if (count > 0) throw new CustomException("该节点下有子节点，你不能删除它！");
        int result = goodsTypeMapper.deleteByPrimaryKey(id);
        return result;
    }

    /**
     * 新增
     *
     * @param goodsType
     * @return
     */
    public int add(GoodsType goodsType) {
        if (goodsType.getParentId() == null) {
            goodsType.setParentId(0);
        }
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId,"未能查询到分公司信息");
        goodsType.setBranchId(Integer.valueOf(branchId));
        goodsTypeMapper.insertSelective(goodsType);
        return goodsType.getId();
    }

    /**
     * 更新节点关系
     *
     * @param data
     * @return
     */
    public int update(JSONArray data) {
        List<GoodsType> goodsTypeList = new ArrayList<>();
        orderNo = 0;
        goodsTypeList = generatorGoodsBrandList(goodsTypeList, data, null);
        return goodsTypeMapper.updateList(goodsTypeList);
    }

    private List<GoodsType> generatorGoodsBrandList(List<GoodsType> goodsTypeList, JSONArray data, Integer parentId) {
        if (goodsTypeList == null) {
            goodsTypeList = new ArrayList<>();
        }

        GoodsType goodsType;
        for (int i = 0; i < data.size(); ++i) {
            goodsType = new GoodsType();
            Integer id = data.getJSONObject(i).getInteger("id");
            goodsType.setId(id);
            goodsType.setParentId(parentId == null ? 0 : parentId);
            goodsType.setOrderNo(++orderNo);
            goodsTypeList.add(goodsType);
            JSONArray children = data.getJSONObject(i).getJSONArray("children");
            if (children != null && children.size() > 0) {
                goodsTypeList = generatorGoodsBrandList(goodsTypeList, children, id);
            }
        }
        return goodsTypeList;
    }
}
