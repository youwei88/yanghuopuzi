package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.yhpz.bean.Goods;
import com.wmeimob.custom.yhpz.bean.GoodsLite;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.dao.GoodsLiteMapper;
import com.wmeimob.custom.yhpz.dao.GoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinez on 2017/7/31.
 */
@Service
@Transactional
public class AdminGoodsService {


    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsLiteMapper goodsLiteMapper;

    @Resource
    private LiteService liteService;


    /**
     * 更新加盟店商品信息
     *
     * @param goodsLite
     * @return
     */
    public int update(GoodsLite goodsLite) {
        Example example = new Example(GoodsLite.class);
        if (goodsLite.getLiteId() == null) return 0;
        example.createCriteria().andEqualTo("liteId", goodsLite.getLiteId());
        int result = goodsLiteMapper.updateByExampleSelective(goodsLite, example);
        return result;
    }

    /**
     * 添加商品
     *
     * @param goods
     */
    public void add(Goods goods) {
        String brandIdStr = DataAuthService.getBranchId();
        Assert.notNull(brandIdStr, "分公司信息未找到");
        goods.setBranchId(Integer.valueOf(brandIdStr));

        //普通商品，设置包邮策略为空,设置下架
        if (Goods.GOODS_PROP_NORMAL.equals(goods.getGoodsProp())) {
            goods.setFreeShipping(null);
            goods.setIsEnabled(false);
        }
        goodsMapper.insertSelective(goods);
    }

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    public int updateGoods(Goods goods) {
        if (goods.getId() == null) return 0;
        String brandIdStr = DataAuthService.getBranchId();
        Assert.notNull(brandIdStr, "分公司信息未找到");
        goods.setBranchId(Integer.valueOf(brandIdStr));
        //普通商品，设置包邮策略为空,设置下架
        if (Goods.GOODS_PROP_NORMAL.equals(goods.getGoodsProp())) {
            goods.setFreeShipping(null);
            goods.setIsEnabled(false);
        }
        Example example = new Example(Goods.class);
        example.createCriteria()
                .andEqualTo("branchId", goods.getBranchId())
                .andEqualTo("id", goods.getId());
        int result = goodsMapper.updateByExampleSelective(goods, example);

        if (result > 0) {
            //查询非自主上架的加盟店
            Lite lite = new Lite();
            lite.setBranchId(Integer.valueOf(brandIdStr));
            lite.setAllowedSelfShelves(false);
            List<Lite> liteList = liteService.findAll(lite);
            List<Integer> liteIds = new ArrayList<>();
            liteList.forEach(l -> liteIds.add(l.getId()));

            if (liteIds.size() > 0) {
                Example liteGoodsExample = new Example(GoodsLite.class);
                liteGoodsExample.createCriteria()
                        .andIn("liteId", liteIds)
                        .andEqualTo("goodsId", goods.getId());
                GoodsLite goodsLite = new GoodsLite();
                goodsLite.setIsEnabled(goods.getIsEnabled());
                goodsLiteMapper.updateByExampleSelective(goodsLite, liteGoodsExample);
            }
        }
        return result;
    }

    /**
     * 根据ID查询商品详情
     *
     * @param id
     * @return
     */
    public Goods findById(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }


    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    public int del(Integer id) {
        //检查加盟店商品的绑定情况
        GoodsLite goodsLite = new GoodsLite();
        goodsLite.setGoodsId(id);
        int count = goodsLiteMapper.selectCount(goodsLite);
        if (count > 0)
            throw new CustomException("该商品有加盟店绑定，您无法删除它");
        int result = goodsMapper.deleteByPrimaryKey(id);
        return result;
    }
}
