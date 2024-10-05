package com.wx.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.video.model.Video;
import com.wx.video.model.vo.VideoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    List<VideoVO> queryAllVideos(@Param("isDelete") Integer isDelete, @Param("offset") int offset, @Param("limit") int limit);

    int countAllVideos(@Param("isDelete") Integer isDelete);
}