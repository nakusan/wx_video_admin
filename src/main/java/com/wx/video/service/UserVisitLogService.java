package com.wx.video.service;

import com.wx.video.model.UserVisitLog;
import com.wx.video.model.vo.UserVisitLogVO;

import java.util.List;

public interface UserVisitLogService {

    /**
     * 保存用户访问信息
     */
    void saveUserVisitLog(String openId, String videoId);

    /**
     * 根据用户名获取用户访问信息
     */
    List<UserVisitLogVO> getUserVisitLogByUserId(String openid);
}
