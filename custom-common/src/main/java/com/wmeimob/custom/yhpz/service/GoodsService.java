package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.Goods;
import com.wmeimob.custom.yhpz.bean.GoodsLite;
import com.wmeimob.custom.yhpz.dao.GoodsLiteMapper;
import com.wmeimob.custom.yhpz.dao.GoodsMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinez on 2017/8/12.
 */
@Service
public class GoodsService {
    @Resource
    private GoodsLiteMapper goodsLiteMapper;
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 合并商品信息
     *
     * @param goodsLiteList
     * @param currentBranchGoods
     * @return
     */
    private Goods generatorGoods(List<GoodsLite> goodsLiteList, Goods currentBranchGoods) {
        for (GoodsLite goodsLite : goodsLiteList) {
            if (goodsLite.getGoodsId().equals(currentBranchGoods.getId())) {
                currentBranchGoods.setLiteName(goodsLite.getLiteName());
                currentBranchGoods.setLiteId(goodsLite.getLiteId());
                if (goodsLite.getSalePrice() != null) {
                    currentBranchGoods.setSalePrice(goodsLite.getSalePrice());
                }
                if (goodsLite.getCostPrice() != null) {
                    currentBranchGoods.setCostPrice(goodsLite.getCostPrice());
                }
                if (goodsLite.getOrderNo() != null) {
                    currentBranchGoods.setOrderNo(goodsLite.getOrderNo());
                }
                if (goodsLite.getPoints() != null) {
                    currentBranchGoods.setPoints(goodsLite.getPoints());
                }
                if (goodsLite.getSaleCount() != null) {
                    currentBranchGoods.setSaleCount(goodsLite.getSaleCount());
                }
                break;
            }
        }
        return currentBranchGoods;
    }


    /**
     * 条件查询商品信息
     *
     * @param goods
     * @return
     */
    public List<Goods> findAll(Goods goods) {
        Example example = new Example(Goods.class);
        example.excludeProperties("details");
        Example.Criteria criteria = example.createCriteria();
        if (goods.getQueryKey() == null || goods.getQueryKey().contains("'") || goods.getQueryKey().contains("or") || goods.getQueryKey().contains("--")) {
            goods.setQueryKey("");
        }

        criteria
                .andEqualTo("brandId", goods.getBrandId())
                .andEqualTo("branchId", goods.getBranchId())
                .andEqualTo("typeId", goods.getTypeId())
                .andEqualTo("goodsProp", goods.getGoodsProp())
                .andCondition("( id = "+goods.getId()+" or goods_no = "+goods.getGoodsNo()+" ) ")
                .andCondition("( goods_name like concat('%','" + goods.getQueryKey() + "','%') or goods_no like concat('%','" + goods.getQueryKey() + "','%')  )");


        List<GoodsLite> goodsLiteList = null;
        if (goods.getLiteId() != null) {
            //查加盟店的商品
            Example goodsLiteExample = new Example(GoodsLite.class);
            goodsLiteExample.createCriteria().andEqualTo("liteId", goods.getLiteId());

            //加盟店的商品列表
            goodsLiteList = goodsLiteMapper.selectByExample(goodsLiteExample);
            List<Integer> goodsIds = new ArrayList<>();
            goodsLiteList.forEach(gll -> goodsIds.add(gll.getGoodsId()));
            if (goodsIds.size() > 0) {
                criteria.andIn("id", goodsIds);
            } else {
                goodsIds.add(-1);
                criteria.andIn("id", goodsIds);
            }
        }
        //分公司的商品列表（不会超出加盟店商品的范围）
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        if (goodsLiteList != null) {
            //替换对应的信息
            List<GoodsLite> finalGoodsLiteList = goodsLiteList;
            goodsList.forEach(g -> generatorGoods(finalGoodsLiteList, g));
        }
        return goodsList;
    }

    /**
     * 获取商品详情
     * @param id
     * @return
     */
    public String findGoodsDetailsById(Integer id) {
        if(id==null)return "";
        Example example = new Example(Goods.class);
        example.selectProperties("details");
        example.createCriteria().andEqualTo("id",id);
        List<Goods> list = goodsMapper.selectByExample(example);
        if(list.size()!=1){
            return "";
        }
        return list.get(0).getDetails();
    }
}
