package com.wx.video.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:wx.properties")
@ConfigurationProperties(prefix="wx")
@Data
@Slf4j
public class WxConfig {

    // 微信小程序ID
    private String appId;
    // 微信小程序密钥
    private String appSecret;
    // 微信小程序登录地址
    private String wxUrl;
}
