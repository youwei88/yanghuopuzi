package com.wmeimob.custom.yhpz.service;

import com.alibaba.fastjson.JSONArray;
import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.yhpz.bean.Goods;
import com.wmeimob.custom.yhpz.bean.GoodsBrand;
import com.wmeimob.custom.yhpz.dao.GoodsBrandMapper;
import com.wmeimob.custom.yhpz.dao.GoodsMapper;
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
@Transactional
@Service
public class AdminGoodsBrandService {


    @Resource
    private GoodsBrandMapper goodsBrandMapper;

    @Resource
    private GoodsMapper goodsMapper;


    private static final Integer ROOT_NODE = 0;




    /**
     * 排序起始数值
     */
    private Integer orderNo = 0;

    /**
     * 级别
     */
    private Integer deep = 2;


    /**
     * 更新
     *
     * @param goodsBrand
     * @return
     */
    public int update(GoodsBrand goodsBrand) {
        if (goodsBrand.getId() == null) return 0;
        int result = goodsBrandMapper.updateByPrimaryKeySelective(goodsBrand);

        if (result > 0) {

            Example example=new Example(Goods.class);
            example.createCriteria().andEqualTo("brandId");
            Goods goods=new Goods();
            goods.setBrandId(goodsBrand.getId());
            goods.setBrandName(goodsBrand.getBrandName());
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
        goods.setBrandId(id);
        int goodsCount = goodsMapper.selectCount(goods);
        if(goodsCount>0){
            throw new CustomException("该品牌下有商品，无法删除");
        }
        if (id == null) return 0;
        GoodsBrand goodsBrand = new GoodsBrand();
        goodsBrand.setParentId(id);
        Integer count = goodsBrandMapper.selectCount(goodsBrand);
        if (count > 0) throw new CustomException("该节点下有子节点，你不能删除它！");
        int result = goodsBrandMapper.deleteByPrimaryKey(id);
        return result;
    }

    /**
     * 新增
     *
     * @param goodsBrand
     * @return
     */
    public int add(GoodsBrand goodsBrand) {
        if (goodsBrand.getParentId() == null) {
            goodsBrand.setParentId(0);
        }
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId,"未能查询到分公司信息");
        goodsBrand.setBranchId(Integer.valueOf(branchId));
        goodsBrandMapper.insertSelective(goodsBrand);
        return goodsBrand.getId();
    }

    /**
     * 更新节点关系
     *
     * @param data
     * @return
     */
    public int update(JSONArray data) {
        List<GoodsBrand> goodsBrandList = new ArrayList<>();
        orderNo = 0;
        goodsBrandList = generatorGoodsBrandList(goodsBrandList, data, null);
        return goodsBrandMapper.updateList(goodsBrandList);
    }

    private List<GoodsBrand> generatorGoodsBrandList(List<GoodsBrand> goodsBrandList, JSONArray data, Integer parentId) {
        if (goodsBrandList == null) {
            goodsBrandList = new ArrayList<>();
        }

        GoodsBrand goodsBrand;
        for (int i = 0; i < data.size(); ++i) {
            goodsBrand = new GoodsBrand();
            Integer id = data.getJSONObject(i).getInteger("id");
            goodsBrand.setId(id);
            goodsBrand.setParentId(parentId == null ? 0 : parentId);
            goodsBrand.setOrderNo(++orderNo);
            goodsBrandList.add(goodsBrand);
            JSONArray children = data.getJSONObject(i).getJSONArray("children");
            if (children != null && children.size() > 0) {
                goodsBrandList = generatorGoodsBrandList(goodsBrandList, children, id);
            }
        }
        return goodsBrandList;
    }

}
