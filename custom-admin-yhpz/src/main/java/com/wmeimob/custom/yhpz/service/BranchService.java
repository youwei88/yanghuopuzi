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
import com.wmeimob.custom.yhpz.bean.Branch;
import com.wmeimob.custom.yhpz.bean.Lite;
import com.wmeimob.custom.yhpz.dao.BranchMapper;
import com.wmeimob.custom.yhpz.dao.LiteMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by Shinez on 2017/6/20.
 */
@Service
@Transactional
public class BranchService {

    @Resource
    private BranchMapper branchMapper;

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


    /**
     * 分公司超管角色CODE
     */
    private static final String BRANCH_ROLE_CODE = "BRANCH";


    /**
     * 条件查询全部
     *
     * @param branch
     * @return
     */
    public List<Branch> findAll(Branch branch) {
        Example example = new Example(Branch.class);
        example.createCriteria()
                .andLike("branchName", "%" + (branch.getBranchName() == null ? "" : branch.getBranchName()) + "%")
                .andEqualTo("id", branch.getId());
        List<Branch> list = branchMapper.selectByExample(example);
        return list;
    }

    /**
     * 新增
     *
     * @param branch
     * @param sysUser
     * @return
     */
    public int add(Branch branch, SysUser sysUser) {

        //添加分公司
        Date date = new Date();
        branch.setCreatedAt(date);
        branch.setUpdatedAt(date);
        branch.setLiteCount(0);
        branch.setIsEnabled(true);
        branchMapper.insertSelective(branch);


        //添加管理后台用户
        SysUser.filterUserName(sysUser.getUsername());
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sysUser.setPwd(DigestUtils.md5DigestAsHex(sysUser.getPwd().getBytes()));
        sysUser.setUserType(SysUser.USER_TYPE_SUPER);
        SysRole sysRoleCondition = new SysRole();
        sysRoleCondition.setRoleCode(BRANCH_ROLE_CODE);
        SysRole sysRole = sysRoleMapper.selectOne(sysRoleCondition);
        sysUser.setRoleId(sysRole.getId());
        sysUser.setRoleName(sysRole.getRoleName());
        sysUser.setIsEnabled(true);
        sysUser.setIsLocked(false);
        sysUser.setCreatedUser(userDetails.getId());
        sysUser.setEnableDataRole(true);
        sysUser.setCreatedAt(new Date());
        sysUser.setNickname(sysUser.getUsername() + "(分公司)");
        try {
            sysUserMapper.insertSelective(sysUser);
        } catch (DuplicateKeyException e) {
            throw new CustomException("管理员账号重复");
        }

        //获取自己的数据权限，附加到添加的用户上
        List<SysUserDataRole> sysUserDataRoles = sysUserService.getDataRole(userDetails.getUsername());
        for (SysUserDataRole sud : sysUserDataRoles) {
            sud.setExtendsUser(userDetails.getId());
            sud.setSysUserId(sysUser.getId());
            sud.setId(null);
            sysUserDataRoleMapper.insertSelective(sud);
        }

        //检查数据权限维度正确性
        SysUserDataRole sysUserDataRole = sysUserDataRoleService.getAdminDataRoleByDataRoleCode(DataAuthService.DATA_AUTH_BY_BRANCH_ID);
        Assert.notNull(sysUserDataRole, "系统暂不支持该方式创建并绑定账户相关权限");


        //绑定分公司数据权限
        sysUserDataRole.setSysUserId(sysUser.getId());
        sysUserDataRole.setColumnValue(branch.getId().toString());
        sysUserDataRole.setId(null);
        sysUserDataRoleMapper.insertSelective(sysUserDataRole);

        //更新分公司里管理员账号信息
        branch.setSysUserId(sysUser.getId());
        branch.setSysUname(sysUser.getUsername());
        int result = branchMapper.updateByPrimaryKeySelective(branch);
        if (result == 0)
            throw new RuntimeException("添加失败");

        //更新数据权限池的元数据
        sysUserDataRoleService.updateAdminDataRole(DataAuthService.DATA_AUTH_BY_BRANCH_ID, branch);
        return result;
    }


    /**
     * 删除分公司
     *
     * @param id
     * @return
     */
    public int delete(Integer id) {
        synchronized (("branch" + id).intern()) {
            //检查加盟店数量
            Branch branch = branchMapper.selectByPrimaryKey(id);
            if (branch.getLiteCount() > 0)
                throw new CustomException("该分公司加盟店数量大于0,你不能删除它");
            Example example = new Example(Lite.class);
            example.createCriteria().andEqualTo("branchId", id);
            int count = liteMapper.selectCountByExample(example);
            if (count > 0) {
                throw new CustomException("该分公司下有加盟店,你不能删除它");
            }

            if (branch.getSysUserId() == null) {
                return branchMapper.deleteByPrimaryKey(id);
            }
            //检查该分公司的超管下有没有子账号
            example = new Example(SysUser.class);
            example.createCriteria()
                    .andEqualTo("createdUser", branch.getSysUserId())
                    .andEqualTo("userType", SysUser.USER_TYPE_CHILD);

            int childrenCount = sysUserMapper.selectCountByExample(example);
            if (childrenCount > 0) {
                throw new CustomException("该分公司管理账号下有子账号，你不能删除它");
            }
            //删除管理员账号
            sysUserMapper.deleteByPrimaryKey(branch.getSysUserId());
            //删除管理员的数据权限配置
            example = new Example(SysUserDataRole.class);
            example.createCriteria().andEqualTo("sysUserId", branch.getSysUserId());
            sysUserDataRoleMapper.deleteByExample(example);
            sysUserDataRoleService.removeAdminDataRole(DataAuthService.DATA_AUTH_BY_BRANCH_ID, branch.getId());
            //删除分公司
            return branchMapper.deleteByPrimaryKey(id);
        }
    }


    /**
     * 修改
     *
     * @param branch
     * @param sysUser
     * @return
     */
    public int update(Branch branch, SysUser sysUser) {
        branch.setSysUname(null);
        branch.setUpdatedAt(new Date());
        branch.setLiteCount(null);
        //更新名称
        int result = branchMapper.updateByPrimaryKeySelective(branch);
        if (result > 0) {
            //更新密码
            sysUser.setPwd(DigestUtils.md5DigestAsHex(sysUser.getPwd().getBytes()));
            sysUser.setId(branch.getSysUserId());
            sysUserMapper.updateByPrimaryKeySelective(sysUser);

        }
        //更新数据权限元数据
        sysUserDataRoleService.updateAdminDataRole(DataAuthService.DATA_AUTH_BY_BRANCH_ID, branch);
        return result;
    }


    /**
     * 分公司上下线
     *
     * @param branch
     * @return
     */
    public int updateEnabled(Branch branch) {
        if (branch.getId() == null) return 0;
        Example example = new Example(Branch.class);
        example.createCriteria().andEqualTo("id", branch.getId());
        Branch updateBranch = new Branch();
        updateBranch.setIsEnabled(branch.getIsEnabled());
        int result = branchMapper.updateByExampleSelective(updateBranch, example);
        if (result > 0) {
            branch = branchMapper.selectByPrimaryKey(branch.getId());
            SysUser sysUser = new SysUser();
            sysUser.setId(branch.getSysUserId());
            sysUser.setIsEnabled(branch.getIsEnabled());
            sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
        return result;
    }

    /**
     * 加盟店数量自增
     *
     * @return
     */
    public int incrementLiteCount(Integer branchId, Integer count) {
        return branchMapper.updateIncrement(branchId, count);
    }
}
