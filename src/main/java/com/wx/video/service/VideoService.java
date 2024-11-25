package com.wx.video.service;

import com.wx.video.model.Category;
import com.wx.video.model.Video;
import com.wx.video.model.vo.VideoQueryVO;
import com.wx.video.utils.PagedResult;

import java.util.List;

/**
 * @description 视频服务
 */
public interface VideoService {
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
     * @param queryVO 查询条件
     * @param page 当前页数
     * @param pageSize 每页条数
     * @return 分页结果
     */
    PagedResult queryVideoList(VideoQueryVO queryVO, Integer page, Integer pageSize);

    /**
     * 删除视频
     * @param videoId 视频id
     */
    void delVideo(String videoId);

    /**
     * 编辑视频
     * @param video 视频信息
     */
    void updateVideo(Video video);

    /**
     * 根据视频id查询视频信息
     * @param videoId 视频id
     * @return 视频信息
     */
    Video queryVideoById(String videoId);

    List<Category> getAllCategories();
}
