package com.wmeimob.custom.yhpz.service;

import com.wmeimob.custom.system.bean.SysUser;
import com.wmeimob.custom.yhpz.bean.User;
import com.wmeimob.custom.yhpz.dao.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shinez on 2017/6/29.
 */
@Transactional
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AdminLiteService adminLiteService;

    /**
     * 查询用户信息
     *
     * @param user
     * @return
     */
    public List<User> findAll(User user) {
        Example example = new Example(User.class);
        example.createCriteria()
                .andLike("nickname", "%" + user.getNickname() + "%")
                .andLike("tel", "%" + user.getTel() + "%")
                .andEqualTo("branchId", user.getBranchId())
                .andEqualTo("liteId", user.getLiteId());
        return userMapper.selectByExample(example);
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    public int update(User user) {
        SysUser userDetails = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String branchIdStr = DataAuthService.getBranchId(userDetails);
        String liteIdStr = DataAuthService.getLiteId(userDetails);
        User currentUser = userMapper.selectByPrimaryKey(user.getId());
        Assert.notNull(currentUser, "未找到该用户");

        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("branchId", branchIdStr)
                .andEqualTo("liteId", liteIdStr);
        int result = userMapper.updateByExampleSelective(user, example);
        if (result > 0) {
            if (!user.getLiteId().equals(currentUser.getLiteId())){
                //currentUser->Lite UserCount--
                result= adminLiteService.incrementUserCount(currentUser.getLiteId(),-1);
                //User->Lite UserCount++
                result= adminLiteService.incrementUserCount(user.getLiteId(),1);
            }
        }
        return result;
    }
}
