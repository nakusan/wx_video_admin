package com.wx.video.service;

public interface JwtService {

    /**
     * 生成认证令牌
     *
     * @param openid 用户的唯一标识
     * @return 生成的认证令牌
     */
    String generateToken(String openid);

    /**
     * 从认证令牌中提取用户的唯一标识
     *
     * @param token 认证令牌
     * @return 用户的唯一标识
     */
    String getOpenidFromToken(String token);

    /**
     * 检查认证令牌是否已过期
     *
     * @param token 认证令牌
     * @return 如果令牌已过期返回true，否则返回false
     */
    boolean isTokenExpired(String token);

}

