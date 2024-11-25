package com.wx.video.model.vo;

import java.io.Serializable;
import lombok.Data;

import java.util.Date;

@Data
public class VideoVO implements Serializable {

    private Long id;
    private String videoId;
    private String videoTitle;
    private String videoDesc;
    private String videoPath;
    private String thumbUrl;
    private String duration;
    private Integer salesCounts;
    private Integer categoryId;
    private String categoryName;
    private Integer price;
    private String priceYuan;
    private Integer status;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
}