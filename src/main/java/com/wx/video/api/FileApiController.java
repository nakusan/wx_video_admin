package com.wx.video.api;

import com.wx.video.model.FileInfo;
import com.wx.video.model.vo.Result;
import com.wx.video.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api/file")
public class FileApiController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(
//            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            FileInfo fileInfo = fileService.uploadFile(file);
            return Result.ok(fileInfo);
        } catch (Exception e) {
            return Result.errorMsg(e.getMessage());
        }

    }
}
