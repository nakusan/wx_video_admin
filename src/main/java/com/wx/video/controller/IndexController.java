package com.wx.video.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description 首页控制器
 */
@Controller
@RequestMapping("management")
public class IndexController {

    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("name", "andy");
        // 跳转到控制中心页
        return "index";
    }

}
