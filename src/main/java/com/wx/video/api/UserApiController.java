package com.wx.video.api;

import com.wx.video.model.User;
import com.wx.video.model.vo.Result;
import com.wx.video.model.vo.UserProfile;
import com.wx.video.service.JwtService;
import com.wx.video.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.wx.video.utils.Constrants.BEARER_PREFIX;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/profile")
    @ResponseBody
    public Result saveProfile(@RequestHeader("Authorization") String authorizationHeader,
                              @RequestBody UserProfile userProfile) {
        String token = authorizationHeader.substring(BEARER_PREFIX.length());
        String openId = jwtService.getOpenidFromToken(token);
        User user = new User();
        user.setOpenid(openId);
        user.setNickname(userProfile.getNickName());
        user.setFaceImage(userProfile.getAvatarUrl());
        userService.updateUserByOpenId(user);
        return Result.ok();
    }
}
