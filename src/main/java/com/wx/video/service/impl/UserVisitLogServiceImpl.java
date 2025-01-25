package com.wx.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wx.video.mapper.UserVisitLogMapper;
import com.wx.video.mapper.VideoMapper;
import com.wx.video.model.User;
import com.wx.video.model.UserVisitLog;
import com.wx.video.model.vo.UserVisitLogVO;
import com.wx.video.service.UserVisitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserVisitLogServiceImpl implements UserVisitLogService {

    @Resource
    private UserVisitLogMapper userVisitLogMapper;

    @Override
    public void saveUserVisitLog(UserVisitLogVO visitLog) {
        QueryWrapper<UserVisitLog> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", visitLog.getOpenid());
        wrapper.eq("video_id", visitLog.getVideoId());
        UserVisitLog userVisitLog = userVisitLogMapper.selectOne(wrapper);

        if (userVisitLog == null) {
            UserVisitLog insertUserVisitLog = new UserVisitLog();
            insertUserVisitLog.setOpenid(visitLog.getOpenid());
            insertUserVisitLog.setVideoId(visitLog.getVideoId());
            insertUserVisitLog.setVisitTime(new Date());
            userVisitLogMapper.insert(insertUserVisitLog);
        } else {
            UserVisitLog updateUserVisitLog = new UserVisitLog();
            updateUserVisitLog.setVisitTime(new Date());
            userVisitLogMapper.update(updateUserVisitLog, wrapper);
        }
    }

    @Override
    public List<UserVisitLogVO> getUserVisitLogByUserId(String openid) {
        return userVisitLogMapper.getUserVisitLogByUserId(openid);
    }
}
