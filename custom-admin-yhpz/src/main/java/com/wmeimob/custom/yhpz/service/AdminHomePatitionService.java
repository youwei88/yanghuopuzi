package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.yhpz.bean.HomePartition;
import com.wmeimob.custom.yhpz.dao.HomePartitionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinez on 2017/8/14.
 */
@Service
@Transactional
public class AdminHomePatitionService {


    @Resource
    private HomePartitionMapper homePartitionMapper;


    /**
     * 保存首页区块
     *
     * @param partition
     */
    public void save(HomePartition partition) {
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店信息");
        partition.setLiteId(Integer.valueOf(liteId));
        partition.setParentId(0);
        if(HomePartition.PARTITION_GOODS.equals(partition.getPartitionType())){
            partition.setDataId(partition.getGoodsNo());
        }
        if(HomePartition.PARTITION_TYPE.equals(partition.getPartitionType())){
            partition.setDataId(String.valueOf(partition.getTypeId()));
        }
        if (partition.getId() == null) {
            homePartitionMapper.insertSelective(partition);
        } else {
            Example example = new Example(HomePartition.class);
            example.createCriteria()
                    .andEqualTo("id", partition.getId())
                    .andEqualTo("liteId", liteId);
            homePartitionMapper.updateByExampleSelective(partition, example);
        }
        List<HomePartition> newList = new ArrayList<>();
        List<HomePartition> updateList = new ArrayList<>();
        for (HomePartition child : partition.getHomePartitions()) {
            child.setLiteId(Integer.valueOf(liteId));
            child.setParentId(partition.getId());
            if(HomePartition.PARTITION_GOODS.equals(child.getPartitionType())){
                child.setDataId(child.getGoodsNo());
            }
            if(HomePartition.PARTITION_TYPE.equals(child.getPartitionType())){
                child.setDataId(String.valueOf(child.getTypeId()));
            }
            if (child.getId() == null) {
                newList.add(child);
            } else {
                updateList.add(child);
            }
        }
        if (newList.size() > 0) {
            homePartitionMapper.insertList(newList);
        }
        if (updateList.size() > 0) {
            homePartitionMapper.updateList(updateList);
        }
    }

//    /**
//     * 批量修改
//     *
//     * @param partitions
//     * @return
//     */
//    public int update(HomePartition[] partitions) {
//        String liteId = DataAuthService.getLiteId();
//        Assert.notNull(liteId, "未能获取当前登陆的加盟店信息");
//        List<HomePartition> list = new ArrayList<>();
//        for (HomePartition partition : partitions) {
//            partition.setParentId(Integer.valueOf(liteId));
//            list.add(partition);
//        }
//        int result = 0;
//        if (list.size() > 0) {
//            result = homePartitionMapper.updateList(list);
//        }
//        return result;
//    }

    /**
     * 根据ID删除
     *
     * @param idArray
     * @return
     */
    public int delChildren(Integer[] idArray) {
        if (idArray.length == 0) return 0;
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店信息");
        List<HomePartition> list = new ArrayList<>();
        for (int i = 0; i < idArray.length; i++) {
            if(idArray[i]==null)continue;
            HomePartition homePartition = new HomePartition();
            homePartition.setId(idArray[i]);
            homePartition.setLiteId(Integer.valueOf(liteId));
            list.add(homePartition);
        }

        int result = 0;
        if (list.size() > 0) {
            result = homePartitionMapper.deleteList(list);
        }
        return result;
    }

    /**
     * 删除父版块
     * @param id
     * @return
     */
    public int delParentPartition(Integer id) {
        if (id==null) return 0;
        String liteId = DataAuthService.getLiteId();
        Assert.notNull(liteId, "未能获取当前登陆的加盟店信息");
        Example example=new Example(HomePartition.class);
        example.createCriteria()
                .andEqualTo("id",id)
                .andEqualTo("liteId",liteId);
        example.or()
                .andEqualTo("parentId",id)
                .andEqualTo("liteId",liteId);
        int result = homePartitionMapper.deleteByExample(example);
        return result;
    }
}
