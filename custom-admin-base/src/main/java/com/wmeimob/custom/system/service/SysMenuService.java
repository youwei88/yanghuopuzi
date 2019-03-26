package com.wmeimob.custom.system.service;

import com.wmeimob.custom.system.bean.*;
import com.wmeimob.custom.system.dao.SysMenuMapper;
import com.wmeimob.custom.system.dao.SysMenuRoleMapper;
import com.wmeimob.custom.system.dao.SysRoleMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Shinez on 2017/6/15.
 */
@Service
@Transactional
public class SysMenuService {

    @Resource
    private SysMenuRoleMapper sysMenuRoleMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleMapper sysRolemapper;


    /**
     * 更新角色菜单
     *
     * @param nodes
     * @param roleId
     * @return
     */
    public int updateRoleMenus(ZTreeNode[] nodes, Integer roleId) {

        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isRoot = sysUserService.isRootAccount(sysUser.getUsername());
        List<SysMenuRole> superRoleMenuConfigList = new ArrayList<>();
        Integer myRoleId = sysUserService.getUserRoleId(sysUser.getUsername());

        List<SysRole> childrenRoles = null;
        List<Integer> childrenRoleIds = new ArrayList<>();
        if (!isRoot) {
            //当前用户是超管
            Assert.notNull(myRoleId, "无法获取当前登陆用户的角色");
            SysMenuRole sysMenuRole = new SysMenuRole();
            sysMenuRole.setRoleId(myRoleId);
            superRoleMenuConfigList = sysMenuRoleMapper.select(sysMenuRole);//查询超管的菜单

        }else{
            Example childrenRolesExample = new Example(SysRole.class);
            childrenRolesExample.selectProperties("id");
            childrenRolesExample.createCriteria().andEqualTo("createdRole", roleId);
            childrenRoles = sysRolemapper.selectByExample(childrenRolesExample);
            childrenRoles.forEach(cr -> childrenRoleIds.add(cr.getId()));
        }
        List<SysMenuRole> addNodes = new ArrayList<>();
        int i = 0;
        ZTreeNode zTreeNode;
        SysMenuRole sysFnRole;

        Example delExample= new Example(SysMenuRole.class,true,true);
        Example delChildrenExample= new Example(SysMenuRole.class,true,true);
        while (i < nodes.length) {
            zTreeNode = nodes[i];
            sysFnRole = new SysMenuRole();
            sysFnRole.setMenuId(zTreeNode.getId());
            sysFnRole.setRoleId(roleId);
            if (!isRoot) {
                //检测超管在配置子账号菜单时有没有超出自己所管辖的菜单范围
                Assert.isTrue(contailsMyMenu(superRoleMenuConfigList, zTreeNode.getId()), "菜单信息有误");
            }
            if (zTreeNode.isChecked()) {
                addNodes.add(sysFnRole);
            } else {
                //删除
                delExample.or().andEqualTo("menuId", sysFnRole.getMenuId()).andEqualTo("roleId", sysFnRole.getRoleId());
                if (isRoot) {
                    if (childrenRoleIds.size() > 0) {
                        delChildrenExample.or().andEqualTo("menuId", sysFnRole.getMenuId()).andIn("roleId", childrenRoleIds);
                    }
                }
            }
            ++i;
        }
        int addCount = 0;
        int delCount=0;
        if (addNodes.size() > 0)
            addCount = sysMenuRoleMapper.insertList(addNodes);

        if (delExample.getOredCriteria().size()>0) {
            delCount+=sysMenuRoleMapper.deleteByExample(delExample);
        }
        if(delChildrenExample.getOredCriteria().size()>0) {
            delCount+=sysMenuRoleMapper.deleteByExample(delChildrenExample);
        }
        return addCount + delCount;
    }

    /**
     * 判断一个菜单id是否在一个集合里面
     *
     * @param myMenuRole
     * @param id
     * @return
     */
    private boolean contailsMyMenu(List<SysMenuRole> myMenuRole, Integer id) {
        for (SysMenuRole sysMenuRole : myMenuRole) {
            if (sysMenuRole.getMenuId().equals(id))
                return true;
        }
        return false;
    }

    /**
     * 查询菜单for
     *
     * @param roleId
     * @return
     */
    public List<ZTreeNode> findMenus(Integer roleId) {
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer typeId = sysUserService.getCanProcessRoleType(userDetails);
        List<SysMenu> sysMenuAll;
        if (!SysRole.ROLE_TYPE_SUPER.equals(typeId)) {
            //配置子账号的菜单，只显示当前超管角色能管理的全部菜单
            Integer myRoleId = sysUserService.getUserRoleId(userDetails.getUsername());
            sysMenuAll = sysMenuMapper.selectChildrenMenusSimple(myRoleId, roleId);
        } else {
            //查询系统的全部菜单
            sysMenuAll = sysMenuMapper.selectMenusSimple(roleId);
        }
        Iterator<SysMenu> iterator = sysMenuAll.iterator();
        List<ZTreeNode> nodes = new ArrayList<>();
        while (iterator.hasNext()) {
            SysMenu sysFn = iterator.next();
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(sysFn.getId());
            zTreeNode.setPId(sysFn.getParentId());
            zTreeNode.setName(sysFn.getMenuName());
            zTreeNode.setChecked(!StringUtils.isEmpty(sysFn.getRoleId()));
            zTreeNode.setOpen(true);
            zTreeNode.setChkDisabled(false);
            nodes.add(zTreeNode);
        }
        return nodes;
    }
}
