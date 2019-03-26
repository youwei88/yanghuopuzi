package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.yhpz.bean.FreightTemplate;
import com.wmeimob.custom.yhpz.bean.FreightTemplateRelation;
import com.wmeimob.custom.yhpz.dao.FreightTemplateMapper;
import com.wmeimob.custom.yhpz.dao.FreightTemplateRelationMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinez on 2017/8/9.
 */
@Service
@Transactional
public class FreightTemplateService {

    @Resource
    private FreightTemplateMapper freightTemplateMapper;

    @Resource
    private FreightTemplateRelationMapper freightTemplateRelationMapper;


    /**
     * 条件查询
     *
     * @param freightTemplate
     * @return
     */
    public List<FreightTemplate> findAll(FreightTemplate freightTemplate) {
        List<FreightTemplate> list = freightTemplateMapper.select(freightTemplate);
        return list;
    }

    /**
     * 批量保存
     *
     * @return
     */
    public void save(FreightTemplate[] freightTemplateArray) {
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String liteId = DataAuthService.getLiteId(userDetails);
        String branchId = DataAuthService.getBranchId(userDetails);
        Assert.notNull(branchId, "分公司信息未找到");
        List<FreightTemplate> freightTemplateList = new ArrayList<>();
        for (FreightTemplate freightTemplate : freightTemplateArray) {
            freightTemplate.setBranchId(Integer.valueOf(branchId));
            freightTemplate.setLiteId(Integer.valueOf(liteId));
            freightTemplateList.add(freightTemplate);
        }
        freightTemplateMapper.insertList(freightTemplateList);

        for (FreightTemplate freightTemplate : freightTemplateList) {
            String cityIds = freightTemplate.getCityIds();
            String[] citiesIdArray = cityIds.split(",");
            FreightTemplateRelation freightTemplateRelation;
            List<FreightTemplateRelation> freightTemplateRelationList = new ArrayList<>();
            for (String id : citiesIdArray) {
                freightTemplateRelation = new FreightTemplateRelation();
                freightTemplateRelation.setFreightTemplateId(freightTemplate.getId());
                freightTemplateRelation.setCityInfoId(Integer.valueOf(id));
                freightTemplateRelationList.add(freightTemplateRelation);
            }
            if (freightTemplateRelationList.size() > 0) {
                freightTemplateRelationMapper.insertList(freightTemplateRelationList);
            }
        }
    }

    /**
     * 删除运费模板
     *
     * @param id
     * @return
     */
    public int del(Integer id) {
        if (id == null) return 0;
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String liteId = DataAuthService.getLiteId(userDetails);
        String branchId = DataAuthService.getBranchId(userDetails);
        Assert.notNull(branchId, "分公司信息未找到");
        Example example = new Example(FreightTemplate.class);
        example.createCriteria()
                .andEqualTo("branchId", branchId)
                .andEqualTo("liteId", liteId)
                .andEqualTo("id", id);
        int result = freightTemplateMapper.deleteByExample(example);
        if (result > 0) {
            example = new Example(FreightTemplateRelation.class);
            example.createCriteria().andEqualTo("freightTemplateId", id);
            freightTemplateRelationMapper.deleteByExample(example);
        }
        return result;
    }
}
