package com.wx.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.video.model.UserVisitLog;
import com.wx.video.model.vo.UserVisitLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserVisitLogMapper extends BaseMapper<UserVisitLog> {

    List<UserVisitLogVO> getUserVisitLogByUserId(@Param("openid") String openid);
}
