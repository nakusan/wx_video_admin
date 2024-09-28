package com.wx.video.controller;

import com.wx.video.model.User;
import com.wx.video.service.UserService;
import com.wx.video.utils.PagedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 用户控制器
 */
@Controller
@RequestMapping("management")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到用户列表页
     * @return 用户列表页路径
     */
    @GetMapping("/showList")
    public String showList() {
        return "user/userList";
    }

    /**
     * 根据条件查询用户列表
     * @param user 用户信息
     * @param page 当前页数
     * @return 用户列表分页结果
     */
    @PostMapping("/userList")
    @ResponseBody
    public PagedResult list(User user, Integer page) {
        return userService.queryUserList(user, (page == null ? 1 : page), 10);
    }

}
