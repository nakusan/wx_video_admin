package com.wx.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.video.model.Video;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

//    /**
//     * 获取所有视频列表
//     * @return 视频列表
//     */
//    List<Video> selectAllVideo();
}