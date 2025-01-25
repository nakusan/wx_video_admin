package com.wx.video.api;

import com.wx.video.model.UserVisitLog;
import com.wx.video.model.vo.Result;
import com.wx.video.model.vo.UserVisitLogVO;
import com.wx.video.model.vo.VideoQueryVO;
import com.wx.video.model.vo.VideoVO;
import com.wx.video.service.UserVisitLogService;
import com.wx.video.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin //开放前端的跨域访问
@RestController
@Slf4j
@RequestMapping("/api/video-info")
public class VideoInfoController {

    @Resource
    private VideoService videoService;
    @Resource
    private UserVisitLogService userVisitLogService;

    @GetMapping("/getUserVisitInfo")
    public Result getUserVisitInfo(@RequestParam String openId){
        List<UserVisitLogVO> list = userVisitLogService.getUserVisitLogByUserId(openId);
        return Result.ok(list);
    }

    @PostMapping("/saveUserVisitLog")
    public Result saveUserVisitLog(@RequestBody UserVisitLogVO userVisitLog) {
        try {
            userVisitLogService.saveUserVisitLog(userVisitLog);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.errorMsg(e.getMessage());
        }
        return Result.ok("数据更新成功");
    }

    @GetMapping("/queryVideoInfoList")
    public Result queryVideoInfoList(VideoQueryVO queryVO, Integer page) {
        List<VideoVO> videoInfos = videoService.queryVideoListByApi(queryVO, page, 10);
        return Result.ok(videoInfos);
    }


}
