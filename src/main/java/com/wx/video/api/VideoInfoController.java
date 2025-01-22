package com.wx.video.api;

import com.wx.video.model.UserVisitLog;
import com.wx.video.model.vo.Result;
import com.wx.video.service.UserVisitLogService;
import com.wx.video.service.VideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin //开放前端的跨域访问
@RestController
@RequestMapping("/api/video-info")
public class VideoInfoController {

    @Resource
    private VideoService videoService;
    @Resource
    private UserVisitLogService userVisitLogService;

    @GetMapping("/getUserVisitInfo")
    public Result getUserVisitInfo(@RequestParam String openId){

        List<UserVisitLog> list = userVisitLogService.getUserVisitLogByUserId(openId);
        return Result.build(200, "success", list);
    }

    @PostMapping("/saveUserVisitLog")
    public Result saveUserVisitLog(@RequestBody UserVisitLog userVisitLog) {
        userVisitLogService.saveUserVisitLog(userVisitLog);
        return Result.ok();
    }

    @GetMapping("/queryVideoInfoList")
    public Result queryVideoInfoList() {
        return Result.ok();
    }


}
