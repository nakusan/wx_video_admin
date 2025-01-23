package com.wx.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.video.mapper.UserMapper;
import com.wx.video.model.User;
import com.wx.video.model.vo.UserProfile;
import com.wx.video.service.JwtService;
import com.wx.video.service.UserService;
import com.wx.video.utils.PagedResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author lyp
 */
@Slf4j
@Service
@CacheConfig(cacheNames = {"UserServiceImpl"})
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserProfile getProfile(String token) {
        String openid = jwtService.getOpenidFromToken(token);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            return UserProfile.instanceUserProfile(user.getFaceImage(), user.getNickname());
        }
        return UserProfile.instanceEmptyProfile();
    }


    /**
     * 查询用户列表
     *
     * @param user 用户对象，用于构建查询条件
     * @param page 当前页数
     * @param pageSize 每页显示的记录数
     * @return 包含用户列表的分页结果对象
     */
    @Override
//    @Cacheable(key = "methodName + #p0.openid + #p0.nickname + #p1 + #p2")
    public PagedResult queryUserList(User user, Integer page, Integer pageSize) {
        // 初始化昵称变量
        String nickname = null;
        // 如果用户对象不为空，获取用户的昵称
        if (user != null) {
            nickname = user.getNickname();
        }

        // MyBatis Plus 分页插件，直接使用 Page 类创建分页对象
        Page<User> pageObj = new Page<>(page, pageSize);
        // 创建查询条件封装器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 如果昵称不为空且不空白，则将昵称包含到查询条件中
        if (StringUtils.isNotBlank(nickname)) {
            queryWrapper.like("nickname", "%" + nickname + "%");
        }

        // 使用MyBatis Plus根据条件查询用户分页数据
        IPage<User> userIPage = userMapper.selectPage(pageObj, queryWrapper);

        // 创建并填充自定义的分页结果对象
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRecords(userIPage.getTotal());  // 设置总记录数
        pagedResult.setRows(userIPage.getRecords());   // 设置每行的数据
        pagedResult.setTotal(userIPage.getPages());    // 设置总页数
        pagedResult.setPage(page);                     // 设置当前页数
        return pagedResult;                            // 返回分页结果
    }


    /**
     * 根据openid查询用户信息
     *
     * 此方法首先验证传入的openid是否为空或者仅包含空格，
     * 最后构建查询条件并调用数据库操作查询用户信息
     *
     * @param openid 用户的唯一标识符，用于查询用户信息
     * @return 查询到的用户信息，如果找不到或出现错误，则返回null
     * @throws IllegalArgumentException 如果传入的openid为空或仅包含空格
     */
    @Override
    public User findByOpenid(String openid) {
        // 验证输入是否为空
        if (openid == null || openid.trim().isEmpty()) {
            throw new IllegalArgumentException("Openid cannot be null or empty");
        }
        // 构建查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        try {
            // 执行查询
            return userMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            // 处理数据库操作可能出现的异常
            log.error("Error occurred while querying user by openid: " + e.getMessage());
            return null; // 或者抛出自定义异常，具体取决于业务需求
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(User user) {
        try {
            return userMapper.insert(user) > 0;
        } catch (Exception e) {
            // 记录异常日志
            log.error("Error saving user: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        try {
            return userMapper.updateById(user) > 0;
        } catch (Exception e) {
            log.error("Error updating user: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserByOpenId(User user) {
        try {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("openid", user.getOpenid())
                    .set("face_image", user.getFaceImage())
                    .set("nickname", user.getNickname());
            return userMapper.update(user, updateWrapper) > 0;
        } catch (Exception e) {
            log.error("Error updating user: " + e.getMessage(), e);
            throw e;
        }
    }

}
