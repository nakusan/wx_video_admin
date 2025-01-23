package com.wx.video.service;

import com.wx.video.model.User;
import com.wx.video.model.vo.UserProfile;
import com.wx.video.utils.PagedResult;

/**
 * @author lyp
 * @description 用户服务接口
 */
public interface UserService {

    /**
     * 查询用户头像昵称等信息
     * @param token
     * @return
     */
    UserProfile getProfile(String token);
    /**
     * 查询用户列表
     * @param user 用户
     * @param page 当前页数
     * @param pageSize 每页条数
     * @return 分页结果
     */
    PagedResult queryUserList(User user, Integer page, Integer pageSize);

    /**
     * 根据openid查找用户
     *
     * @param openid 用户的唯一标识符
     * @return 返回找到的用户对象，如果找不到则返回null
     */
    User findByOpenid(String openid);

    /**
     * 保存用户信息的方法
     *
     * @param user 要保存的用户对象，包含用户的各种信息
     * @return boolean 表示用户信息是否保存成功
     */
    boolean saveUser(User user);

    /**
     * 更新用户信息的方法
     *
     * @param user 要保存的用户对象，包含用户的各种信息
     * @return boolean 表示用户信息是否保存成功
     */
    boolean updateUser(User user);

    boolean updateUserByOpenId(User user);

}
