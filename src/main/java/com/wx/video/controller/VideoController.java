package com.wx.video.controller;

import com.wx.video.enums.VideoStatusEnum;
import com.wx.video.service.VideoService;
import com.wx.video.utils.JsonResult;
import com.wx.video.utils.PagedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description 视频控制器
 */
@Controller
@RequestMapping("management")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 跳转到视频列表页面
     * @return 视频列表页面路径
     */
    @GetMapping("/showVideoList")
    public String showVideoList() {
        return "video/videoList";
    }

    /**
     * 分页查询视频列表
     * @param page 当前页数
     * @return 视频列表
     */
    @PostMapping("/videoList")
    @ResponseBody
    public PagedResult videoList(Integer page) {
        return videoService.queryVideoList(page, 10);
    }

    /**
     * 删除视频
     * @return 视频id
     */
    @PostMapping("/deleteVideo")
    @ResponseBody
    public JsonResult deleteVideo(String videoId) {
        videoService.delVideo(videoId);
        return JsonResult.ok();
    }

    /**
     * 禁播视频
     * @param videoId 视频id
     * @return 禁播视频成功
     */
    @PostMapping("/toggleStatus")
    @ResponseBody
    public JsonResult toggleStatus(String videoId, Integer videoStatus) {
        Integer status = videoStatus == 1 ? VideoStatusEnum.SUCCESS.getValue() : VideoStatusEnum.FORBID.getValue();
        videoService.updateVideoStatus(videoId, status);
        return JsonResult.ok();
    }

    /**
     * 跳转到添加视频页面
     * @return 视频页面路径
     */
    @GetMapping("/showAddVideo")
    public String showAddVideo() {
        return "video/addVideo";
    }

}
