package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

import java.util.Date;

@Data
@TableName("videos")
public class Video implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String videoId;
    private String videoTitle;
    private String videoDesc;
    private String videoPath;
    private String thumbUrl;
    private String duration;
    private Integer salesCounts;
    private Integer categoryId;
    private Integer price;
    private Integer status;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
}