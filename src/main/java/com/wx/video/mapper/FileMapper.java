package com.wx.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.video.model.FileInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<FileInfo> {

}