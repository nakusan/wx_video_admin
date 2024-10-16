package com.wx.video.service.impl;

import com.wx.video.config.WxConfig;
import com.wx.video.mapper.UserMapper;
import com.wx.video.model.User;
import com.wx.video.service.UserService;
import com.wx.video.service.WeChatLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WeChatLoginServiceImpl implements WeChatLoginService {

    @Resource
    private WxConfig wxConfig;

    @Resource
    private UserService userService;

    /**
     * 用户登录方法，通过微信小程序码换取用户信息
     *
     * @param code 用户授权后获取的临时登录凭证
     * @return 包含用户信息的Map，包括session_key和openid等
     * @throws Exception 当请求微信API失败时抛出异常
     */
    @Override
    public Map<String, String> getWxSession(String code) throws Exception {
        try {
            // 构造请求微信API的URL，包含小程序的appid、appsecret以及用户的临时登录凭证
            String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                    wxConfig.getWxUrl(), wxConfig.getAppId(), wxConfig.getAppSecret(), code);

            // 使用RestTemplate发送HTTP GET请求到微信API
            RestTemplate restTemplate = new RestTemplate();
            // 发送请求并接收响应，响应为一个Map对象
            Map<String, String> response = restTemplate.getForObject(url, HashMap.class);
            // 记录成功获取到的用户信息
            log.info("Successfully fetched user info: {}", response);
            return response;
        } catch (Exception e) {
            // 处理网络请求失败的情况
            log.error("Failed to fetch user info from WeChat API", e);
            // 将异常向上抛出，由调用者处理
            throw e;
        }

    }

    /**
     * 用户登录方法
     * 该方法通过 openid 和 sessionKey 来认证用户
     * 如果用户不存在，则创建新用户；如果用户存在，则更新 sessionKey
     *
     * @param openid 用户的唯一标识
     * @param sessionKey 当前会话的密钥
     * @return 经过验证或创建的用户对象
     */
    @Override
    public User login(String openid, String sessionKey) {
        // 查找用户
        User user = userService.findByOpenid(openid);
        if (user == null) {
            // 如果用户不存在，创建新用户
            user = new User();
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            userService.saveUser(user);
        } else {
            // 如果用户已存在，更新 session_key
            user.setSessionKey(sessionKey);
            userService.updateUser(user);
        }
        return user;
    }

}
