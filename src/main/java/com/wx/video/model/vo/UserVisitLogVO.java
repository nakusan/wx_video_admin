package com.wx.video.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVisitLogVO implements Serializable {
    private Long id;
    private String openid;
    private String nickname;
    private String videoId;
    private String videoTitle;
    private String videoDescription;
    private String videoPath;
    private String thumbUrl;
    private String duration;
    private Date visitTime;
}
