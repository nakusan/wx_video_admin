package com.wx.video.controller;


import com.wx.video.model.Video;
import com.wx.video.service.FileService;
import com.wx.video.service.VideoService;
import com.wx.video.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
//@CrossOrigin
@RequestMapping("management")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private VideoService videoService;

    /**
     * 文件上传
     *
     * @param file
     */
    @PostMapping("/upload")
    @ResponseBody
    public JsonResult upload(
//            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("filename") String filename,
            @RequestParam("file") MultipartFile file) {
        try {
            Video video = fileService.upload(file);

            video.setVideoName(filename); // 视频名
            // 添加视频到数据库
            videoService.addVideo(video);
        } catch (Exception e) {
            return JsonResult.errorMsg(e.getMessage());
        }
        //返回r对象
        return JsonResult.ok();
    }

}
