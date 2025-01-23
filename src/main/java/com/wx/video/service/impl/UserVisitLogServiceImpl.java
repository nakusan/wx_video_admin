package com.wx.video.service.impl;

import com.wx.video.mapper.UserVisitLogMapper;
import com.wx.video.mapper.VideoMapper;
import com.wx.video.model.UserVisitLog;
import com.wx.video.service.UserVisitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class UserVisitLogServiceImpl implements UserVisitLogService {

    @Resource
    private UserVisitLogMapper userVisitLogMapper;

    @Override
    public void saveUserVisitLog(UserVisitLog visitLog) {

    }

    @Override
    public List<UserVisitLog> getUserVisitLogByUserId(String userId) {
        return Collections.emptyList();
    }
}
