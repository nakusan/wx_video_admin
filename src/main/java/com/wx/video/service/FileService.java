package com.wx.video.service;

import com.wx.video.model.Video;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 输出文件到oss
     * @param file
     * @return
     */
    public Video upload(MultipartFile file) throws Exception;

//    public List<OSSObjectSummary> getObjectList();
//
//    public String getUrl(String objectName ,long effectiveTime);

    /**
     * 删除OSS中的单个文件
     *
     * @param objectName 唯一objectName（在oss中的文件名字）
     */
    public void delete(String objectName);
//    /**
//     * 输出文件到fastdfs
//     * @param fileStr
//     * @return
//     */
//    String uploadToFastDfs(String fileStr);
}