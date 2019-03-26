package com.wmeimob.custom.system.service;

import com.wmeimob.custom.exception.CustomException;
import com.wmeimob.custom.system.bean.*;
import com.wmeimob.custom.system.dao.*;
import com.wmeimob.tool.SpringRedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Shinez on 2017/5/19.
 */
@Service
@Transactional
public class SysUserService {


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SimpleConfigMapper simpleConfigMapper;

    @Resource
    private SysUserDataRoleMapper sysUserDataRoleMapper;

    @Resource
    private SysUserDataRoleService sysUserDataRoleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据用户名获取用户角色集合
     *
     * @param username
     * @return
     */
    public List<SysRole> getUserRole(String username) {
        return SpringRedisUtil.get("userRoles:" + username);
    }

    /**
     * 更新用户角色
     *
     * @param username
     * @param newRoleList
     */
    public void updateUserRoleMap(String username, List<SysRole> newRoleList) {
        SpringRedisUtil.save("userRoles:" + username, newRoleList, null);
    }

    /**
     * 移除用户角色
     *
     * @param username
     */
    public void removeUserRoleMaps(String username) {
        SpringRedisUtil.delete("userRoles:" + username);
    }


    /**
     * 根据用户名获取用户数据权限集合
     *
     * @param username
     * @return
     */
    public List<SysUserDataRole> getDataRole(String username) {
        return SpringRedisUtil.get("userDataRoles:" + username);
    }


    /**
     * 更新用户数据权限
     *
     * @param username
     * @param newDataRoleList
     */
    public void updateUserDataRoleMaps(String username, List<SysUserDataRole> newDataRoleList) {
        SpringRedisUtil.save("userDataRoles:" + username, newDataRoleList, null);
    }

    /**
     * 移除用户数据权限
     *
     * @param username
     */
    public void removeUserDataRoleMaps(String username) {
        SpringRedisUtil.delete("userDataRoles:" + username);
    }

    /**
     * 获取角色id（目前系统仅支持自定义单角色）
     *
     * @param username
     * @return
     */
    public Integer getUserRoleId(String username) {
        List<SysRole> roles = getUserRole(username);
        Integer roleId = -1;
        for (SysRole role : roles) {
            if (role.getId() != null) {
                roleId = role.getId();
                break;
            }
        }
        return roleId;
    }

    /**
     * 获取当前登陆的用户
     * @return
     */
    public SysUser getPrincipal(){
        return  (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取当前登陆用户所能操作的角色类型
     *
     * @return
     */
    public Integer getCanProcessRoleType() {
        SysUser userDetails =getPrincipal();
        return getCanProcessRoleType(userDetails);
    }

    /**
     * 根据登陆用户获取所能操作的角色类型
     *
     * @param userDetails
     * @return
     */
    public Integer getCanProcessRoleType(SysUser userDetails) {
        return isRootAccount(userDetails.getUsername()) ? SysRole.ROLE_TYPE_SUPER : SysRole.ROLE_TYPE_NORMAL;
    }


    private void throwErrorAuthentication() {
        throw new CustomException("用户名或密码错误");
    }

    private void throwErrorAuthentication(String reason) {
        throw new CustomException(reason);
    }

    private String generateSecurityRoleCode(String roleCode) {
        return "ROLE_" + roleCode;
    }

    /**
     * 判断是否是root账户
     *
     * @param username
     * @return
     */
    public boolean isRootAccount(String username) {
        return "system".equals(username);
    }

    /**
     * 判断是否是超管
     *
     * @param userType
     * @return
     */
    public boolean isSuper(Integer userType) {
        return userType.equals(SysUser.USER_TYPE_SUPER);
    }


    /**
     * 构建system账号
     *
     * @param pwd
     * @return
     */
    private SysUser createSystemUser(String pwd) {
        SimpleConfig simpleConfig = new SimpleConfig();
        simpleConfig.setConfigType(-1);
        simpleConfig = simpleConfigMapper.selectOne(simpleConfig);
        if (!pwd.equals(simpleConfig.getConfigValue()))
            throwErrorAuthentication();
        List<SysRole> roles = new ArrayList<>();
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode(generateSecurityRoleCode("ROOT"));
        roles.add(sysRole);

        sysRole=new SysRole();
        sysRole.setRoleCode(generateSecurityRoleCode("ACTUATOR"));//服务监控角色
        roles.add(sysRole);

        SysUser sysUser = new SysUser();
        sysUser.setUsername("system");
        sysUser.setUserType(SysUser.USER_TYPE_ROOT);
        sysUser.setNickname("系统管理员");
        sysUser.setIsLocked(false);
        sysUser.setIsEnabled(true);
        sysUser.setRoles(roles);
        return sysUser;
    }

    /**
     * 构建管理员
     *
     * @param sysUser
     * @return
     */
    private SysUser createAdmin(SysUser sysUser) {
        List<SysRole> roles = new ArrayList<>();
        if (SysUser.USER_TYPE_SUPER.equals(sysUser.getUserType())) {
            //创建超管
            SysRole sysRole = new SysRole();
            sysRole.setRoleCode(generateSecurityRoleCode("SUPER"));
            roles.add(sysRole);
        }
        SysRole dbSysRole = sysRoleMapper.selectByPrimaryKey(sysUser.getRoleId());
        dbSysRole.setRoleCode(generateSecurityRoleCode(dbSysRole.getRoleCode()));
        roles.add(dbSysRole);

        sysUser.setRoles(roles);
        sysUser.setPwd("");
        if (sysUser.getEnableDataRole()) {
            //数据权限
            SysUserDataRole sysUserDataRole = new SysUserDataRole();
            sysUserDataRole.setSysUserId(sysUser.getId());
            List<SysUserDataRole> sysUserDataRoles = sysUserDataRoleMapper.select(sysUserDataRole);
            sysUser.setDataRoles(sysUserDataRoles);

        }
        return sysUser;
    }

    /**
     * 登陆
     *
     * @param user
     * @return
     */
    public SysUser login(SysUser user) {
        String username = user.getUsername();
        String pwd = user.getPwd();
        Assert.hasText(username, "用户名不能为空");
        Assert.hasLength(pwd, "密码不能为空");
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (isRootAccount(username)) {
            return createSystemUser(pwd);
        }
        SysUser queryLogin = new SysUser();
        queryLogin.setUsername(username);
        queryLogin.setPwd(pwd);
        queryLogin = sysUserMapper.selectOne(queryLogin);
        if (queryLogin == null) {
            throw new CustomException("用户名或密码错误");
        }
        if (queryLogin.getIsLocked() || !queryLogin.getIsEnabled()) {
            throwErrorAuthentication("用户被锁定或被禁用");
        }
        return createAdmin(queryLogin);
    }


    /**
     * 保存账号
     *
     * @param user
     * @param sysUserDataRole
     * @return
     */
    public int save(SysUser user, SysUserDataRole sysUserDataRole) {

        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isRoot = isRootAccount(userDetails.getUsername());
        if (!isRoot) {//当前是超管
            user.setCreatedUser(userDetails.getId());
            user.setUserType(SysUser.USER_TYPE_CHILD);
        } else {
            user.setUserType(SysUser.USER_TYPE_SUPER);
        }
        int result;
        if (StringUtils.isEmpty(user.getId())) {
            //新增
            user.setCreatedAt(new Date());
            user.setPwd(DigestUtils.md5DigestAsHex(user.getPwd().getBytes()));
            try {
                sysUserMapper.insertSelective(user);
                result = user.getId();
            } catch (DuplicateKeyException e) {
                throw new CustomException("您不能使用这个用户名");
            }
        } else {
            user.setUsername(null);
            user.setUserType(null);
            //修改
            Example example = new Example(SysUser.class);
            example.createCriteria()
                    .andEqualTo("createdUser", user.getCreatedUser())
                    .andEqualTo("userType", user.getUserType())
                    .andEqualTo("id", user.getId());
            if (sysUserDataRole.getDataRoles() == null)
                user.setEnableDataRole(false);
            result = sysUserMapper.updateByExampleSelective(user, example);
        }

        if (result > 0) {
            SysUserDataRole delCondition = new SysUserDataRole();
            delCondition.setSysUserId(user.getId());
            sysUserDataRoleMapper.delete(delCondition);
            delCondition=new SysUserDataRole();
            delCondition.setExtendsUser(user.getId());
            sysUserDataRoleMapper.delete(delCondition);

            if (user.getEnableDataRole() && sysUserDataRole.getDataRoles() != null) {
                boolean validDataConfigStatus = false;
                SysUserDataRole sysInitDataRole;
                Map<String, SysUserDataRole> sysUserDataRoleMap = null;
                List<Integer> childrenIds = new ArrayList<>();
                if (isRoot) {
                    sysUserDataRoleMap = sysUserDataRoleService.getAdminDataRole();
                    //获取当前编辑的超管的子账号id
                    Example example=new Example(SysUser.class);
                    example.selectProperties("id");
                    example.createCriteria().andEqualTo("createdUser",user.getId());
                    List<SysUser> children=sysUserMapper.selectByExample(example);
                    children.forEach(s->childrenIds.add(s.getId()));

                } else {
                    sysUserDataRoleMap = sysUserDataRoleService.getChildrenDataRole();
                }
                for (SysUserDataRole dataRole : sysUserDataRole.getDataRoles()) {
                    if (!StringUtils.isEmpty(dataRole.getColumnValue()) && !StringUtils.isEmpty(dataRole.getDataRoleCode())) {
                        sysInitDataRole = sysUserDataRoleMap != null ? sysUserDataRoleMap.get(dataRole.getDataRoleCode()) : null;
                        if (sysInitDataRole == null) break;
                        dataRole.setSysUserId(user.getId());
                        dataRole.setTableName(sysInitDataRole.getTableName());
                        dataRole.setColumnName(sysInitDataRole.getColumnName());
                        sysUserDataRoleMapper.insertIgnore(dataRole);
                        //添加子账号相同的数据权限
                        for (Integer childrenId : childrenIds) {
                            dataRole.setSysUserId(childrenId);
                            dataRole.setExtendsUser(user.getId());
                            sysUserDataRoleMapper.insertIgnore(dataRole);
                        }

                        validDataConfigStatus = true;
                    }
                }
                if (!validDataConfigStatus) {
                    user.setEnableDataRole(false);
                    sysUserMapper.updateByPrimaryKeySelective(user);
                    return 1;
                }
            }
        }
        return result;
    }
}
