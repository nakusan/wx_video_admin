package com.wx.video.controller;

import com.wx.video.enums.VideoStatusEnum;
import com.wx.video.model.Category;
import com.wx.video.model.Video;
import com.wx.video.model.vo.Result;
import com.wx.video.service.VideoService;
import com.wx.video.utils.PagedResult;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    public Result deleteVideo(String videoId) {
        videoService.delVideo(videoId);
        return Result.ok();
    }

    /**
     * 编辑视频
     * @return 视频id
     */
    @PostMapping("/updateVideo")
    @ResponseBody
    public Result updateVideo(@RequestParam String videoId,
                                  Integer price,
                                  String videoTitle,
                                  String videoCategory,
                                  String videoDesc) {
        Video video = new Video();
        video.setVideoId(videoId);
        video.setPrice(price);
        video.setVideoTitle(videoTitle);
        video.setCategoryId(Integer.parseInt(videoCategory));
        video.setVideoDesc(videoDesc);
        video.setUpdateTime(new Date());
        videoService.updateVideo(video);
        return Result.ok();
    }

    /**
     * 禁播视频
     * @param videoId 视频id
     * @return 禁播视频成功
     */
    @PostMapping("/toggleStatus")
    @ResponseBody
    public Result toggleStatus(String videoId, Integer videoStatus) {
        Integer status = videoStatus == 1 ? VideoStatusEnum.SUCCESS.getValue() : VideoStatusEnum.FORBID.getValue();
        videoService.updateVideoStatus(videoId, status);
        return Result.ok();
    }

    /**
     * 跳转到添加视频页面
     * @return 视频页面路径
     */
    @GetMapping("/showAddVideo")
    public String showAddVideo() {
        return "video/addVideo";
    }

    /**
     * 跳转到视频编辑页面
     * @return 视频编辑页面路径
     */
    @GetMapping("/showUpdateVideo")
    public String showUpdateVideo(@RequestParam String videoId, Model model) {
        Video video = videoService.queryVideoById(videoId);
        model.addAttribute("video", video);
        return "video/updateVideo";
    }

    @GetMapping("/categories")
    @ResponseBody
    public Result getCategories() {
        List<Category> categories = videoService.getAllCategories();
        return Result.ok(categories);
    }

}
