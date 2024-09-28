package com.wx.video.service.impl;

import com.wx.video.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000 * 3; // 3 天（单位为毫秒）

    @Value("${wx.appSecret}")
    String appSecret;

    // 生成 JWT
    @Override
    public String generateToken(String openid) {
        // 创建存储JWT载荷的映射
        Map<String, Object> claims = new HashMap<>();
        // 将用户openid存入JWT的载荷中
        claims.put("openid", openid);

        // 使用Jwts.builder构建JWT
        // 设置载荷、签发时间、过期时间和签名算法及密钥
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, appSecret)
                // 将构建好的JWT紧凑化后返回
                .compact();
    }

    @Override
    public String getOpenidFromToken(String token) {
        // 解析JWT令牌，验证其完整性和有效性
        Claims claims = Jwts.parser()
                .setSigningKey(appSecret)  // 设置用于验证签名的密钥
                .parseClaimsJws(token)      // 解析令牌并验证其签名
                .getBody();                 // 获取令牌主体部分的声明
        // 从声明中获取名为"openid"的值，并指定其类型为字符串
        return claims.get("openid", String.class);
    }

    // 检查 token 是否过期
    @Override
    public boolean isTokenExpired(String token) {
        // 解析 token 并获取其主体部分
        Claims claims = Jwts.parser()
                .setSigningKey(appSecret)
                .parseClaimsJws(token)
                .getBody();
        // 比较 token 的过期时间与当前时间，判断 token 是否过期
        return claims.getExpiration().before(new Date());
    }


}
