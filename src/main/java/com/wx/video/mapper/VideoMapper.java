package com.wx.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.video.model.Video;
import com.wx.video.model.vo.VideoQueryVO;
import com.wx.video.model.vo.VideoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    List<VideoVO> queryAllVideos(@Param("query") VideoQueryVO query, @Param("offset") int offset, @Param("limit") int limit);

    int countAllVideos();
}