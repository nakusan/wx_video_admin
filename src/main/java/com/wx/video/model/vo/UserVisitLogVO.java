package com.wx.video.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVisitLogVO implements Serializable {
    private Long id;
    private String openid;
    private String videoId;
    private Date visitTime;
}
