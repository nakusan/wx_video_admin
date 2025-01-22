package com.wx.video.service;

import com.wx.video.model.UserVisitLog;

import java.util.List;

public interface UserVisitLogService {

    /**
     * 保存用户访问信息
     */
    void saveUserVisitLog(UserVisitLog visitLog);

    /**
     * 根据用户名获取用户访问信息
     */
    List<UserVisitLog> getUserVisitLogByUserId(String userId);
}
