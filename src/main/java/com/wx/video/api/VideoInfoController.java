package com.wx.video.api;

import com.wx.video.model.UserVisitLog;
import com.wx.video.model.vo.Result;
import com.wx.video.model.vo.UserVisitLogVO;
import com.wx.video.model.vo.VideoQueryVO;
import com.wx.video.model.vo.VideoVO;
import com.wx.video.service.JwtService;
import com.wx.video.service.UserVisitLogService;
import com.wx.video.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.wx.video.utils.Constrants.BEARER_PREFIX;

@CrossOrigin //开放前端的跨域访问
@RestController
@Slf4j
@RequestMapping("/api/video-info")
public class VideoInfoController {

    @Resource
    private VideoService videoService;
    @Resource
    private UserVisitLogService userVisitLogService;
    @Resource
    private JwtService jwtService;

    @GetMapping("/getUserVisitInfo")
    public Result getUserVisitInfo(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(BEARER_PREFIX.length());
        String openId = jwtService.getOpenidFromToken(token);
        List<UserVisitLogVO> list = userVisitLogService.getUserVisitLogByUserId(openId);
        return Result.ok(list);
    }

    @PostMapping("/saveUserVisitLog")
    public Result saveUserVisitLog(@RequestHeader("Authorization") String authorizationHeader,
                                   @RequestBody String videoId) {
        try {
            String token = authorizationHeader.substring(BEARER_PREFIX.length());
            String openId = jwtService.getOpenidFromToken(token);
            userVisitLogService.saveUserVisitLog(openId, videoId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.errorMsg(e.getMessage());
        }
        return Result.ok("数据更新成功");
    }

    @GetMapping("/queryVideoInfoList/{page}")
    public Result queryVideoInfoList(VideoQueryVO queryVO, @PathVariable Integer page) {
        List<VideoVO> videoInfos = videoService.queryVideoListByApi(queryVO, page, 10);
        return Result.ok(videoInfos);
    }


}
