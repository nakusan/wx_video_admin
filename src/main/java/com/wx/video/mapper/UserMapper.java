package com.wx.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.video.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}