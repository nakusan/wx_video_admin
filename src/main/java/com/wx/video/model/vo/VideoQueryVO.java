package com.wx.video.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoQueryVO  implements Serializable {

    private String selectVideoTitle;
    private Integer selectVideoCategory;
}
