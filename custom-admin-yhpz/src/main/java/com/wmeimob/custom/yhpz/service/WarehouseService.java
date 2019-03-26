package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.yhpz.bean.Goods;
import com.wmeimob.custom.yhpz.bean.Warehouse;
import com.wmeimob.custom.yhpz.dao.GoodsMapper;
import com.wmeimob.custom.yhpz.dao.WarehouseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shinez on 2017/8/5.
 */
@Service
@Transactional
public class WarehouseService {


    @Resource
    private WarehouseMapper warehouseMapper;

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 新增
     *
     * @param warehouse
     * @return
     */
    public Warehouse add(Warehouse warehouse) {
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId, "未找到当前登陆的分公司信息");
        warehouse.setBranchId(Integer.valueOf(branchId));
        warehouse.setUpdatedAt(new Date());
        warehouse.setCreatedAt(new Date());
        warehouseMapper.insertSelective(warehouse);
        return warehouse;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int del(Integer id) {
        if (id == null) return 0;
        //TODO 查询运费模板关联的,如果有关联，删除失败

        //查询商品关联的
        Example example = new Example(Goods.class);
        example.createCriteria().andEqualTo("taxId", id);
        example.or().andEqualTo("postId", id);
        example.selectProperties("id");
        List<Goods> list = goodsMapper.selectByExample(example);
        if (list == null || list.size() > 0) {
            throw new CustomException("该直邮地或保税仓有关联的商品，您无法删除它");
        }
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId, "未找到当前登陆的分公司信息");
        example = new Example(Warehouse.class);
        example.createCriteria()
                .andEqualTo("branchId", Integer.valueOf(branchId))
                .andEqualTo("id", id);
        int result = warehouseMapper.deleteByExample(example);
        return result;
    }


    /**
     * 修改
     * @param warehouse
     * @return
     */
    public int update(Warehouse warehouse) {
        String branchId = DataAuthService.getBranchId();
        Assert.notNull(branchId, "未找到当前登陆的分公司信息");
        Example example=new Example(Warehouse.class);
        example.createCriteria()
                .andEqualTo("id",warehouse.getId())
                .andEqualTo("branchId",branchId);
        warehouse.setUpdatedAt(new Date());
        int result = warehouseMapper.updateByExampleSelective(warehouse,example);
        if(result>0){
            //TODO 更新运费模板中的仓库名称
        }
        return result;
    }

    /**
     * 查询全部
     * @return
     * @param warehouse
     */
    public List<Warehouse> findAll(Warehouse warehouse) {
        if(warehouse.getBranchId()==null)return new ArrayList<>();
        List<Warehouse> list = warehouseMapper.select(warehouse);
        return list;
    }
}
