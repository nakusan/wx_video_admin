package com.wx.video.service;

import com.wx.video.model.User;

import java.util.Map;

/**
 * 微信登录服务接口
 * 该接口定义了通过微信授权码进行登录的方法
 */
public interface WeChatLoginService {

    /**
     * 使用微信授权码进行登录
     * 通过此方法，可以获取用户的基本信息
     *
     * @param code 微信授权码，用于换取用户信息
     * @return 包含用户信息的Map，其中包含用户标识等信息
     * @throws Exception 如果登录失败，可能抛出异常
     */
    Map<String, String> getWxSession(String code) throws Exception ;

    /**
     * 用户登录方法
     * 该方法通过 openid 和 sessionKey 来认证用户
     * 如果用户不存在，则创建新用户；如果用户存在，则更新 sessionKey
     *
     * @param openid 用户的唯一标识
     * @param sessionKey 当前会话的密钥
     * @return 经过验证或创建的用户对象
     */
    User login(String openid, String sessionKey);
}

