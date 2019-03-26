package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.system.bean.SysRole;
import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.system.bean.SysUserDataRole;
import com.wmeimob.custom.system.dao.SysRoleMapper;
import com.wmeimob.custom.system.dao.SysUserDataRoleMapper;
import com.wmeimob.custom.system.dao.SysUserMapper;
import com.wmeimob.custom.system.service.SysUserDataRoleService;
import com.wmeimob.custom.system.service.SysUserService;
import com.wmeimob.custom.yhpz.bean.GoodsLite;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.dao.LiteMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Shinez on 2017/6/30.
 */
@Transactional
@Service("adminLiteService")
public class AdminLiteService {


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserDataRoleMapper sysUserDataRoleMapper;

    @Resource
    private SysUserDataRoleService sysUserDataRoleService;

    @Resource
    private LiteMapper liteMapper;

    @Resource
    private BranchService branchService;

    @Resource
    private AdminGoodsService adminGoodsService;


    /**
     * 加盟店超管角色CODE
     */
    private static final String LITE_ROLE_CODE = "LITE";

    /**
     * 新增
     *
     * @param lite
     * @param sysUser
     * @return
     */
    public int add(Lite lite, SysUser sysUser) {

        //添加加盟店
        Date date = new Date();
        lite.setCreatedAt(date);
        lite.setUpdatedAt(date);
        lite.setUserCount(0);
        lite.setIsEnabled(false);

        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String branchIdStr = DataAuthService.getBranchId(userDetails);
        Assert.notNull(branchIdStr, "未能识别当前分公司信息");
        lite.setBranchId(Integer.valueOf(branchIdStr));
        liteMapper.insertSelective(lite);


        //添加管理后台用户
        SysUser.filterUserName(sysUser.getUsername());
        sysUser.setPwd(DigestUtils.md5DigestAsHex(sysUser.getPwd().getBytes()));
        sysUser.setUserType(SysUser.USER_TYPE_SUPER);
        SysRole sysRoleCondition = new SysRole();
        sysRoleCondition.setRoleCode(LITE_ROLE_CODE);
        SysRole sysRole = sysRoleMapper.selectOne(sysRoleCondition);
        sysUser.setRoleId(sysRole.getId());
        sysUser.setRoleName(sysRole.getRoleName());
        sysUser.setIsEnabled(false);//默认禁用
        sysUser.setIsLocked(false);
        sysUser.setCreatedUser(userDetails.getId());
        sysUser.setEnableDataRole(true);
        sysUser.setCreatedAt(new Date());
        sysUser.setNickname(sysUser.getUsername() + "(加盟店)");
        try {
            sysUserMapper.insertSelective(sysUser);
        } catch (DuplicateKeyException e) {
            throw new CustomException("管理员账号重复");
        }
        //获取自己的数据权限，附加到添加的用户上
        List<SysUserDataRole> sysUserDataRoles = sysUserService.getDataRole(userDetails.getUsername());
        sysUserDataRoles.forEach(sud -> {
            sud.setExtendsUser(userDetails.getId());
            sud.setSysUserId(sysUser.getId());
            sud.setId(null);
            sysUserDataRoleMapper.insertSelective(sud);
        });

        //检查数据权限维度正确性
        SysUserDataRole sysUserDataRole = sysUserDataRoleService.getAdminDataRoleByDataRoleCode(DataAuthService.DATA_AUTH_BY_LITE_ID);
        Assert.notNull(sysUserDataRole, "系统暂不支持该方式创建并绑定账户相关权限");

        //绑定加盟店数据权限
        sysUserDataRole.setSysUserId(sysUser.getId());
        sysUserDataRole.setColumnValue(lite.getId().toString());
        sysUserDataRole.setId(null);
        sysUserDataRoleMapper.insertSelective(sysUserDataRole);

        //更新加盟店里管理员账号信息
        lite.setSysUserId(sysUser.getId());
        lite.setSysUname(sysUser.getUsername());
        int result = liteMapper.updateByPrimaryKeySelective(lite);
        if (result == 0)
            throw new RuntimeException("添加失败");

        //分公司加盟店自增
        branchService.incrementLiteCount(Integer.valueOf(branchIdStr), 1);

        //更新数据权限池的元数据
        sysUserDataRoleService.updateAdminDataRole(DataAuthService.DATA_AUTH_BY_LITE_ID, lite);
        return result;
    }


    /**
     * 删除加盟店
     *
     * @param id
     * @return
     */
    public int delete(Integer id) {
        synchronized (("lite" + id).intern()) {
            //检查会员数量
            Lite lite = liteMapper.selectByPrimaryKey(id);
            if (lite.getUserCount() > 0)
                throw new CustomException("该加盟店下存在会员,你不能删除它");

            if (lite.getSysUserId() == null) {
                if (liteMapper.deleteByPrimaryKey(id) > 0) {
                    return branchService.incrementLiteCount(lite.getBranchId(), -1);
                }
            }
            //检查该加盟店的超管下有没有子账号
            Example example = new Example(SysUser.class);
            example.createCriteria()
                    .andEqualTo("createdUser", lite.getSysUserId())
                    .andEqualTo("userType", SysUser.USER_TYPE_CHILD);

            int childrenCount = sysUserMapper.selectCountByExample(example);
            if (childrenCount > 0) {
                throw new CustomException("该加盟店管理账号下有子账号，你不能删除它");
            }
            //删除管理员账号
            sysUserMapper.deleteByPrimaryKey(lite.getSysUserId());
            //删除管理员的数据权限配置
            example = new Example(SysUserDataRole.class);
            example.createCriteria().andEqualTo("sysUserId", lite.getSysUserId());
            sysUserDataRoleMapper.deleteByExample(example);
            //更新数据权限元数据
            sysUserDataRoleService.removeAdminDataRole(DataAuthService.DATA_AUTH_BY_LITE_ID, lite.getId());
            //删除分公司
            int result = liteMapper.deleteByPrimaryKey(id);
            if (result > 0) {
                //分公司加盟店自减
                branchService.incrementLiteCount(lite.getBranchId(), -1);
            }
            return result;
        }
    }


    /**
     * 修改
     *
     * @param lite
     * @param sysUser
     * @return
     */
    public int  update(Lite lite, SysUser sysUser) {
        if (lite.getId() == null) return 0;
        lite.setSysUname(null);
        lite.setUpdatedAt(new Date());
        lite.setUserCount(null);
        //更新名称
        Example example = new Example(Lite.class);
        String liteId = DataAuthService.getLiteId();
        String branchId = DataAuthService.getBranchId();
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(liteId)) {
            criteria.andEqualTo("id", liteId);
        } else {
            criteria
                    .andEqualTo("branchId", branchId)
                    .andEqualTo("id", lite.getId());
        }
        int result = liteMapper.updateByExampleSelective(lite, example);
        if (result > 0) {
            //更新密码
            if (sysUser != null) {
                sysUser.setPwd(DigestUtils.md5DigestAsHex(sysUser.getPwd().getBytes()));
                sysUser.setId(lite.getSysUserId());
                sysUserMapper.updateByPrimaryKeySelective(sysUser);
            }

            //更新其它冗余字段
            GoodsLite goodsLite = new GoodsLite();
            goodsLite.setLiteId(lite.getId());
            goodsLite.setLiteName(lite.getLiteName());
            adminGoodsService.update(goodsLite);

        }
        //更新数据权限元数据
        sysUserDataRoleService.updateAdminDataRole(DataAuthService.DATA_AUTH_BY_LITE_ID, lite);
        return result;
    }


    /**
     * 加盟店上下线
     *
     * @param lite
     * @return
     */
    public int updateEnabled(Lite lite) {
        if (lite.getId() == null) return 0;
        Example example = new Example(Lite.class);
        example.createCriteria().andEqualTo("id", lite.getId());
        Lite updateLite = new Lite();
        updateLite.setIsEnabled(lite.getIsEnabled());
        int result = liteMapper.updateByExampleSelective(updateLite, example);
        if (result > 0) {
            lite = liteMapper.selectByPrimaryKey(lite.getId());
            SysUser sysUser = new SysUser();
            sysUser.setId(lite.getSysUserId());
            sysUser.setIsEnabled(lite.getIsEnabled());
            result = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
        return result;
    }

    /**
     * 会员数量递增/减
     *
     * @param liteId
     * @return
     */
    public int incrementUserCount(Integer liteId, Integer count) {
        return liteMapper.updateIncrement(liteId, count);
    }

}
