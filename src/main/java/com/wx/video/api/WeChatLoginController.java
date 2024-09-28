package com.wx.video.api;

import com.wx.video.model.User;
import com.wx.video.service.JwtService;
import com.wx.video.service.WeChatLoginService;
import com.wx.video.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class WeChatLoginController {

    @Autowired
    private WeChatLoginService weChatLoginService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public JsonResult login(@RequestParam String code) {
        // 调用微信服务获取 openid 和 session_key
        Map<String, String> wxData = null;
        try {
            wxData = weChatLoginService.getWxSession(code);
        } catch (Exception e) {
            log.error("Failed to get wx session: " + e.getMessage(), e);
            return JsonResult.errorMsg("Failed to get wx session");
        }

        String openid = wxData.get("openid");
        String sessionKey = wxData.get("session_key");
        // 根据微信返回的信息处理用户登录
        User user = weChatLoginService.login(openid, sessionKey);
        // 生成 JWT
        String token = jwtService.generateToken(user.getOpenid());

        // 返回给前端 JWT 和用户信息
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("openid", user.getOpenid());
        return JsonResult.ok(response);
    }
}
