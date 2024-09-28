package com.wx.video.service;

import com.wx.video.model.Video;
import com.wx.video.utils.PagedResult;

/**
 * @description 视频服务
 */
public interface VideoService {
//    /**
//     * 添加背景乐
//     * @param bgm 背景乐
//     */
//    void addBgm(Bgm bgm);

//    /**
//     * 分页查询背景乐列表
//     * @param page 当前页数
//     * @param pageSize 每页条数
//     * @return 分页结果
//     */
//    PagedResult queryBgmList(Integer page, Integer pageSize);

//    /**
//     * 删除背景乐
//     * @param bgmId 背景乐id
//     */
//    void delBgm(String bgmId);

//    /**
//     * 分页查询举报视频列表
//     * @param page 当前页数
//     * @param pageSize 每页条数
//     * @return 分页结果
//     */
//    PagedResult queryReportList(Integer page, Integer pageSize);

    /**
     * 添加视频
     * @param video 视频
     */
    void addVideo(Video video);

    /**
     * 更新视频状态
     * @param videoId 视频id
     * @param status 视频状态
     */
    void updateVideoStatus(String videoId, Integer status);

    /**
     * 分页查询视频列表
     * @param page 当前页数
     * @param pageSize 每页条数
     * @return 分页结果
     */
    PagedResult queryVideoList(Integer page, Integer pageSize);

    /**
     * 删除视频
     * @param videoId 视频id
     */
    void delVideo(String videoId);
}
